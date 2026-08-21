const DateUtil = (() => {
  // 표준 날짜 포맷 정의
  const FORMAT = {
    DATE: 'YYYY-MM-DD',
    TIME: 'HH:mm:ss',
    DATETIME: 'YYYY-MM-DD HH:mm:ss',
    DATETIME_SHORT: 'YYYY-MM-DD HH:mm',
    MONTH_DAY: 'MM-DD',
  };

  /**
   * 입력값을 Day.js 객체로 안전하게 변환 (내부 헬퍼)
   */
  const _toDayjs = value => {
    if (!value) {
      return null;
    }

    // Thymeleaf에서 LocalDateTime 등이 넘어올 때 배열 [2026, 8, 18, 9, 45] 형태나 ISO 문자열로 오는 경우 대응
    const d = dayjs(value);

    return d.isValid() ? d : null;
  };

  return {
    FORMAT,

    /**
     * 날짜 포맷팅 (null/유효하지 않은 값 처리 포함)
     * @param {string|number|Date} value - 날짜 값
     * @param {string} [formatPattern=FORMAT.DATE] - 포맷 양식
     * @param {string} [defaultValue='-'] - null 또는 유효하지 않을 때 반환할 기본값
     */
    format(value, formatPattern = FORMAT.DATE, defaultValue = '-') {
      const d = _toDayjs(value);

      return d ? d.format(formatPattern) : defaultValue;
    },

    /**
     * 날짜 + 시간 포맷팅 (YYYY-MM-DD HH:mm:ss)
     */
    formatDateTime(value, defaultValue = '-') {
      return this.format(value, FORMAT.DATETIME, defaultValue);
    },

    /**
     * 오늘 날짜 가져오기
     */
    today(formatPattern = FORMAT.DATE) {
      return dayjs().format(formatPattern);
    },

    /**
     * 상대 시간 표시 (예: "3분 전", "2시간 전", "어제")
     * ※ dayjs/plugin/relativeTime 필요 (한국어 설정: dayjs.locale('ko'))
     */
    fromNow(value, defaultValue = '-') {
      const d = _toDayjs(value);
      return d ? d.fromNow() : defaultValue;
    },

    /**
     * 오늘/특정일 기준 날짜 범위를 Quick하게 생성 (검색 버튼용: 오늘, 1주일, 1개월, 3개월)
     * @param {number} amount - 차감할 숫자
     * @param {string} [unit='month'] - 단위 ('day', 'week', 'month')
     * @returns {{startDate: string, endDate: string}}
     */
    getDateRange(amount, unit = 'month') {
      const end = dayjs().format(FORMAT.DATE);
      const start = dayjs().subtract(amount, unit).format(FORMAT.DATE);

      return {
        startDate: start,
        endDate: end
      };
    },

    /**
     * 퀵 버튼 클릭 시 target input에 날짜 바인딩
     */
    setRange(btn, amount, unit) {
      const $btn = $(btn);
      const $group = $btn.closest('.btn-group-quick-date');
      const startTarget = $group.data('start-target');
      const endTarget = $group.data('end-target');

      const { startDate, endDate } = this.getDateRange(amount, unit);
      $(startTarget).val(startDate);
      $(endTarget).val(endDate);

      $group.find('button').removeClass('btn_type2').addClass('btn_type1');
      $btn.removeClass('btn_type1').addClass('btn_type2');
    },

    /**
     * 날짜 더하기 / 빼기
     * @param {string|number|Date} value - 기준 날짜
     * @param {number} amount - 더할 숫자 (음수면 빼기)
     * @param {string} [unit='day'] - 단위 ('day', 'month', 'year', 'hour' 등)
     * @param {string} [formatPattern=FORMAT.DATE]
     */
    add(value, amount, unit = 'day', formatPattern = FORMAT.DATE) {
      const d = _toDayjs(value);
      return d ? d.add(amount, unit).format(formatPattern) : '-';
    },

    /**
     * 두 날짜 사이의 차이 계산
     * @param {string|number|Date} floatDate - 비교 대상 날짜
     * @param {string|number|Date} baseDate - 기준 날짜 (기본값: 오늘)
     * @param {string} [unit='day'] - 단위 ('day', 'month', 'year' 등)
     */
    diff(floatDate, baseDate = new Date(), unit = 'day') {
      const d1 = _toDayjs(floatDate);
      const d2 = _toDayjs(baseDate);

      if (!d1 || !d2) {
        return 0;
      }

      return d1.diff(d2, unit);
    },

    /**
     * 두 날짜 사이인지 확인 (시작일 <= 대상 <= 종료일)
     */
    isBetween(targetDate, startDate, endDate) {
      const target = _toDayjs(targetDate);
      const start = _toDayjs(startDate);
      const end = _toDayjs(endDate);

      if (!target || !start || !end) {
        return false;
      }

      // 시작일과 종료일을 포함하는 경우
      return (target.isAfter(start) || target.isSame(start)) &&
        (target.isBefore(end) || target.isSame(end));
    },

    /**
     * 오늘 여부 확인
     */
    isToday(value) {
      const d = _toDayjs(value);
      return d ? d.isSame(dayjs(), 'day') : false;
    },

    /**
     * 기간 검색용 (시작일/종료일 검증)
     * 시작일이 종료일보다 이후인지 확인
     */
    isAfter(startDate, endDate) {
      const start = _toDayjs(startDate);
      const end = _toDayjs(endDate);

      if (!start || !end) {
        return false;
      }

      return start.isAfter(end);
    },

    /**
     * 요일 구하기 (월, 화, 수...)
     * @param {string|number|Date} value 
     * @param {boolean} [withBracket=false] - true면 "(월)" 형태로 반환
     */
    dayOfWeek(value, withBracket = false) {
      const d = _toDayjs(value);
      if (!d) {
        return '-';
      }

      const day = d.format('ddd'); // ko.js 설정 시 "월", "화" 반환

      return withBracket ? `(${day})` : day;
    },

    /**
     * 날짜 + 요일 조합 (YYYY-MM-DD (월))
     */
    formatWithDay(value, defaultValue = '-') {
      const d = _toDayjs(value);
      return d ? `${d.format('YYYY-MM-DD')} (${d.format('ddd')})` : defaultValue;
    },

    /**
     * 특정 날짜가 속한 달의 시작일 / 마지막일 구하기 (달력/조회 조건용)
     * @param {string|number|Date} value - 기준 날짜 (기본값: 오늘)
     * @param {'start'|'end'} type - 시작일 또는 마지막일
     */
    monthBoundary(value = new Date(), type = 'start') {
      const d = _toDayjs(value);

      if (!d) {
        return '-';
      }

      return type === 'start'
        ? d.startOf('month').format(FORMAT.DATE)
        : d.endOf('month').format(FORMAT.DATE);
    },

    /**
     * D-Day 계산 (D-5, D+3, D-Day)
     */
    getDDay(targetDate) {
      const target = _toDayjs(targetDate);
      if (!target) {
        return '-';
      }

      const today = dayjs().startOf('day');
      const diff = target.startOf('day').diff(today, 'day');

      if (diff === 0) {
        return 'D-Day';
      }

      return diff > 0 ? `D-${diff}` : `D+${Math.abs(diff)}`;
    }
  };
})();

// relativeTime 플러그인 활성화 (d.fromNow 함수 생성)
dayjs.extend(window.dayjs_plugin_relativeTime);

// 한국어("3분 전", "어제" 등) 설정
dayjs.locale('ko');