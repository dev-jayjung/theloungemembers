/**
 * 공통 UI 마스킹 및 디폴트 이벤트 핸들러
 */
const UiCommon = (() => {
  return {

    /**
     * jQuery UI Datepicker 및 마스킹
     */
    datepicker() {
      const $datepicker = $('.datepicker');

      $datepicker.datepicker({
        dateFormat: 'yy-mm-dd',
        prevText: '이전달',
        nextText: '다음달',
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        showMonthAfterYear: true,
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        currentText: '오늘 날짜',
        closeText: '닫기'
      }).mask('9999-99-99');;

      $('.datepicker').off('blur.dp').on('blur.dp', e => {
        const $this = $(e.currentTarget);
        const val = $this.val();
        if (ValidationUtil.isNotEmpty(val) && !ValidationUtil.isValidDate(val)) {
          $this.val('').focus();
          alert('올바르지 않은 날짜입니다.');
        }
      });

      $('.datepickerBtn').off('click.dp').on('click.dp', e => $(e.currentTarget).prev().focus());
    },

    /**
     * 숫자 전용 입력 (한글 제한)
     */
    numeric() {
      $('.numeric').css('ime-mode', 'disabled').mask('#0', { reverse: true, maxlength: false });
    },

    /**
     * 소수점 입력 (autoNumeric)
     */
    decimal() {
      $('.decimal').css('ime-mode', 'disabled').autoNumeric('init', {
        aSep: ',', aDec: '.', vMax: '9999999999999.9', vMin: '-9999999999999.9'
      });
    },

    /**
     * 전화번호 하이픈(-) 자동 마스킹
     */
    phoneNumber() {
      const $phone = $('.phoneNumber');

      // 입력 중 숫자만 추출 후 FormatUtil.phone으로 하이픈 변환
      $phone.off('input.phone blur.phone').on('input.phone blur.phone', e => {
        const $this = $(e.currentTarget);
        const rawVal = $this.val();
        if (ValidationUtil.isNotEmpty(rawVal)) {
          $this.val(FormatUtil.phone(rawVal));
        }
      });
    },

    /**
     * 천단위 콤마 포맷팅
     */
    comma() {
      const $comma = $('.comma');

      $comma.off('blur.comma').on('blur.comma', e => {
        const $this = $(e.currentTarget);
        const rawVal = FormatUtil.removeComma($this.val());
        if (ValidationUtil.isNotEmpty(rawVal)) {
          $this.val(FormatUtil.addComma(rawVal));
        } else {
          $this.val('');
        }
      });
    },

    /**
     * 비율/수수료율 입력
     */
    rate() {
      $('.rate').css('ime-mode', 'disabled').autoNumeric('init', {
        aSep: ',', aDec: '.', vMax: '999.99', vMin: '-999.99'
      });

      $('.rate').off('blur.rate').on('blur.rate', e => {
        const $this = $(e.currentTarget);
        if (ValidationUtil.isEmpty($this.val())) {
          $this.val('0.00');
        }
      });
    },

    /**
     * 전체 UI 마스킹 및 이벤트 초기화
     */
    initAll() {
      this.datepicker();
      this.numeric();
      this.phoneNumber();
      this.comma();
      this.decimal();
      this.rate();
    }
  };
})();

// ==========================================
// 전역 이벤트 바인딩
// ==========================================
$(document).ready(() => {
  UiCommon.initAll();
});

// Form Submit 시 다운로드가 아닌 경우 로딩창 표출
$(document).on('submit', 'form', e => {
  const $form = $(e.currentTarget);
  const target = $form.attr('target');
  const action = $form.attr('action') || '';
  const isDownload = action.includes('/common/fileDownloadResult.do') || action.includes('Download.do');

  if (!target && !isDownload) {
    if (typeof LoadingUtil !== 'undefined') {
      LoadingUtil.start();
    }
  }
});

// Text Input 포커스 시 전체 선택
$(document).on('focus', 'input[type=text]', e => $(e.currentTarget).select());

// Input 엔터키 Submit 방지 (pg-input 제외)
$(document).on('keydown', 'input', e => {
  if (!$(e.currentTarget).hasClass('ui-pg-input') && e.keyCode === 13) {
    return false;
  }
});

// 탭 버튼 공통 클릭 이벤트
$(document).on('click', '.mTab1 .tabMenu a[data-val]', e => {
  e.preventDefault();
  const $this = $(e.currentTarget);
  const tabVal = $this.data('val');

  $this.closest('ul').find('li').removeClass('active');
  $this.closest('li').addClass('active');

  $this.trigger('tabChange', [tabVal]);
});

const goUrl = url => FormUtil.openTab({ url });