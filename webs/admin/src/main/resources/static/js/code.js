var codeAjax = {
    select : function(config) {
        var grpCd = config.grpCd;
        var selectKey = config.selectKey ? config.selectKey : '';
        var usrDfn1Val = config.usrDfn1Val ? config.usrDfn1Val : '';
        var usrDfn2Val = config.usrDfn2Val ? config.usrDfn2Val : '';
        var usrDfn3Val = config.usrDfn3Val ? config.usrDfn3Val : '';
        var usrDfn4Val = config.usrDfn4Val ? config.usrDfn4Val : '';
        var usrDfn5Val = config.usrDfn5Val ? config.usrDfn5Val : '';
        var defaultName = config.defaultName ? config.defaultName : '';
        var showValue = config.showValue ? config.showValue : false;

        var options = {
            url : "/common/codeSelectView.do"
            , data : {
                grpCd : grpCd
                , selectKey : selectKey
                , usrDfn1Val : usrDfn1Val
                , usrDfn2Val : usrDfn2Val
                , usrDfn3Val : usrDfn3Val
                , usrDfn4Val : usrDfn4Val
                , usrDfn5Val : usrDfn5Val
                , defaultName : defaultName
                , showValue : showValue
            }
            , dataType : "html"
            , callBack : function(data){
                config.callBack(data);
            }
        };
        ajax.call(options);
    }
    , getUsrDfnVal : function (config) {
        var grpCd = config.grpCd;
        var dtlCd = config.dtlCd;
        var usrDfnValIdx = config.usrDfnValIdx ? config.usrDfnValIdx : 1;
        var options = {
            url : "/common/codeUsrDfnValView.do"
            , data : {
                grpCd : grpCd
                , dtlCd : dtlCd
                , usrDfnValIdx : usrDfnValIdx
            }
            , dataType : "html"
            , callBack : function(data ) {
                config.callBack(data);
            }
        };
        ajax.call(options);
    }
}