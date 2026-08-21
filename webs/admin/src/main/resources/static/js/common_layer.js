/**
 * 도메인별 공통 레이어 팝업 모듈
 */
const CommonPopup = (() => {
  return {
    /**
     * API Member 검색 팝업
     */
    memberSearch(options = {}) {
      const defaultData = {

      };

      AjaxUtil.call({
        url: '/sample/grid-layer',
        type: 'GET',
        dataType: 'html',
        data: $.extend({}, defaultData, options.param),
        wait: true,
        callBack: htmlResult => {
          LayerUtil.open({
            id: 'layerCompanyView',
            width: 1200,
            title: '업체 조회',
            body: htmlResult,
            onConfirm: $layer => {
              // 확인 버튼 클릭 시 선택된 데이터를 추출
              const selectedRows = Tabulator.findTable('#apiMemberList')[0].getSelectedData();

              // 특정 컬럼 값만 추출
              const selectedIds = selectedRows.map(row => row.uid);

              if (!selectedIds) {
                alert('항목을 선택해주세요.');
                return;
              }

              // 콜백 함수가 있으면 결과 데이터 전달
              if (typeof options.callBack === 'function') {
                options.callBack([selectedIds]);
              }
            }
          });
        },
        fail: xhr => console.error('팝업 로드 실패:', xhr)
      });
    }
  };
})();