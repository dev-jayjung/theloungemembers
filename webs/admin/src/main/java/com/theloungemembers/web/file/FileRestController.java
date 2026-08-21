package com.theloungemembers.web.file;

import java.net.URI;
import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.helper.S3FileHelper;
import com.theloungemembers.core.helper.S3FileInfo;
import com.theloungemembers.core.helper.S3FilePath;
import com.theloungemembers.core.util.ResponseUtil;

import lombok.RequiredArgsConstructor;

/**
 * S3 파일 업로드/다운로드 URL RestController
 */
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileRestController {

    private final S3FileHelper s3FileHelper;

    @GetMapping("/upload-url")
    public ResponseEntity<ApiResponse<S3FileInfo>> getUploadUrl(@RequestParam String fileName,
            @RequestParam(defaultValue = "true") boolean isBuildFileName) {
        S3FileInfo fileInfo = s3FileHelper.getPresignedUploadUrl(S3FilePath.TEMP, fileName, Duration.ofMinutes(3),
                isBuildFileName);

        return ResponseUtil.success(fileInfo);
    }

    @GetMapping("/download-url")
    public ResponseEntity<ApiResponse<String>> getDownloadUrl(@RequestParam String key, @RequestParam String fileName) {
        String presignedDownloadUrl = s3FileHelper.getPresignedDownloadUrl(key, fileName, Duration.ofMinutes(10));

        return ResponseUtil.success(presignedDownloadUrl);
    }

    /**
     * S3 Key를 받아 10분간 유효한 Presigned Download URL로 302 Redirect
     * (에디터 이미지 표시 전용)
     */
    @GetMapping("/display")
    public ResponseEntity<Void> displayImage(@RequestParam String key) {
        // 이미지 렌더링을 위해 originalFileName은 null 전달
        String presignedUrl = s3FileHelper.getPresignedDownloadUrl(key, null, Duration.ofMinutes(10));

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(presignedUrl))
                .build();
    }
}