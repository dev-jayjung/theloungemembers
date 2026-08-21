/**
 * @use 간단 포토 업로드용으로 제작되었습니다.
 * @author cielo
 * @See nhn.husky.SE2M_Configuration 
 * @ 팝업 마크업은 SimplePhotoUpload.html과 SimplePhotoUpload_html5.html이 있습니다. 
 */

nhn.husky.SE2M_AttachQuickPhoto = jindo.$Class({
  name: 'SE2M_AttachQuickPhoto',

  $init: function () { },

  $ON_MSG_APP_READY: function () {
    this.oApp.exec('REGISTER_UI_EVENT', ['photo_attach', 'click', 'ATTACHPHOTO_OPEN_WINDOW']);
    this._createHiddenFileInput();
  },

  // 숨겨진 File Input 생성
  _createHiddenFileInput: function () {
    if (document.getElementById('se2_file_input')) return;

    const fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.id = 'se2_file_input';
    fileInput.accept = 'image/*';
    fileInput.style.display = 'none';

    const self = this;
    fileInput.addEventListener('change', function (e) {
      const file = e.target.files[0];
      if (file) {
        self._uploadImageFile(file);
      }
      fileInput.value = ''; // 동일 파일 다시 선택 가능하도록 초기화
    });

    document.body.appendChild(fileInput);
  },

  $LOCAL_BEFORE_FIRST: function (sMsg) {
    if (!!this.oPopupMgr) { return; }
    // Popup Manager에서 사용할 param
    this.htPopupOption = {
      oApp: this.oApp,
      sName: this.name,
      bScroll: false,
      sProperties: "",
      sUrl: ""
    };
    this.oPopupMgr = nhn.husky.PopUpManager.getInstance(this.oApp);
  },

  /**
   * 포토 웹탑 오픈 -> [사진] 버튼 클릭 이벤트 처리 (팝업창 생성 대신 파일 창 오픈)
   */
  $ON_ATTACHPHOTO_OPEN_WINDOW: function () {
    const fileInput = document.getElementById('se2_file_input');
    if (fileInput) {
      fileInput.click();
    }
  },

  /**
   * FileUtil을 이용한 이미지 업로드 및 에디터 본문 삽입
   */
  _uploadImageFile: async function (file) {
    if (!file.type.startsWith('image/')) {
      alert('이미지 파일만 선택 가능합니다.');
      return;
    }

    const self = this;

    const FileUtil = window.FileUtil || (window.top && window.top.FileUtil) || (window.parent && window.parent.FileUtil);

    if (!FileUtil) {
      alert('FileUtil 모듈이 로드되지 않았습니다.');
      return;
    }

    try {
      // 공통 S3Uploader 모듈로 S3 직접 업로드 수행 (Enum: 'BOARD' 사용)
      const result = await FileUtil.upload(file);

      const downloadUrl = await FileUtil.getDownloadUrl(result.s3Key, result.originalName);

      // 에디터 본문에 HTML 태그 삽입
      const imgTag = `<img src="${downloadUrl}" data-s3-key="${result.s3Key}" style="max-width:100%; height:auto;"><br><br>`;
      self.oApp.exec('PASTE_HTML', [imgTag]);
    } catch (err) {
      console.error('SmartEditor 이미지 업로드 오류:', err);
      alert('이미지 업로드에 실패했습니다.');
    }
  },

  /**
   * 서비스별로 팝업에  parameter를 추가하여 URL을 생성하는 함수	 
   * nhn.husky.SE2M_AttachQuickPhoto.prototype.makePopupURL로 덮어써서 사용하시면 됨.
   */
  makePopupURL: function () {
    return './sample/photo_uploader/photo_uploader.html';
  },

  /**
   * 팝업에서 호출되는 메세지.
   */
  $ON_SET_PHOTO: function (aPhotoData) {
    if (!aPhotoData) return;
    try {
      let sContents = "";
      for (let i = 0; i < aPhotoData.length; i++) {
        const htData = aPhotoData[i];
        const aPhotoInfo = {
          sName: htData.sFileName || "",
          sOriginalImageURL: htData.sFileURL,
          bNewLine: htData.bNewLine || false
        };
        sContents += this._getPhotoTag(aPhotoInfo);
      }
      this.oApp.exec('PASTE_HTML', [sContents]);
    } catch (e) {
      return false;
    }
  },

  /**
   * @use 일반 포토 tag 생성
   */
  _getPhotoTag: function (htPhotoInfo) {
    var sTag = '<img src="{=sOriginalImageURL}" title="{=sName}" style="max-width:100%;">';
    if (htPhotoInfo.bNewLine) {
      sTag += '<br style="clear:both;">';
    }
    return jindo.$Template(sTag).process(htPhotoInfo);
  }
});