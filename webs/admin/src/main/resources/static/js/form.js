/**
 * Form 조작 유틸리티
 */
const FormUtil = (() => {
  return {
    /**
     * Form 입력 요소 초기화
     * @param {string|HTMLElement|jQuery} form - Form 엘리먼트 또는 Selector
     */
    reset(form) {
      const $form = $(form);
      $form.trigger('reset');
      $form.find('input[type="hidden"]').val('');
    },

    /**
     * Form 요소 내의 입력 값들을 JavaScript Object로 변환
     * @param {string|HTMLElement|jQuery} form - Form 엘리먼트 또는 Selector
     * @returns {Object} 직렬화된 데이터 객체
     */
    serialize(form) {
      const $form = $(form);
      if ($form.length === 0) {
        return {};
      }

      const formElement = $form.is('form') ? $form[0] : $form.find('form')[0] || $form[0];
      const formData = new FormData(formElement);
      const result = {};

      for (const [key, value] of formData.entries()) {
        // 이미 동일한 key가 존재하면 배열로 변환 (다중 체크박스, 동일 name 요소 처리)
        if (Object.prototype.hasOwnProperty.call(result, key)) {
          if (!Array.isArray(result[key])) {
            result[key] = [result[key]];
          }
          result[key].push(value);
        } else {
          result[key] = value;
        }
      }

      return result;
    },

    /**
     * 동적 Form 생성 및 Submit
     * @param {Object} config - { id, url, data, method, target }
     */
    submit(config = {}) {
      const {
        id = 'dynamicForm',
        url = '',
        data = null,
        method = 'POST',
        target = ''
      } = config;

      if (!url) {
        console.warn('[FormUtil.submit] 요청 URL이 존재하지 않습니다.');
        return;
      }

      // 엑셀 다운로드 요청 시 로딩바 표출
      if (url.toLowerCase().includes('exceldownload')) {
        if (typeof LoadingUtil !== 'undefined') {
          LoadingUtil.start();
        }
      }

      $(`#${id}Form`).remove();

      const $form = $('<form>', {
        id: `${id}Form`,
        name: `${id}Form`,
        action: url,
        method: method.toUpperCase(),
        target: target
      });

      const appendInputs = obj => {
        if (!obj || typeof obj !== 'object') return;
        Object.entries(obj).forEach(([key, value]) => {
          $('<input>').attr({ type: 'hidden', name: key, value: value ?? '' }).appendTo($form);
        });
      };

      if (Array.isArray(data)) {
        data.forEach(item => appendInputs(item));
      } else if (typeof data === 'object') {
        appendInputs(data);
      }

      $('body').append($form);
      $form.submit();
    },

    /**
     * Form validationEngine 설정 및 검증
     */
    validate: {
      /**
       * 폼 유효성 검사기 엔진 초기 설정
       * @param {string|HTMLElement|jQuery} form - Form 엘리먼트 또는 Selector
       */
      init(form) {
        const $form = $(form);
        if ($.fn.validationEngine) {
          $form.validationEngine('attach', {
            promptPosition: 'topLeft',
            scroll: false
          });
        }
      },

      /**
       * 폼 에러 프롬프트 툴팁 모두 숨기기
       * @param {string|HTMLElement|jQuery} form - Form 엘리먼트 또는 Selector
       */
      hide(form) {
        const $form = $(form);
        if ($.fn.validationEngine) {
          $form.validationEngine('hide');
        }
      },

      /**
       * 폼 전체 유효성 검사 실행
       * @param {string|HTMLElement|jQuery} form - Form 엘리먼트 또는 Selector
       * @returns {boolean} 통과 여부 (true/false)
       */
      check(form) {
        const $form = $(form);

        if ($.fn.validationEngine) {
          return $form.validationEngine('validate');
        }

        return true;
      }
    }
  };
})();