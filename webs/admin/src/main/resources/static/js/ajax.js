/**
 * 공통 AJAX 통신 유틸리티
 */
const AjaxUtil = (() => {
  return {
    /**
     * 공통 AJAX 요청
     * @param {Object} options 
     */
    call(options = {}) {
      const wait = options.wait ?? true;

      if (wait && typeof LoadingUtil !== 'undefined') {
        LoadingUtil.start();
      }

      jQuery.ajaxSettings.traditional = true;

      options.type = options.type || 'POST';
      options.dataType = options.dataType || 'json';
      options.async = options.async !== false;
      const noAlert = options.noAlert || false;

      const isGet = options.type === 'GET';
      const isHtmlOrText = options.dataType === 'html' || options.dataType === 'text';

      const sendType = options.sendType || (isGet || isHtmlOrText ? 'form' : 'json');

      if (sendType === 'json') {
        options.contentType = options.contentType || 'application/json; charset=UTF-8';
        options.data = (typeof options.data === 'object') ? JSON.stringify(options.data) : options.data;
      } else if (sendType === 'form') {
        // GET 요청이면 contentType을 undefined(기본값)로 두어 Header 입력을 방지
        if (!isGet) {
          options.contentType = options.contentType || 'application/x-www-form-urlencoded; charset=UTF-8';
        } else {
          delete options.contentType; // GET 요청 시 contentType 헤더 제거
        }
      } else if (sendType === 'multipart') {
        options.contentType = false;
        options.processData = false;
      }

      return $.ajax({
        url: options.url,
        type: options.type,
        dataType: options.dataType,
        contentType: options.contentType,
        processData: options.processData,
        cache: false,
        data: options.data,
        async: options.async
      })
        .done(response => {
          let res = response;

          // text/html 요청 시 JSON 파싱 시도
          if (options.dataType === 'text' || options.dataType === 'html') {
            try {
              res = (typeof response === 'string') ? JSON.parse(response) : response;
            } catch (e) {
              res = response;
            }
          }

          // ApiResponse<T> 비즈니스 에러 처리 (200 OK 내 success: false)
          const isJsonObject = (typeof res === 'object' && res !== null);

          if (isJsonObject && !res.success) {
            if (!noAlert && res.message) {
              alert(res.message);
            }

            if (typeof options.fail === 'function') {
              options.fail(res);
            }
            return;
          }

          // 정상 성공 콜백
          if (typeof options.callBack === 'function') {
            options.callBack(res);
          }

          // 페이지 내 공통 바인딩 이벤트 실행
          if (typeof UiCommon !== 'undefined') {
            UiCommon.initAll();
          }
        })
        .fail((xhr, status, error) => {
          // 401 Unauthorized (세션 만료)
          if (xhr.status === 401) {
            alert('세션이 만료되어 로그인 페이지로 이동합니다.');
            window.top.location.href = '/login';
            return;
          }

          // 403 Forbidden (권한 없음)
          if (xhr.status === 403) {
            alert('접근 권한이 없습니다.');
            return;
          }

          let errorMsg = '';
          if (xhr.responseJSON && xhr.responseJSON.message) {
            errorMsg = xhr.responseJSON.message;
          } else if (xhr.responseText) {
            try {
              const parsed = JSON.parse(xhr.responseText);
              errorMsg = parsed.message;
            } catch (e) { }
          }

          if (!errorMsg) {
            errorMsg = `요청 처리 중 오류가 발생했습니다. (${xhr.status})`;
          }

          if (!noAlert) {
            alert(errorMsg);
          }

          if (typeof options.fail === 'function') {
            options.fail(xhr, status, error);
          }
        })
        .always(() => {
          if (wait && typeof LoadingUtil !== 'undefined') {
            LoadingUtil.stop();
          }

          if (typeof options.complete === 'function') {
            options.complete();
          }
        });
    },

    /**
     * 특정 영역 HTML 동적 로드 (jQuery load 래핑)
     * @param {String} targetId - HTML이 삽입될 요소 ID
     * @param {String} url - 요청 URL
     * @param {Object} params - 파라미터
     */
    load(targetId, url, params = {}) {
      if (typeof LoadingUtil !== 'undefined') {
        LoadingUtil.start();
      }

      $(`#${targetId}`).load(url, params, (response, status, xhr) => {
        if (typeof LoadingUtil !== 'undefined') {
          LoadingUtil.stop();
        }

        if (status === 'error') {
          if (xhr.status === 401) {
            alert('세션이 만료되어 로그인 페이지로 이동합니다.');
            window.top.location.href = '/login';
            return;
          }

          if (xhr.status === 403) {
            alert('접근 권한이 없습니다.');
            return;
          }

          let errorMsg = `화면 불러오기 중 오류가 발생했습니다. (${xhr.status})`;
          if (xhr.responseText) {
            try {
              const parsed = JSON.parse(xhr.responseText);
              if (parsed.message) errorMsg = parsed.message;
            } catch (e) { }
          }

          alert(errorMsg);
        }
      });
    }
  };
})();