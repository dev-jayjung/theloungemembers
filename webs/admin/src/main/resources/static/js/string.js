/**
 * 공통 StringUtil (Lodash Wrapper)
 */
const StringUtil = (() => {
  // Lodash 객체 존재 여부 체크
  const _ = window._;

  return {
    /**
     * 빈값 체크 (null, undefined, "", "  ")
     */
    isEmpty(str) {
      return ValidationUtil.isEmpty(str);
    },

    isNotEmpty(str) {
      return ValidationUtil.isNotEmpty(str);
    },

    /**
     * 좌우 공백 제거 (Null Safe)
     * null이나 undefined가 들어와도 에러 없이 빈 문자열('') 반환
     */
    trim(str) {
      if (this.isEmpty(str)) {
        return '';
      }

      return _ ? _.trim(str) : String(str).trim();
    },

    /**
     * 모든 공백 완전 제거 (문자열 중간 공백까지 제거)
     * 예: " 010 - 1234 - 5678 " -> "010-1234-5678"
     */
    stripSpace(str) {
      if (this.isEmpty(str)) {
        return '';
      }

      return String(str).replace(/\s+/g, '');
    },

    /**
     * 문자열 전체 치환 (replaceAll)
     * @param {string} str - 원본 문자열
     * @param {string} target - 찾을 문자열
     * @param {string} replacement - 교체할 문자열
     */
    replaceAll(str, target, replacement = '') {
      if (this.isEmpty(str) || target === undefined || target === null) {
        return str || '';
      }

      // Lodash의 replace 활용 (정규식 이스케이프 자동 처리)
      if (_) {
        // target을 정규식 특수문자 안전 처리 후 global('g') 옵션으로 전체 치환
        const escapedTarget = target.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
        return _.replace(String(str), new RegExp(escapedTarget, 'g'), replacement);
      }

      // Lodash 없을 때 Native fallback
      return String(str).split(target).join(replacement);
    },

    /**
     * 기본값 설정
     */
    defaultIfEmpty(str, defaultStr = '') {
      return this.isEmpty(str) ? defaultStr : str;
    },

    /**
     * 말줄임표 처리 (Truncate)
     * 예: StringUtil.truncate("긴 제목입니다", 5) -> "긴 제목..."
     */
    truncate(str, length = 10, omission = '...') {
      if (this.isEmpty(str)) {
        return '';
      }

      return _ ? _.truncate(str, { length, omission }) : str.substring(0, length) + omission;
    },

    /**
     * 전화번호 마스킹
     */
    maskPhone(phone) {
      if (this.isEmpty(phone)) {
        return '-';
      }

      const clean = String(phone).replace(/[^0-9]/g, '');
      return clean.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, '$1-****-$3');
    },

    /**
     * 표기법 변환 (CamelCase, SnakeCase 등)
     */
    toCamelCase(str) {
      if (this.isEmpty(str)) {
        return '';
      }
      return _ ? _.camelCase(str) : str;
    },

    /**
     * HTML 이스케이프 (XSS 방지)
     */
    escapeHtml(str) {
      if (this.isEmpty(str)) {
        return '';
      }

      return _ ? _.escape(str) : str;
    }
  };
})();