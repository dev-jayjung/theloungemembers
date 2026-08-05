(function() {
    openByclose = function(param) {
        let closeUrl = param.closeUrl;
        if (closeUrl.indexOf('.do?') > 0) {
            closeUrl = closeUrl.substr(0, closeUrl.indexOf('.do?')) + '.do';
        }

        const tabId = top.tabList.getTabId(closeUrl);
        if (tabId != '') {
            closeTab(tabId);
        }

        const openUrl = param.openUrl;
        openTab({
            url: openUrl
        });
    };

    openWindowByclose = function(param) {
        const openUrl = param.openUrl;
        top.open(openUrl, param.winName);

        let closeUrl = param.closeUrl;
        if (closeUrl.indexOf('.do?') > 0) {
            closeUrl = closeUrl.substr(0, closeUrl.indexOf('.do?')) + '.do';
        }

        const tabId = top.tabList.getTabId(closeUrl);
        if (tabId != '') {
            closeTab(tabId);
        }
    };

    closeTab = function(param) {
        let tabId = null;

        if (param.type && param.type == 'url') {
            let closeUrl = param.closeUrl;
            if (closeUrl.indexOf('.do?') > 0) {
                closeUrl = closeUrl.substr(0, closeUrl.indexOf('.do?')) + '.do';
            }

            tabId = top.tabList.getTabId(closeUrl);
        } else {
            tabId = param;
        }

        top.tabList.close({
            tabId: tabId
        });
    };

    clickTab = function(tabId) {
        top.tabList.activeTab({
            tabId: tabId
        });
    };

    openHome = function() {
        openTab({
            url: '/main/dashboard',
            isHome: true,
            tabName: '홈'
        });
    };

    openTab = function(param) {
        let tabName = param.tabName;
        let url = param.url;
        if (url.indexOf('.do?') > 0) {
            url = url.substr(0, url.indexOf('.do?')) + '.do';
        }
        
        if (!tabName) {
            tabName = url;
        }
//        
//        $.ajax({
//            url:'/main/getScreenName.do',
//            type : 'POST',
//            dataType:'json',
//            async:false,
//            data:{
//                uri:url
//            },
//            success:function(res) {
//                tabName = res.screenName;
//            },
//            error:function() {
//                tabName = '';
//            }
//        });

        return top.tabList.open({
                    url: url,
                    realUrl: param.url,
                    name: tabName,
                    isHome: param.isHome
                });
    };

    closeAllTab = function() {
        const tabCount = top.tabList.list.length;

        if (tabCount > 1) {
            closeTab(top.tabList.list[tabCount - 1].tabId);
            setTimeout('closeAllTab()', 50);
        }
    };

    _resizeTabWindow = function() {
        //현재 활성화된 탭조회
        if (top.tabList == null || top.tabList =='undefined') {
            return;
        } else {
            const tabCount = top.tabList.list.length;
            const activeTabId = tabList.activeList[tabList.activeList.length - 1];

            const $div = $('#screenList > div[id="div_' + activeTabId + '"]');
            const $side = $('#side');

            const iframeBody = window['iframe_' + activeTabId].document.body;
            const iframeHeight = iframeBody.scrollHeight;

            if (iframeHeight > defaultHeight) {
                $div.height(iframeHeight);
                $side.height(iframeHeight + footerHeight);
            } else {
                $div.height(defaultHeight);
                $side.height(defaultHeight + footerHeight);
            }
        }
    };

    resizeTabWindow = function() {
        top._resizeTabWindow();
    };

})(window);