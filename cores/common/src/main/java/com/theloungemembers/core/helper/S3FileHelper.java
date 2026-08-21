package com.theloungemembers.core.helper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.util.AssertUtil;
import com.theloungemembers.core.util.MimeTypeUtil;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
@RequiredArgsConstructor
public class S3FileHelper {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.s3.url}")
    private String bucketUrl;

    @Value("${spring.cloud.aws.s3.directory}")
    private String directory;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public S3FileInfo upload(S3FilePath path, MultipartFile file) {
        return upload(path, file, true);
    }

    public S3FileInfo upload(S3FilePath path, MultipartFile file, boolean isBuildFileName) {
        AssertUtil.notNull(path, "path must not be null");
        AssertUtil.notNull(file, "file must not be null");

        String key = getPathKey(path, file.getOriginalFilename(), isBuildFileName);

        String originalFileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();

        PutObjectRequest.Builder requestBuilder = PutObjectRequest.builder().bucket(bucket).key(key)
                .contentLength(size);

        if (contentType != null && !contentType.isBlank()) {
            requestBuilder = requestBuilder.contentType(contentType);
        }

        PutObjectRequest putObjectRequest = requestBuilder.build();

        try (InputStream is = file.getInputStream()) {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(is, size));

            String url = bucketUrl + key;

            return S3FileInfo.builder()
                    .s3Key(key)
                    .originalName(originalFileName)
                    .url(url)
                    .size(size)
                    .contentType(contentType)
                    .build();
        } catch (IOException ioe) {
            throw new BusinessException("S3 업로드 실패: 입력 스트림 처리 중 오류가 발생했습니다.");
        } catch (SdkException sdkEx) {
            throw new BusinessException("S3 업로드 실패: S3 통신 중 오류가 발생했습니다.");
        }
    }

    public List<S3FileInfo> upload(S3FilePath path, List<MultipartFile> fileList) {
        return upload(path, fileList, true);
    }

    public List<S3FileInfo> upload(S3FilePath path, List<MultipartFile> fileList, boolean isBuildFileName) {
        AssertUtil.notNull(path, "path must not be null");
        AssertUtil.notNull(fileList, "fileList must not be null");

        List<S3FileInfo> uploadList = new ArrayList<>();

        for (MultipartFile file : fileList) {
            uploadList.add(upload(path, file, isBuildFileName));
        }

        return uploadList;
    }

    public S3FileInfo getFile(String key) {
        AssertUtil.notNull(key);

        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(bucket).key(key).build();

        try {
            HeadObjectResponse headObject = s3Client.headObject(headObjectRequest);

            return S3FileInfo.builder()
                    .s3Key(key)
                    .url(bucketUrl + key)
                    .size(headObject.contentLength())
                    .contentType(headObject.contentType())
                    .build();
        } catch (SdkException sdkEx) {
            throw new BusinessException("S3 파일 메타데이터 조회 실패");
        }
    }

    public List<S3FileInfo> getFileList(S3FilePath path) {
        AssertUtil.notNull(path);

        String prefix = directory + "/" + path.getCode();

        ListObjectsV2Request listRequest = ListObjectsV2Request.builder().bucket(bucket).prefix(prefix + "/").build();

        try {
            ListObjectsV2Response response = s3Client.listObjectsV2(listRequest);
            List<S3Object> objects = response.contents();

            if (objects == null || objects.isEmpty()) {
                return List.of();
            }

            return objects.stream().map(object -> {
                String objectKey = object.key();
                long size = object.size();

                return S3FileInfo.builder()
                        .s3Key(objectKey)
                        .size(size)
                        .build();
            }).filter(Objects::nonNull).toList();
        } catch (SdkException sdkEx) {
            throw new BusinessException("S3 파일 목록 조회 실패");
        }
    }

    public void delete(String key) {
        AssertUtil.notNull(key);

        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        try {
            s3Client.deleteObject(deleteRequest);
        } catch (SdkException sdkEx) {
            throw new BusinessException("S3 파일 삭제 실패");
        }
    }

    private String buildFileName(String fileName) {
        AssertUtil.notNull(fileName);

        String ext = "";

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            String extractedExt = FilenameUtils.getExtension(fileName);
            if (extractedExt != null && !extractedExt.isBlank()) {
                ext = "." + extractedExt;
            }
        }

        return UUID.randomUUID() + ext;
    }

    public ResponseEntity<Resource> download(String key, String fileName) {
        AssertUtil.notNull(key);
        AssertUtil.notNull(fileName);

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        try {
            ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);
            Resource resource = new InputStreamResource(responseInputStream);

            // 한글 파일명 URL 인코딩 (브라우저 깨짐 방지)
            String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''"
                    + encodedFileName;

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
        } catch (SdkException sdkEx) {
            throw new BusinessException("S3 다운로드 실패");
        }
    }

    public String getPresignedDownloadUrl(String key, String originalFileName, Duration expiry) {
        AssertUtil.notNull(key, "key must not be null");
        AssertUtil.notNull(expiry, "expiry must not be null");

        GetObjectRequest.Builder getReqBuilder = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key);

        if (originalFileName != null && !originalFileName.isBlank()) {
            String encodedFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
            getReqBuilder.responseContentDisposition(
                    "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName);
        }

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getReqBuilder.build())
                .signatureDuration(expiry)
                .build();

        try {
            PresignedGetObjectRequest presigned = s3Presigner.presignGetObject(presignRequest);
            return presigned.url().toString();
        } catch (SdkException sdkEx) {
            throw new BusinessException("S3 다운로드용 Presigned URL 생성 실패");
        }
    }

    public S3FileInfo getPresignedUploadUrl(S3FilePath path, String fileName, Duration expiry) {
        return getPresignedUploadUrl(path, fileName, expiry, true);
    }

    public S3FileInfo getPresignedUploadUrl(S3FilePath path, String fileName, Duration expiry,
            boolean isBuildFileName) {
        AssertUtil.notNull(path, "path must not be null");
        AssertUtil.notNull(fileName, "fileName must not be null");
        AssertUtil.notNull(expiry, "expiry must not be null");

        String key = getPathKey(path, fileName, isBuildFileName);
        String contentType = MimeTypeUtil.detectMimeType(fileName);

        PutObjectRequest.Builder putReqBuilder = PutObjectRequest.builder().bucket(bucket).key(key);

        if (contentType != null && !contentType.isBlank()) {
            putReqBuilder = putReqBuilder.contentType(contentType);
        }

        PutObjectRequest putObjectRequest = putReqBuilder.build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder().putObjectRequest(putObjectRequest)
                .signatureDuration(expiry).build();

        try {
            PresignedPutObjectRequest presigned = s3Presigner.presignPutObject(presignRequest);
            URL signedPutURL = presigned.url();
            return S3FileInfo.builder()
                    .s3Key(key)
                    .originalName(fileName)
                    .url(signedPutURL.toString())
                    .contentType(contentType)
                    .build();
        } catch (SdkException sdkEx) {
            throw new BusinessException("S3 업로드용 Presigned URL 생성 실패");
        }
    }

    public S3FileInfo getTempUploadUrl(String fileName) {
        return getPresignedUploadUrl(S3FilePath.TEMP, fileName, Duration.ofMinutes(3));
    }

    public void moveObject(String sourceKey, String targetKey) {
        AssertUtil.notNull(sourceKey, "sourceKey must not be null");
        AssertUtil.notNull(targetKey, "targetKey must not be null");

        CopyObjectRequest copyRequest = CopyObjectRequest.builder().sourceBucket(bucket).sourceKey(sourceKey)
                .destinationBucket(bucket).destinationKey(targetKey).build();

        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder().bucket(bucket).key(sourceKey).build();

        try {
            s3Client.copyObject(copyRequest);
            s3Client.deleteObject(deleteRequest);
        } catch (SdkException sdkEx) {
            throw new BusinessException("S3 객체 이동 실패");
        }
    }

    public String moveObject(S3FileInfo fileInfo, S3FilePath path) {
        AssertUtil.notNull(fileInfo, "fileInfo must not be null");
        AssertUtil.notNull(fileInfo.getS3Key(), "s3Key must not be null");
        AssertUtil.notNull(path, "path must not be null");
        AssertUtil.notNull(fileInfo.getOriginalName(), "fileName must not be null");

        String targetKey = getPathKey(path, fileInfo.getOriginalName());

        moveObject(fileInfo.getS3Key(), targetKey);

        return targetKey;
    }

    public String getPathKey(S3FilePath path, String fileName) {
        return getPathKey(path, fileName, true);
    }

    public String getPathKey(S3FilePath path, String fileName, boolean isBuildFileName) {
        AssertUtil.notNull(path, "path must not be null");
        AssertUtil.notNull(fileName, "fileName must not be null");

        return directory + "/" + path.getCode() + "/" + (isBuildFileName ? buildFileName(fileName) : fileName);
    }
}
