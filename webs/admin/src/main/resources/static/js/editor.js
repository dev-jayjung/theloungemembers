/**
 * 스마트 에디터(SmartEditor2) 관련 유틸
 */
const EditorUtil = (() => {
  // 생성된 에디터 인스턴스들을 관리하는 객체
  const instances = {};

  return {
    /**
     * 스마트에디터 초기화
     * @param {string} targetId - textarea의 element ID
     * @param {object} customParams - 툴바/옵션 설정 (선택)
     */
    init(targetId, customParams) {
      if (!nhn || !nhn.husky || !nhn.husky.EZCreator) {
        console.error('HuskyEZCreator.js가 로드되지 않았습니다.');
        return;
      }

      instances[targetId] = [];

      const defaultParams = {
        bUseToolbar: true,
        bUseVerticalResizer: true,
        bUseModeChanger: true
      };

      // 커스텀 옵션 병합
      const htParams = Object.assign({}, defaultParams, customParams || {});

      nhn.husky.EZCreator.createInIFrame({
        oAppRef: instances[targetId],
        elPlaceHolder: targetId,
        sSkinURI: '/plugins/smarteditor/SmartEditor2Skin.html', // 실제 설치 경로에 맞게 수정
        fCreator: 'createSEditor2',
        htParams: htParams
      });
    },

    /**
    * 특정 textarea에 에디터 내용 동기화 (UPDATE_CONTENTS_FIELD)
    * @param {string} targetId - textarea의 element ID
    */
    sync(targetId) {
      if (instances[targetId] && instances[targetId].getById[targetId]) {
        instances[targetId].getById[targetId].exec('UPDATE_CONTENTS_FIELD', []);
      }
    },

    /**
    * 모든 에디터의 내용을 각 textarea에 일괄 동기화
    */
    syncAll() {
      Object.keys(instances).forEach(targetId => this.sync(targetId));
    },

    /**
     * 특정 에디터에 이미지 HTML 삽입 (S3Uploader 연동 지원)
     * @param {string} targetId - textarea의 element ID
     * @param {string} imgUrl - 이미지 Download/Access URL
     * @param {string} s3Key - (선택) S3 Object Key (추후 본문 파싱용)
     */
    pasteHTML(targetId, imgUrl, s3Key) {
      if (instances[targetId] && instances[targetId].getById && instances[targetId].getById[targetId]) {
        const keyAttr = s3Key ? ` data-s3-key="${s3Key}"` : '';
        const imgHtml = `<img src="${imgUrl}"${keyAttr} style="max-width:100%; height:auto;" /><br><br>`;

        instances[targetId].getById[targetId].exec('PASTE_HTML', [imgHtml]);
      } else {
        console.warn('SmartEditor 인스턴스를 찾을 수 없습니다:', targetId);
      }
    },

    /**
     * 특정 에디터에 HTML/텍스트 데이터 바인딩
     * @param {string} targetId - textarea의 element ID
     * @param {string} htmlContent - DB에서 조회해온 본문 HTML
     */
    setHTML(targetId, htmlContent) {
      const content = htmlContent || '';

      // 초기화 단계에서 textarea에 직접 값을 넣어두면 에디터 로드 시 자동 반영됨
      const $elem = document.getElementById(targetId);
      if ($elem) {
        $elem.value = content;
      }

      // 이미 에디터 인스턴스가 생성 완료된 상태라면 에디터 내부 본문(IR) 업데이트
      if (instances[targetId] && instances[targetId].getById[targetId]) {
        instances[targetId].getById[targetId].exec('SET_IR', [content]);
      }
    },

    instances: instances
  };
})();