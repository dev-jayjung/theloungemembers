/**
 * 백오피스 탭(Tab) 및 프레임 제어 유틸리티
 */
const TabUtil = (() => {

  // 내부 URL 정제 헬퍼 (QueryString 및 ? 이하 제거)
  const cleanUrl = url => {
    if (!url) {
      return '';
    }

    const index = url.indexOf('?');

    return index > 0 ? url.substring(0, index) : url;
  };

  /**
     * 객체/배열 데이터를 URL Query String으로 변환
     * @param {String} url 
     * @param {Object|Array} data 
     * @returns {String}
     */
  const buildUrl = (url = '', data = null) => {
    if (!data) {
      return url;
    }

    const params = new URLSearchParams();

    const appendParam = obj => {
      if (!obj || typeof obj !== 'object') {
        return;
      }

      Object.entries(obj).forEach(([key, value]) => {
        params.append(key, value ?? '');
      });
    };

    if (Array.isArray(data)) {
      data.forEach(item => appendParam(item));
    } else if (typeof data === 'object') {
      appendParam(data);
    }

    const queryString = params.toString();
    if (!queryString) {
      return url;
    }

    return url.includes('?') ? `${url}&${queryString}` : `${url}?${queryString}`;
  };

  // Top Window의 tabList 안전 조회
  const getTopTabList = () => {
    return (window.top && window.top.tabList) ? window.top.tabList : null;
  };

  return {
    /**
     * URL 경로 기준 Tab ID 추출
     */
    getTabIdByUrl(url) {
      const tabList = getTopTabList();
      if (!tabList) {
        return '';
      }
      return tabList.getTabId(cleanUrl(url)) || '';
    },

    /**
     * 새 탭 오픈 (data 객체자동 URL 파싱)
     * @param {Object} param - { url, data, tabName, isHome }
     */
    async openTab(param = {}) {
      const tabList = getTopTabList();
      if (!tabList) {
        return;
      }

      let { tabName, url, data, isHome } = param;
      // data 파라미터가 있으면 URL에 쿼리스트링으로 머지
      const fullUrl = buildUrl(url, data);
      const pureUrl = cleanUrl(url);

      // 탭 이름이 지정되지 않은 경우 메뉴명 조회
      if (!tabName && typeof AjaxUtil !== 'undefined') {
        try {
          const res = await new Promise(resolve => {
            AjaxUtil.call({
              url: `/api/v1/admin-menus/by-url?url=${encodeURIComponent(pureUrl)}`,
              type: 'GET',
              wait: false,
              noAlert: true,
              callBack: resolve,
              fail: () => resolve(null)
            });
          });

          if (res && res.success && res.data) {
            tabName = res.data.subTitle;
          }
        } catch (e) {
          console.error('메뉴명 조회 실패:', e);
        }
      }

      return tabList.open({
        url: pureUrl,
        realUrl: fullUrl,
        name: tabName || pureUrl,
        isHome: !!isHome
      });
    },

    /**
     * 현재 탭 닫고 새 탭 열기
     * @param {Object} param - { closeUrl, openUrl, data }
     */
    openTabAndCloseSelf(param = {}) {
      const { closeUrl, openUrl, data } = param;

      if (closeUrl) {
        TabUtil.closeTabByUrl(closeUrl);
      }

      if (openUrl) {
        TabUtil.openTab({ url: openUrl, data });
      }
    },

    /**
     * 현재 탭 닫고 윈도우 팝업 열기
     * @param {Object} param - { openUrl, data, winName, closeUrl }
     */
    openWindowAndCloseSelf(param = {}) {
      const { openUrl, data, winName = '_blank', closeUrl } = param;
      const fullUrl = buildUrl(openUrl, data);

      if (fullUrl) {
        window.top.open(fullUrl, winName);
      }

      if (closeUrl) {
        TabUtil.closeTabByUrl(closeUrl);
      }
    },

    /**
     * 현재 탭 닫기
     */
    closeSelf() {
      const curUrl = cleanUrl(window.location.pathname);
      this.closeTabByUrl(curUrl);
    },

    /**
     * Tab ID 기준 탭 닫기
     * @param {String} tabId 
     */
    closeTab(tabId) {
      if (!tabId) {
        return;
      }

      const tabList = getTopTabList();
      if (tabList) {
        tabList.close(tabId);
      }
    },

    /**
     * URL 경로 기준 탭 닫기
     * @param {String} closeUrl 
     */
    closeTabByUrl(closeUrl) {
      if (!closeUrl) {
        return;
      }
      const targetTabId = TabUtil.getTabIdByUrl(closeUrl);
      if (targetTabId) {
        TabUtil.closeTab(targetTabId);
      }
    },

    /**
     * 특정 탭 활성화
     */
    activateTab(tabId) {
      const tabList = getTopTabList();
      if (tabList && tabId) {
        tabList.activeTab({ tabId });
      }
    },

    /**
     * 홈(대시보드) 탭 열기
     */
    openHome() {
      TabUtil.openTab({
        url: '/main/dashboard',
        isHome: true,
        tabName: '홈'
      });
    },

    /**
     * 열려있는 모든 탭 순차 닫기
     */
    closeAllTabs() {
      const tabList = getTopTabList();
      if (!tabList || !tabList.list) {
        return;
      }

      const list = tabList.list;
      if (list.length > 1) {
        const lastTab = list[list.length - 1];
        TabUtil.closeTab(lastTab.tabId);

        setTimeout(() => TabUtil.closeAllTabs(), 50);
      }
    },

    /**
     * 탭 높이 리사이징
     */
    resizeTabWindow() {
      const tabList = getTopTabList();
      if (!tabList || !tabList.list || !tabList.activeList) {
        return;
      }

      const activeTabId = tabList.activeList[tabList.activeList.length - 1];
      if (!activeTabId) {
        return;
      }

      const $div = $(`#screenList > div[id="div_${activeTabId}"]`);
      const $side = $('#side');
      const iframeWin = window[`iframe_${activeTabId}`];

      if (!iframeWin || !iframeWin.document || !iframeWin.document.body) {
        return;
      }

      const iframeHeight = iframeWin.document.body.scrollHeight;
      const defaultH = typeof defaultHeight !== 'undefined' ? defaultHeight : 600;
      const footerH = typeof footerHeight !== 'undefined' ? footerHeight : 0;

      if (iframeHeight > defaultH) {
        $div.height(iframeHeight);
        $side.height(iframeHeight + footerH);
      } else {
        $div.height(defaultH);
        $side.height(defaultH + footerH);
      }
    }
  };
})();