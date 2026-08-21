package com.theloungemembers.core.helper;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestClientHelper {

    private final RestClient restClient;

    /**
     * 공통 GET 요청 API (공통 RestClient 사용)
     * @param uri 호출할 외부 URL (Ex. "https://api.com/v1/users/{id}")
     * @param responseType 받아올 Response DTO 클래스 타입
     * @param uriVariables URI에 매핑될 패스 파라미터 변수들
     */
    public <T> T get(String uri, Class<T> responseType, Object... uriVariables) {
        return get(this.restClient, uri, responseType, uriVariables);
    }

    /**
     * 공통 GET 요청 API (특정 RestClient 사용. ex. PG용)
     * @param targetClient 사용할 RestClient 인스턴스
     * @param uri 호출할 외부 URL (Ex. "https://api.com/v1/users/{id}")
     * @param responseType 받아올 Response DTO 클래스 타입
     * @param uriVariables URI에 매핑될 패스 파라미터 변수들
     */
    public <T> T get(RestClient targetClient, String uri, Class<T> responseType, Object... uriVariables) {
        try {
            return targetClient.get()
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
     * 공통 POST 요청 API (공통 RestClient 사용)
     * @param uri 호출할 외부 URL
     * @param requestBody 보낼 Request DTO 객체
     * @param responseType 받아올 Response DTO 클래스 타입
     */
    public <T, R> T post(String uri, R requestBody, Class<T> responseType) {
        return post(this.restClient, uri, requestBody, responseType);
    }

    /**
     * 공통 POST 요청 API (특정 RestClient 사용. ex. PG용)
     * @param targetClient 사용할 RestClient 인스턴스
     * @param uri 호출할 외부 URL
     * @param requestBody 보낼 Request DTO 객체
     * @param responseType 받아올 Response DTO 클래스 타입
     */
    public <T, R> T post(RestClient targetClient, String uri, R requestBody, Class<T> responseType) {
        try {
            return targetClient.post()
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
     * 공통 Form Data POST 요청 API (application/x-www-form-urlencoded)
     * Keycloak 토큰 발급 등 Form 폼 데이터 전송용
     * @param uri 호출할 외부 URL
     * @param formData MultiValueMap 형태로 구성된 Form 파라미터
     * @param responseType 받아올 Response DTO 클래스 타입
     */
    public <T> T postForm(String uri, MultiValueMap<String, String> formData, Class<T> responseType) {
        try {
            return restClient.post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED) // Keycloak 규격
                    .body(formData)
                    .retrieve()
                    .body(responseType);
        } catch (Exception e) {
            log.error("[RestClientHelper] POST Form 요청 실패 - URI: {}, Error: {}", uri, e.getMessage());
            throw e;
        }
    }

    /**
     * 공통 PUT 요청 (데이터 수정) (공통 RestClient 사용)
     * @param uri 호출할 외부 URL
     * @param requestBody 수정할 데이터 내용 (Request DTO)
     * @param responseType 수정 후 응답받을 결과 DTO 타입
     */
    public <T, R> T put(String uri, R requestBody, Class<T> responseType) {
        return put(this.restClient, uri, requestBody, responseType);
    }

    /**
     * 공통 PUT 요청 (데이터 수정) (특정 RestClient 사용. ex. PG용)
     * @param targetClient 사용할 RestClient 인스턴스
     * @param uri 호출할 외부 URL
     * @param requestBody 수정할 데이터 내용 (Request DTO)
     * @param responseType 수정 후 응답받을 결과 DTO 타입
     */
    public <T, R> T put(RestClient targetClient, String uri, R requestBody, Class<T> responseType) {
        try {
            return targetClient.put()
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
        delete(this.restClient, uri, uriVariables);
    }

    /**
     * 공통 DELETE 요청 (데이터 삭제) (특정 RestClient 사용. ex. PG용)
     * @param targetClient 사용할 RestClient 인스턴스
     * @param uri 호출할 외부 URL (Ex. "https://api.com/v1/lounges/{id}")
     * @param uriVariables 삭제할 대상의 Key 값들 (패스 파라미터 변수)
     */
    public void delete(RestClient targetClient, String uri, Object... uriVariables) {
        try {
            targetClient.delete()
                    .uri(uri, uriVariables)
                    .retrieve()
                    .toBodilessEntity(); // 삭제 요청은 보통 응답 바디가 없으므로 본문을 비워서 처리
        } catch (Exception e) {
            log.error("[RestClientHelper] DELETE 요청 실패 - URI: {}, Error: {}", uri, e.getMessage());
            throw e;
        }
    }
}