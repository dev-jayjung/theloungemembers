/**
 * 공통 윈도우 팝업 유틸리티
 */
const WindowUtil = (() => {
  return {
    /**
     * 공통 윈도우 팝업 오픈 유틸리티
     * @param {Object} config 
     *  - url : 이동할 URL
     *  - target : 팝업 타겟명 (기본값: 'popup')
     *  - width : 팝업 가로 크기 (기본값: 800)
     *  - height : 팝업 세로 크기 (기본값: 600)
     *  - method : GET / POST (기본값: 'GET')
     *  - data : 전달할 데이터 (Object 또는 Array)
     *  - scrollbars, resizable 등 option 설정
     */
    open(config = {}) {
      const {
        url = '',
        target = 'popup',
        width = 800,
        height = 600,
        method = 'GET',
        data = null
      } = config;

      // 듀얼 모니터 지원 중앙 정렬 좌표 계산
      const dualScreenLeft = window.screenLeft !== undefined ? window.screenLeft : window.screenX;
      const dualScreenTop = window.screenTop !== undefined ? window.screenTop : window.screenY;

      const screenWidth = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
      const screenHeight = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

      const left = Math.floor((screenWidth - width) / 2 + dualScreenLeft);
      const top = Math.floor((screenHeight - height) / 2 + dualScreenTop);

      // Window 옵션 설정
      const settings = [
        `width=${width}`,
        `height=${height}`,
        `top=${top}`,
        `left=${left}`
      ];

      const options = ['scrollbars', 'resizable', 'toolbar', 'location', 'status', 'menubar', 'fullscreen'];
      options.forEach(opt => {
        if (config[opt] !== undefined) {
          settings.push(`${opt}=${config[opt]}`);
        }
      });

      const windowFeatures = settings.join(',');

      // 팝업 오픈 및 Form 전송 처리
      if (data) {
        // 빈 창 팝업 먼저 오픈
        const popupWindow = window.open('', target, windowFeatures);

        // 기존 동적 폼 제거 후 새로 생성
        const formId = `${target}Form`;
        $(`#${formId}`).remove();

        const $form = $('<form>', {
          id: formId,
          name: formId,
          action: url,
          target: target,
          method: method.toUpperCase()
        });

        // Hidden Input 데이터 생성
        const appendInputs = obj => {
          if (!obj || typeof obj !== 'object') {
            return;
          }

          Object.entries(obj).forEach(([key, value]) => {
            $('<input>').attr({
              type: 'hidden',
              name: key,
              value: value ?? ''
            }).appendTo($form);
          });
        };

        if (Array.isArray(data)) {
          data.forEach(item => appendInputs(item));
        } else if (typeof data === 'object') {
          appendInputs(data);
        }

        $('body').append($form);
        $form.submit();

        // 팝업 포커스
        if (popupWindow) {
          popupWindow.focus();
        }
      } else {
        // 단순 URL 호출
        const popupWindow = window.open(url, target, windowFeatures);
        if (popupWindow) {
          popupWindow.focus();
        }
      }
    }
  };
})();