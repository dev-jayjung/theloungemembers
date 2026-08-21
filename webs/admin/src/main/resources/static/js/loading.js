const LoadingUtil = (() => {
  return {
    start(msg) {
      const messageHtml = msg
        ? `<div style="text-align: center; background-color:#FFF; padding:10px"><div>${msg}</div><img src="/images/ajax-loader-white.gif" alt="Loading..." /></div>`
        : '<img src="/images/ajax-loader-white.gif" alt="Loading..." />';
      $.blockUI({ message: messageHtml });
    },

    startZip() {
      this.start('상품 압축풀기 및 적용에 시간이 걸립니다. 기다려주세요.');
    },

    stop() {
      $.unblockUI();
    }
  };
})();