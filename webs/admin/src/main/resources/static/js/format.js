/**
 * 문자열/숫자 포맷팅 및 변환 유틸리티
 */
const FormatUtil = (() => {
  const numberFormatter = new Intl.NumberFormat('ko-KR');

  return {
    /**
     * 천단위 콤마 추가 (숫자, 문자열 모두 대응)
     * @param {Number|String} val 
     * @param {String} fallback - 빈값일 경우 반환할 기본값
     * @returns {String}
     */
    addComma(val, fallback = '0') {
      if (ValidationUtil.isEmpty(val)) {
        return fallback;
      }

      const num = Number(String(val).replace(/[^0-9.-]/g, ''));
      if (isNaN(num)) {
        return fallback;
      }

      return numberFormatter.format(num);
    },

    /**
     * 콤마 및 숫자가 아닌 문자를 모두 제거하고 순수 숫자/소수점만 추출
     * @param {String|Number} val 
     * @returns {String}
     */
    removeComma(val) {
      if (ValidationUtil.isEmpty(val)) {
        return '';
      }

      return String(val).replace(/[^0-9.]/g, '');
    },

    /**
     * 전화번호 하이픈(-) 자동 포맷팅 (02, 010, 대표번호 등 자동 정렬)
     * @param {String} val 
     * @returns {String}
     */
    phone(val) {
      if (ValidationUtil.isEmpty(val)) {
        return '';
      }

      const clean = String(val).replace(/[^0-9]/g, '');

      if (clean.length === 8) {
        // 1588-XXXX 등 대표번호
        return clean.replace(/^(\d{4})(\d{4})$/, '$1-$2');
      }

      return clean
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, '$1-$2-$3')
        .replace(/(--)+/g, '-');
    },

    /**
     * 전화번호 가운데 자리 마스킹 (010-****-1234, 02-***-1234)
     * @param {String} val 
     * @returns {String}
     */
    maskPhone(val) {
      if (ValidationUtil.isEmpty(val)) {
        return '-';
      }

      const clean = String(val).replace(/[^0-9]/g, '');

      if (clean.length < 9) {
        return this.phone(clean);
      }

      return clean.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, (match, p1, p2, p3) => {
        return `${p1}-${'*'.repeat(p2.length)}-${p3}`;
      });
    },

    /**
     * 사업자등록번호 포맷팅 (000-00-00000)
     * @param {String} val 
     * @returns {String}
     */
    bizNo(val) {
      if (ValidationUtil.isEmpty(val)) {
        return '';
      }

      const clean = String(val).replace(/[^0-9]/g, '');

      return clean.replace(/^(\d{3})(\d{2})(\d{5})$/, '$1-$2-$3');
    },

    /**
     * 법인등록번호 포맷팅 (000000-0000000)
     * @param {String} val 
     * @returns {String}
     */
    cprNo(val) {
      if (ValidationUtil.isEmpty(val)) {
        return '';
      }

      const clean = String(val).replace(/[^0-9]/g, '');

      return clean.replace(/^(\d{6})(\d{7})$/, '$1-$2');
    },

    /**
     * 우편번호 포맷팅 (000-000)
     * @param {String} val 
     * @returns {String}
     */
    post(val) {
      if (ValidationUtil.isEmpty(val)) {
        return '';
      }

      const clean = String(val).replace(/[^0-9]/g, '');

      return clean.replace(/^(\d{3})(\d{3})$/, '$1-$2');
    },

    /**
     * YYYYMMDD -> YYYY년 MM월 DD일 변환
     * @param {String} val 
     * @returns {String}
     */
    birth(val) {
      if (ValidationUtil.isEmpty(val)) {
        return '';
      }

      const clean = String(val).replace(/[^0-9]/g, '');

      return clean.replace(/^(\d{4})(\d{2})(\d{2})$/, '$1년 $2월 $3일');
    },

    /**
     * 소수점 반올림
     * @param {Number|String} n 
     * @param {Number} pos 자릿수
     * @returns {Number}
     */
    round(n, pos = 0) {
      if (ValidationUtil.isEmpty(n) || isNaN(n)) {
        return 0;
      }

      const num = Number(n);

      return Number(Math.round(Number(`${num}e${pos}`)) + `e-${pos}`);
    },

    /**
     * 소수점 절사 (버림)
     * @param {Number|String} n 
     * @param {Number} pos 자릿수
     * @returns {Number}
     */
    floor(n, pos = 0) {
      if (ValidationUtil.isEmpty(n) || isNaN(n)) {
        return 0;
      }

      const num = Number(n);

      return Number(Math.floor(Number(`${num}e${pos}`)) + `e-${pos}`);
    },

    /**
     * 소수점 올림
     * @param {Number|String} n 
     * @param {Number} pos 자릿수
     * @returns {Number}
     */
    ceil(n, pos = 0) {
      if (ValidationUtil.isEmpty(n) || isNaN(n)) {
        return 0;
      }

      const num = Number(n);

      return Number(Math.ceil(Number(`${num}e${pos}`)) + `e-${pos}`);
    }
  };
})();