package com.theloungemembers.core.aspect;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.ObjectUtils;

import com.theloungemembers.core.annotation.S3File;
import com.theloungemembers.core.helper.S3FileHelper;
import com.theloungemembers.core.helper.S3FilePath;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FileUploadAspect {

    private final S3FileHelper s3FileHelper;
    private static final String S3_IMG_KEY = "data-s3-key";

    @Before("@annotation(com.theloungemembers.core.annotation.ProcessS3File)")
    public void processS3Files(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg == null) {
                continue;
            }
            inspectAndProcessFields(arg);
        }
    }

    private void inspectAndProcessFields(Object dto) throws IllegalAccessException {
        Field[] fields = dto.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(S3File.class)) {
                continue;
            }

            field.setAccessible(true);
            Object value = field.get(dto);

            if (value == null)
                continue;

            S3FilePath targetPath = field.getAnnotation(S3File.class).path();

            // String 필드 처리 (단일 Key OR 에디터 HTML)
            if (value instanceof String stringValue && !ObjectUtils.isEmpty(stringValue)) {
                if (isHtmlContent(stringValue)) {
                    // 에디터 HTML인 경우
                    String processedHtml = convertEditorHtmlAndRegisterS3Move(stringValue, targetPath);
                    field.set(dto, processedHtml);
                } else if (isTempKey(stringValue)) {
                    // 단일 파일 Key인 경우
                    String targetKey = convertToTargetKey(stringValue, targetPath);
                    field.set(dto, targetKey);
                    registerS3MoveAfterCommit(stringValue, targetKey);
                }
            }
            // List<String> 필드 처리 (다중 파일 Key)
            else if (value instanceof List<?> listValue && !listValue.isEmpty()) {
                List<String> updatedList = new ArrayList<>();
                boolean isModified = false;

                for (Object item : listValue) {
                    if (item instanceof String stringItem && isTempKey(stringItem)) {
                        String targetKey = convertToTargetKey(stringItem, targetPath);
                        updatedList.add(targetKey);
                        registerS3MoveAfterCommit(stringItem, targetKey);
                        isModified = true;
                    } else if (item instanceof String stringItem) {
                        updatedList.add(stringItem);
                    }
                }

                if (isModified) {
                    field.set(dto, updatedList);
                }
            }
        }
    }

    private boolean isHtmlContent(String content) {
        return content.contains("<img") || content.contains(S3_IMG_KEY);
    }

    private boolean isTempKey(String key) {
        return key.contains("/" + S3FilePath.TEMP.getCode() + "/");
    }

    private String convertToTargetKey(String tempKey, S3FilePath targetPath) {
        String fileName = tempKey.substring(tempKey.lastIndexOf('/') + 1);
        return s3FileHelper.getPathKey(targetPath, fileName, false);
    }

    private String convertEditorHtmlAndRegisterS3Move(String htmlContent, S3FilePath targetPath) {
        Document doc = Jsoup.parse(htmlContent);
        Elements imgs = doc.select("img[" + S3_IMG_KEY + "]");
        boolean isModified = false;

        for (Element img : imgs) {
            String tempS3Key = img.attr(S3_IMG_KEY);

            if (isTempKey(tempS3Key)) {
                String targetKey = convertToTargetKey(tempS3Key, targetPath);

                img.attr(S3_IMG_KEY, targetKey);
                img.attr("src", "/api/files/display?key=" + URLEncoder.encode(targetKey, StandardCharsets.UTF_8));
                isModified = true;

                registerS3MoveAfterCommit(tempS3Key, targetKey);
            }
        }

        return isModified ? doc.body().html() : htmlContent;
    }

    private void registerS3MoveAfterCommit(String sourceKey, String targetKey) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    try {
                        s3FileHelper.moveObject(sourceKey, targetKey);
                        log.info("S3 File Moved Success: {} -> {}", sourceKey, targetKey);
                    } catch (Exception e) {
                        log.error("S3 File Move Failed: {} -> {}", sourceKey, targetKey, e);
                    }
                }
            });
        } else {
            s3FileHelper.moveObject(sourceKey, targetKey);
        }
    }
}