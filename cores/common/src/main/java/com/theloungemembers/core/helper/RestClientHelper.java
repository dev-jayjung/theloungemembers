package com.theloungemembers.core.helper;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestClientHelper {

    private final RestClient restClient;

    /**
     * 공통 GET 요청 API
     * @param uri 호출할 외부 URL (Ex. "https://api.com/v1/users/{id}")
     * @param responseType 받아올 Response DTO 클래스 타입
     * @param uriVariables URI에 매핑될 패스 파라미터 변수들
     */
    public <T> T get(String uri, Class<T> responseType, Object... uriVariables) {
        try {
            return restClient.get()
                    .uri(uri, uriVariables)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(responseType);
        } catch (Exception e) {
            log.error("[RestClientHelper] GET 요청 실패 - URI: {}, Error: {}", uri, e.getMessage());
            throw e;
        }
    }

    /**
     * 공통 POST 요청 API
     * @param uri 호출할 외부 URL
     * @param requestBody 보낼 Request DTO 객체
     * @param responseType 받아올 Response DTO 클래스 타입
     */
    public <T, R> T post(String uri, R requestBody, Class<T> responseType) {
        try {
            return restClient.post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(responseType);
        } catch (Exception e) {
            log.error("[RestClientHelper] POST 요청 실패 - URI: {}, Error: {}", uri, e.getMessage());
            throw e;
        }
    }

    /**
     * 공통 PUT 요청 (데이터 수정)
     * @param uri 호출할 외부 URL
     * @param requestBody 수정할 데이터 내용 (Request DTO)
     * @param responseType 수정 후 응답받을 결과 DTO 타입
     */
    public <T, R> T put(String uri, R requestBody, Class<T> responseType) {
        try {
            return restClient.put()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(responseType);
        } catch (Exception e) {
            log.error("[RestClientHelper] PUT 요청 실패 - URI: {}, Error: {}", uri, e.getMessage());
            throw e;
        }
    }

    /**
     * 공통 DELETE 요청 (데이터 삭제)
     * @param uri 호출할 외부 URL (Ex. "https://api.com/v1/lounges/{id}")
     * @param uriVariables 삭제할 대상의 Key 값들 (패스 파라미터 변수)
     */
    public void delete(String uri, Object... uriVariables) {
        try {
            restClient.delete()
                    .uri(uri, uriVariables)
                    .retrieve()
                    .toBodilessEntity(); // 삭제 요청은 보통 응답 바디가 없으므로 본문을 비워서 처리
        } catch (Exception e) {
            log.error("[RestClientHelper] DELETE 요청 실패 - URI: {}, Error: {}", uri, e.getMessage());
            throw e;
        }
    }
}