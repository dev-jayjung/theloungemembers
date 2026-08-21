/**
 * 입력값 유효성 검증 유틸리티
 */
const ValidationUtil = (() => {
  const _ = window._;
  const dayjs = window.dayjs;
  const REGEX = {
    PASSWORD: /^(?=.*[a-zA-Z]+)(?=.*[0-9]+)(?=.*[~!@#$%^&*]+).{8,20}$/,
    SPECIAL_CHAR: /[<>]/,
    DATE_FORMAT: /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/
  };

  return {
    /**
     * 빈값 검증 (null, undefined, "", "  ", 빈 배열/객체 지원)
     * @param {*} val 
     * @returns {Boolean}
     */
    isEmpty(val) {
      if (val === null || val === undefined) {
        return true;
      }

      if (typeof val === 'string') {
        return val.trim().length === 0 || val === 'null';
      }

      // Lodash가 있으면 배열/객체 빈값 체크 활용
      if (_) {
        return _.isEmpty(val);
      }

      // Native fallback (배열 및 일반 객체)
      if (Array.isArray(val)) {
        return val.length === 0;
      }

      if (typeof val === 'object') {
        return Object.keys(val).length === 0;
      }

      return false;
    },

    /**
     * 값이 존재하는지 체크
     * @param {*} val 
     * @returns {Boolean}
     */
    isNotEmpty(val) {
      return !this.isEmpty(val);
    },

    /**
     * 비밀번호 규칙 검증 (영문, 숫자, 특수문자 조합 8~20자)
     * @param {String} pw 
     * @returns {Boolean}
     */
    isValidPassword(pw) {
      if (this.isEmpty(pw)) {
        return false;
      }

      return REGEX.PASSWORD.test(String(pw));
    },

    /**
     * XSS 위험 특수문자(<, >) 포함 여부 체크
     * @param {String} str 
     * @returns {Boolean}
     */
    hasSpecialChar(str) {
      if (this.isEmpty(str)) {
        return false;
      }

      return REGEX.SPECIAL_CHAR.test(String(str));
    },

    /**
     * 날짜 유효성 검증 (YYYY-MM-DD 또는 YYYY/MM/DD)
     * @param {String} dateStr 
     * @returns {Boolean}
     */
    isValidDate(dateStr) {
      if (this.isEmpty(dateStr)) {
        return false;
      }

      const str = String(dateStr).trim();

      // Day.js가 로드되어 있는 경우 우선 활용
      if (dayjs) {
        return dayjs(str, ['YYYY-MM-DD', 'YYYY/MM/DD'], true).isValid();
      }

      // Native Fallback
      if (!REGEX.DATE_FORMAT.test(str)) {
        return false;
      }
      const [year, month, day] = str.split(/[-/]/).map(Number);

      const date = new Date(year, month - 1, day);

      return (
        date.getFullYear() === year &&
        date.getMonth() === month - 1 &&
        date.getDate() === day
      );
    }
  };
})();