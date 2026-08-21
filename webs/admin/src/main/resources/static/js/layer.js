/**
 * 공통 레이어 팝업 유틸리티
 */
const LayerUtil = (() => {
  return {
    /**
     * 레이어 팝업 생성 및 오픈
     * @returns {Promise} 팝업 닫힘/확인 시 결과 반환
     */
    open(config = {}) {
      return new Promise(resolve => {
        const {
          id = 'layerPopup_' + new Date().getTime(),
          title = '레이어 팝업',
          width = 800,
          body = '',
          confirmText = '확인',
          closeText = '닫기',
          showConfirm = true,
          onConfirm = null // 추가 유효성 검사 또는 커스텀 처리용
        } = config;

        // 기존 동일 ID 레이어 제거
        this.close(id);

        // HTML 템플릿
        const layerHtml = `
          <div class="layer_overlay"></div>
          <div class="modalLayerPop blankPop">
              <div class="modal fade in" tabindex="-1"  style="display:block; padding-bottom: 200px;">
                  <div class="modal-dialog" style="width: ${width}px;">
                      <div class="modal-content" id="${id}">
                          <img src="/images/pop_blank_close.png" class="layer-close-btn" alt="닫기" />
                          <div class="modal-header">
                              <h1 class="modal-title">${title}</h1>
                          </div>
                          <div class="modal-body">
                              <div class="popContent">${body}</div>
                              <div class="modal-footer">
                                  ${showConfirm ? `<button type="button" class="btn_type1 btn-confirm">${confirmText}</button>` : ''}
                                  <button type="button" class="btn_type2 ml7 btn-close">${closeText}</button>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
        `;

        const $layer = $(layerHtml);
        $('body').append($layer).css('overflow-y', 'hidden');

        // 팝업 닫기 내부 함수
        const handleClose = (resultData = null) => {
          this.close(id);

          resolve(resultData);
        };

        // 이벤트 바인딩
        $layer.find('.btn-close, .layer-close-btn').on('click', () => handleClose(null));

        $layer.find('.btn-confirm').on('click', async () => {
          if (typeof onConfirm === 'function') {
            const checkResult = await onConfirm($layer);
            if (!checkResult) {
              return;
            }

            handleClose(checkResult);
          } else {
            handleClose(true);
          }
        });
      });
    },

    /**
     * 레이어 수동 닫기
     */
    close(id) {
      if (id) {
        $(`.modalLayerPop[data-layer-id="${id}"], .layer_overlay[data-layer-id="${id}"]`).remove();
        $(`#${id}`).closest('.modalLayerPop').prev('.layer_overlay').remove();
        $(`#${id}`).closest('.modalLayerPop').remove();
      } else {
        $('.modalLayerPop').last().prev('.layer_overlay').remove();
        $('.modalLayerPop').last().remove();
      }

      // 열려있는 레이어 팝업이 더 이상 없으면 스크롤바 복원
      if ($('.modalLayerPop').length === 0) {
        $('body').css('overflow-y', 'auto');
      }
    }
  };
})();