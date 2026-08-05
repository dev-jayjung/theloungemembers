var waiting = {
    start : function(){
        $.blockUI({ message: '<img src="/images/ajax-loader-white.gif" alt="Loading..." />' });
    }
    , stop : function(){
        $.unblockUI();
    }
    , startId : function(id){
        $.blockUI({ message: $("#" + id) });
    }
    , stopId : function(){
        $.unblockUI();
    }
};

var waitingZip = {
    start : function(){
        $.blockUI({ message: '<div style="text-align: center;background-color:#FFF;padding:10px"><div >상품 압축풀기 및 적용에 시간이 걸립니다. 기다려주세요.</div> <img  src="/images/ajax-loader-white.gif" alt="Loading..." /></div>' });
    }
    , stop : function(){
        $.unblockUI();
    }
    , startId : function(id){
        $.blockUI({ message: $("#" + id) });
    }
    , stopId : function(){
        $.unblockUI();
    }
};

var gridFormat = {
    date : function(cellValue, options, rowObject) {
        if(options.colModel.dateformat != null && options.colModel.dateformat != undefined && options.colModel.dateformat != '') {
            return new Date(cellValue).format(options.colModel.dateformat);
        } else {
            return new Date(cellValue).format("yyyy-MM-dd HH:mm:ss");
        }
    }, // hjko  추가
    phonenumber : function(cellvalue, options, rowObject){
         return cellvalue == null ? "" : cellvalue.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
    },
    mbrId : function(cellvalue, options, rowObject){
        return cellvalue == null ? "" : cellvalue.substr(0, 2) + '*****';
    }
}

var valid = {
      password : /^(?=.*[a-zA-Z]+)(?=.*[0-9]+)(?=.*[~!@#$%^&*]+).{8,20}$/
    , numberWithCommas : function(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
}

var grid = {

    create : function(targetId, options){
        var sortName = options.colModels[0].name;

        if(options.paging == undefined){
            options.paging = {};
        }

        if(options.editing == undefined){
            options.editing = false;
        }

        var paging = false;
        var editing = false;
        var navmore = false;
        var pager = "";
        var rowList;
        var rowNum;
        var footerrow = false;
        var userDataOnFooter = false;

        if(options.paging){
            paging = true;
        }
        if(options.navmore){
            navmore = true;
        }
        if(options.editing){
            editing = true;
        }

        if(options.footerrow) {
            footerrow = true;
        }

        if(options.userDataOnFooter) {
            userDataOnFooter = true;
        }

        if(paging){
            pager = "#"+targetId+"Page";
            rowList = options.paging.limit || [50,100,200,300,500];
            rowNum = options.paging.rows || 50;
        }
        

        var gridCreateOption = {
            url : options.url
            , timeout : 300000
            , loadonce : options.loadonce || false
            , postData : options.searchParam
            , datatype : options.datatype || "json"
            , rownumbers : options.isRownumbers == undefined ? false : options.isRownumbers 
            , rownumWidth : 25
            , sortable: true
            , mtype : "POST"
            , autowidth : false
            , width : '100%'
            , height : options.height || 200
            , colNames : options.colNames
            , colModel : options.colModels
            , cellEdit : options.cellEdit || false
            , cellsubmit : options.cellsubmit || 'clientArray'
            , cellurl : options.cellurl
            , editurl : options.editurl
            , afterEditCell : options.afterEditCell
            , afterSaveCell : options.afterSaveCell
            , beforeEditCell : options.beforeEditCell
            , beforeSaveCell : options.beforeSaveCell
            , rowNum : rowNum || -1
            , rowList : rowList
            , pager : pager
            , viewrecords : true
            , multiselect : options.multiselect || false
            , multiboxonly : options.multiboxonly || false
            , sortname : options.sortname || sortName
            , sortorder : options.sortorder || "desc"
            , jsonReader : {
                page: "page"
                , total: "total"
                , root: "data"
                , records: "records"
                , repeatitems: false,
            }
            , loadError : function(xhr, status, error){
                if(xhr.status == 450) {
                    location.replace("/login/noSessionView.do");
                } else {
                    if(xhr.status != 0) {
                        alert("오류가 발생되었습니다. 관리자에게 문의하십시요.["+xhr.status+"]["+error+"]");
                    }
                }
            }
            , footerrow : footerrow
            , userDataOnFooter : userDataOnFooter
            , beforeSelectRow : options.beforeSelectRow
            , onSelectRow : options.onSelectRow
            , onCellSelect : options.onCellSelect == undefined ? undefined : options.onCellSelect
            , onSelectAll : options.onSelectAll == undefined ? undefined : options.onSelectAll
            , ondblClickRow : options.ondblClickRow == undefined ? null : options.ondblClickRow
            , gridComplete : options.gridComplete == undefined ? function() {
                                                                             $("#" + targetId).closest(".ui-jqgrid-bdiv").scrollTop(0);
                                                                             resizeTabWindow();
                                                                             }
                                                               :
                                                                 function() {
                                                                             $("#" + targetId).closest(".ui-jqgrid-bdiv").scrollTop(0);
                                                                             options.gridComplete.call(this); resizeTabWindow();
                                                                             }
            , loadComplete : options.loadComplete == undefined ? undefined : options.loadComplete
            , caption : options.caption == undefined ? undefined : options.caption
            , grouping: options.grouping == undefined ? undefined : options.grouping
            , groupingView: {
                groupField: options.groupField == undefined ? undefined : options.groupField
                , groupText: options.groupText == undefined ? undefined : ['<b>' + options.groupText + ' : {0}</b>']
                , groupOrder : options.groupOrder == undefined ? undefined : options.groupOrder
                , groupColumnShow : options.groupColumnShow == undefined ? undefined : options.groupColumnShow
                , groupCollapse: options.groupCollapse == undefined ? undefined : options.groupCollapse
                , groupSummary: options.groupSummary == undefined ? undefined : options.groupSummary
                , showSummaryOnHide: options.showSummaryOnHide == undefined ? undefined : options.showSummaryOnHide
                , groupDataSorted: options.showSummaryOnHide == undefined ? undefined : options.showSummaryOnHide
            }
            , ajaxGridOptions : (options.isJsonSend ? {contentType:"application/json; charset=UTF-8"} : null)
        };
        
        if (options.singleselect) {
            gridCreateOption.multiselect = true;
            
            if (options.oncyCheckboxChecked) {
                gridCreateOption.beforeSelectRow = function (rowid, e) {
                    var $grid = $(this);
                    var i = $.jgrid.getCellIndex($(e.target).closest('td')[0]);
                    var cm = $grid.jqGrid('getGridParam', 'colModel');
                    var ischeckbox = cm[i].name === 'cb';

                    if (ischeckbox) {
                        var checked = false;
                        var rowIds = $grid.jqGrid('getGridParam', 'selarrrow');
                        for (i in rowIds) {
                            if (rowIds[i] == rowid) {
                                checked = true;
                                break;
                            }
                        }
                        if (!checked) {
                            $grid.jqGrid("resetSelection");
                        }
                    }
                    
                    return ischeckbox;
                };
                
                options.oncyCheckboxChecked = false;
            } else {
                gridCreateOption.beforeSelectRow = function (rowid, e) {
                    var $grid = $(this);
                    
                    var checked = false;
                    var rowIds = $grid.jqGrid('getGridParam', 'selarrrow');
                    for (i in rowIds) {
                        if (rowIds[i] == rowid) {
                            checked = true;
                            break;
                        }
                    }
                    if (!checked) {
                        $grid.jqGrid("resetSelection");
                    }
                };
            }
        }
        
        if (options.oncyCheckboxChecked) {
            gridCreateOption.beforeSelectRow = function (rowid, e) {
                var $grid = $(this);
                var i = $.jgrid.getCellIndex($(e.target).closest('td')[0]);
                var cm = $grid.jqGrid('getGridParam', 'colModel');
                return (cm[i].name === 'cb');
            };
        }
        
        $("#"+targetId).jqGrid(gridCreateOption);
        $("#"+ targetId).setGridWidth($("#gbox_"+ targetId).parent().width() - 2, false);

        if(editing){
            $("#"+targetId).jqGrid('navGrid', pager, { search:false, del:true, add:true, edit:true, refresh:false }).navButtonAdd(pager,{
                   caption:"클릭하여 더보기", 
                   buttonicon:"", 
                   onClickButton: function(){ 
                       var viewType = $("#"+targetId).jqGrid("getGridParam", "height");
                        if(viewType == 'auto'){
                            $("#"+targetId).setGridHeight('350');
                        } else {
                            $("#"+targetId).setGridHeight('auto');
                        }
                        resizeTabWindow();
                   }, 
                   position:"last"
                });
        } else if(!navmore){
            $("#"+targetId).jqGrid('navGrid', pager, { search:false, del:false, add:false, edit:false, refresh:false }).navButtonAdd(pager,{
                   caption:"클릭하여 더보기", 
                   buttonicon:"", 
                   onClickButton: function(){ 
                       var viewType = $("#"+targetId).jqGrid("getGridParam", "height");
                        if(viewType == 'auto'){
                            $("#"+targetId).setGridHeight('350');
                        } else {
                            $("#"+targetId).setGridHeight('auto');
                        }
                        resizeTabWindow();
                   }, 
                   position:"last"
                });
        }

    }
    , reload : function(targetId, options){
        $("#"+targetId).jqGrid('setGridParam',{
            postData: options.searchParam
            , datatype : options.datatype || "json"
        })
        .trigger("reloadGrid",[{page:1}]);
    }
    , resize : function(){
        var grid = $('.ui-jqgrid-btable:visible');
        grid.each(function(index) {
            gridId = $(this).attr('id');
            $('#' + gridId).setGridWidth($('#gbox_' + gridId).parent().width() - 2, false);
        });
    }
    , jsonData : function(id) {
        var ids = $("#"+id).getDataIDs();

        var jsonArray = new Array();

        for (var i = 0; i < ids.length; i++) {
            jsonArray.push($("#"+id).getRowData(ids[i]));
        }

        return jsonArray;
    }
    , scrollTop : function(id) {
       $("#" + id).closest(".ui-jqgrid-bdiv").scrollTop(0);
    }
};

// Sub Grid 생성
var subGrid = {

    create : function (targetId, options ) {
        var sortName = options.colModels[0].name;

        if(options.paging == undefined){
            options.paging = true;
        }

        if(options.editing == undefined){
            options.editing = false;
        }

        var paging = false;
        var editing = false;
        var pager = "";
        var rowList;
        var rowNum;
        var footerrow = false;
        var userDataOnFooter = false;

        if(options.paging){
            paging = true;
        }

        if(options.editing){
            editing = true;
        }

        if(options.footerrow) {
            footerrow = true;
        }

        if(options.userDataOnFooter) {
            userDataOnFooter = true;
        }

        if(paging){
            pager = "#"+targetId+"Page";
            rowList = [50,100,200];
            rowNum = 50;
        }
        $("#"+targetId).jqGrid({
            url : options.url
            , postData : options.searchParam
            , datatype : options.datatype || "json"
            , mtype : "POST"
            , autowidth : false
            , width : '100%'
            , height : options.height || 200
            , colNames : options.colNames
            , colModel : options.colModels
            , cellEdit : options.cellEdit || false
            , cellsubmit : options.cellsubmit || 'clientArray'
            , cellurl : options.cellurl == undefined ? undefined : options.cellurl
            , rowNum : rowNum || -1
            , rowList : rowList
            , pager : pager
            , viewrecords : true
            , multiselect : options.multiselect || false
            , sortname : options.sortname || sortName
            , sortorder : options.sortorder || "desc"
            , afterEditCell : options.afterEditCell
            , afterSaveCell : options.afterSaveCell
            , beforeEditCell : options.beforeEditCell
            , beforeSaveCell : options.beforeSaveCell
            , jsonReader : {
                page: "page"
                , total: "total"
                , root: "data"
                , records: "records"
                , repeatitems: false,
            }
            , loadError : function(xhr, status, error){
                if(xhr.status == 450) {
                    location.replace("/login/noSessionView.do");
                } else {
                    if(xhr.status != 0) {
                        alert("오류가 발생되었습니다. 관리자에게 문의하십시요.["+xhr.status+"]["+error+"]");
                    }
                }
            }
            , onSelectRow : options.onSelectRow == undefined ? undefined : options.onSelectRow
            , onCellSelect : options.onCellSelect == undefined ? undefined : options.onCellSelect
            // Sub Grid 설정
            , subGrid: true // set the subGrid property to true to show expand buttons for each row
            , subGridRowExpanded: options.subGridRowExpanded // javascript function that will take care of showing the child grid
            , isHasSubGrid : options.isHasSubGrid
            /*
            , subGridOptions : {
                // configure the icons from theme rolloer
                plusicon: "ui-icon-triangle-1-e",
                minusicon: "ui-icon-triangle-1-s",
                openicon: "ui-icon-arrowreturn-1-e"
            }
            */
            , footerrow : footerrow
            , userDataOnFooter : userDataOnFooter
            , gridComplete : options.gridComplete == undefined ? undefined : options.gridComplete
            , caption : options.caption == undefined ? undefined : options.caption
            , ajaxGridOptions : options.ajaxGridOptions
        });

        $("#"+ targetId).setGridWidth($("#gbox_"+ targetId).parent().width() - 2, false);

        if(editing){
            $("#"+targetId).jqGrid('navGrid', pager, { search:false, del:true, add:true, edit:true, refresh:false });
        }

    }
    , reload : function(targetId, options){
        $("#"+targetId).jqGrid('setGridParam',{
            postData: options.searchParam
            , datatype : options.datatype || "json"
        }).trigger("reloadGrid",[{page:1}]);
    }
    , resize : function(){
        var grid = $('.ui-jqgrid-btable:visible');
        grid.each(function(index) {
            gridId = $(this).attr('id');
            $('#' + gridId).setGridWidth($('#gbox_' + gridId).parent().width() - 2, false);
        });
    }
};


var ajax = {
    call : function(options){

        var wait = true;

        if(options.wait != undefined && options.wait != null){
            wait = false;
        }

        if(wait){
            waiting.start();
        }

        jQuery.ajaxSettings.traditional = true;
        
        if (options.sendType && options.sendType == "json") {
            options.contentType = "application/json; charset=UTF-8";
            options.data = JSON.stringify(options.data);
        }

        options.contentType = options.contentType || "application/x-www-form-urlencoded;charset=UTF-8";
        options.type = options.type || "POST";
        options.dataType = options.dataType || "json";
        options.async = (options.async == null || options.async) ? true : false;
        var noAlert = (options.noAlert === undefined) ? false : options.noAlert
        
        $.ajax({
            url : options.url
            , type : options.type
            , dataType : options.dataType
            , contentType : options.contentType
            , cache : false
            , data : options.data
            , async: options.async
        })
        .done(function(data, textStatus, jqXHR){
            if(options.dataType == "text" || options.dataType == "html") {
                try {
                    var obj = eval("("+ data +")");

                    if(obj.exCode != null && obj.exCode != undefined && obj.exCode != ""){
                        alert(obj.exMsg);
//                        waiting.stop();
                    } else {
                        options.callBack(data);
                        common.all();
                    }
                } catch (e) {
                    options.callBack(data);
                    common.all();
                }
            } else {
                if(data.exCode != null && data.exCode != undefined && data.exCode != ""){
                    alert(data.exMsg);
//                    waiting.stop();
                } else {
                    options.callBack(data);
                    common.all();
                }
            }
        })
        .fail(function( xhr, status, error ){
            if(xhr.status == 450) {
                location.replace("/login/noSessionView.do");
            } else {
                if (! noAlert) { 
                    alert("오류가 발생되었습니다. 관리자에게 문의하십시요.["+xhr.status+"]["+error+"]");
                }
            }
        })
        .always(function(){
            if(wait){
                waiting.stop();
            }
        })
        .then(function(data, textStatus, jqXHR ) {

        });
    },
    load : function(targetId, url, params){
        waiting.start();

        if(params == undefined){
            params = {};
        }
        $("#"+targetId).load(url, params, function(response, status, xhr){
            waiting.stop();

            if(status == "error"){
                if(xhr.status == 450) {
                    location.replace("/login/noSessionView.do");
                } else {
                    alert("오류가 발생되었습니다. 관리자에게 문의하십시요.["+xhr.status+"]["+error+"]");
                }
            }

        });
    }
};

var validation = {
    timestamp : function(val){
        if(val != null && val != "" && val != undefined){
            return new Date(val).format("yyyy-MM-dd HH:mm:ss");
        }else{
            return "";
        }
    }
    ,birth : function(val){
        if(val != null && val != "" && val != undefined){
            return val.replace(/([0-9]{4})([0-9]{2})([0-9]{2})/,"$1년 $2월 $3일");
        }else{
            return "";
        }
    }
    ,tel: function(tel){
        if(tel != "" && tel != undefined && tel != null && tel != 'null'){
            return tel.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
        }else{
            return "";
        }
    }
    , fax: function(fax){
        if(fax != "" && fax != undefined && fax != null && fax != 'null'){
            return fax.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
        }else{
            return "";
        }
    }
    , mobile: function(no){
        if(no != "" && no != undefined && no != null && no != 'null'){
            return no.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
        }else{
            return "";
        }
    }
    , post: function(no) {
        if(no != "" && no != undefined && no != null && no != 'null'){
            return no.replace(/([0-9]{3})([0-9]{3})/,"$1-$2");
        }
    }
    , bizNo: function(no){
        if(no != "" && no != undefined && no != null && no != 'null'){
            return no.replace(/([0-9]{3})([0-9]{2})([0-9]{5})/,"$1-$2-$3");
        }
    }
    , cprNo: function(no){
        if(no != "" && no != undefined && no != null && no != 'null'){
            return no.replace(/([0-9]{6})([0-9]{7})/,"$1-$2");
        }
    }
    , isValidDate : function(str) {
        // Checks for the following valid date formats:
        // Also separates date into month, day, and year variables
        var datePat = /^(\d{2}|\d{4})(\/|-)(\d{1,2})\2(\d{1,2})$/;

        var matchArray = str.match(datePat); // is the format ok?
        if (matchArray == null) {
            return false;
        }
        year = matchArray[1];
        month = matchArray[3]; // parse date into variables
        day = matchArray[4];

        if (month < 1 || month > 12) { // check month range
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        if ((month==4 || month==6 || month==9 || month==11) && day==31) {
            return false
        }
        if (month == 2) { // check for february 29th
            var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));

            if (day > 29 || (day == 29 && !isleap)) {
                return false;
            }
        }
        return true; // date is valid
    }
    , isNull : function(str) {
        if(str != "" && str != undefined && str != null && str != 'null'){
            return false;
        } else {
            return true;
        }
    }
    , num : function(n){
          var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
          n += '';                          // 숫자를 문자열로 변환

          while (reg.test(n))
            n = n.replace(reg, '$1' + ',' + '$2');

          return n;
    }

    , isValidCodeName : function(str){
        // '/'는 47, ','는 44 {:123, }:125,(:40,):41, space:32
        for (i = 0; i < str.length; i++)
        {
            var ch = str.charCodeAt(i);
//            if((ch >= 0  && ch < 32) || ( ch == 47 )  ||   (ch >= 127 && ch <= 255)){
//                return true;
//            }
            if((ch >= 33 && ch <= 43) ||  (ch >= 91 && ch <= 96) || ( ch >= 123 && ch<= 126)){
                return false;
            }
        }

        return true;
    }
};

var validate = {
    set: function(formId) {
        $("#"+formId).validationEngine();
    },
    hide: function(formId) {
        $("#"+formId).validationEngine("hide");
    },
    check: function(formId) {
        return $("#"+formId).validationEngine("validate");
    }
};

var common = {
    datepicker : function(){
        $(".datepicker").datepicker({
              dateFormat : 'yy-mm-dd'
            , prevText : '이전달'
            , nextText : '다음달'
            , monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
            , monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
            , dayNamesMin : ['일','월','화','수','목','금','토']
            , showMonthAfterYear : true
            , inline : true
            , changeMonth : true
            , changeYear : true
            , showMonthAfterYear : true
            , showButtonPanel : true // 캘린더 하단에 버튼 패널을 표시한다.
            , currentText : '오늘 날짜' // 오늘 날짜로 이동하는 버튼 패널
            , closeText : '닫기'
        });

        $(".datepicker").mask("9999-99-99");

        $(".datepicker").blur(function(){
            if(!validation.isNull($(this).val()) && !validation.isValidDate($(this).val())){
                $(this).val("");
                $(this).focus();
                alert("올바르지 않은 날짜입니다.");
            }
        });

        $(".datepickerBtn").click(function(){
            $(this).prev().focus();
        });
    }
    , numeric : function(){
        $(".numeric").css("ime-mode", "disabled");//한글입력 X
        $(".numeric").mask("#0", {reverse: true, maxlength: false});
    }
    , decimal : function(){
        $(".decimal").css("ime-mode", "disabled");//한글입력 X
         $(".decimal").autoNumeric("init",{
            aSep: ','
            , aDec: '.'
            , vMax : '9999999999999.9'
            , vMin : '-9999999999999.9'
        });
    }
    , decimalExceptSep : function(){
        $(".decimal").css("ime-mode", "disabled");//한글입력 X
         $(".decimal").autoNumeric("init",{
            aSep: ''
            , aDec: '.'
            , vMax : '9999999999999.9'
            , vMin : '-9999999999999.9'
        });
    }
    , phoneNumber : function() {
        var phoneMask = function (val) {
            var mask = "000-000-000000";
            var value = val.replace(/\D/g, '');

            if(value.length > 2) {
                if(value.substring(0,2) == "02"){
                    mask = "00-000-00000"
                    if(value.length == 10) {
                        mask = "00-0000-0000"
                    }
                } else if(value.substring(0,2) == "01"){
                    if(value.length == 11) {
                        mask = "000-0000-0000"
                    }
                } else {
                    if(value.length == 11) {
                        mask = "000-0000-00000"
                    } else if(value.length == 12) {
                        mask = "0000-0000-0000"
                    }
                }
            }
            return mask;
        }
        var option = {
            onKeyPress: function(val, e, field, options) {
                field.mask(phoneMask.apply({}, arguments), options);
            }
            , onComplete: function(val, e, field, options) {
                var mask = "000-000-000000";
                var value = val.replace(/\D/g, '');
                if(value.length > 2) {
                    if(value.substring(0,2) == "02"){
                        mask = "00-000-00000"
                        if(value.length == 10) {
                            mask = "00-0000-0000"
                        }
                    } else if(value.substring(0,2) == "01"){
                        if(value.length == 11) {
                            mask = "000-0000-0000"
                        }
                    } else {
                        if(value.length == 11) {
                            mask = "000-0000-00000"
                        } else if(value.length == 12) {
                            mask = "0000-0000-0000"
                        }
                    }
                }
                field.mask(mask, options);
            }
        }

        $('.phoneNumber').mask(phoneMask, option);
    }
    , comma : function() {
        $('.comma').mask("#,##0", {reverse: true, maxlength: false});
    }
    , rate : function(){
        $(".rate").css("ime-mode", "disabled");//한글입력 X
         $(".rate").autoNumeric("init",{
            aSep: ','
            , aDec: '.'
            , vMax : '999.99'
            , vMin : '-999.99'
        });
    }
    , all : function() {
        common.datepicker();
        common.numeric();
        common.phoneNumber();
        common.comma();
        common.decimal();
        common.rate();
    }
}

var objClass = {
    add : function (obj, className ) {
        var hasClass = $(obj).hasClass(className);

        if(!hasClass ) {
            $(obj).addClass(className);
        }
    }
    , remove : function (obj, className ) {
        var hasClass = $(obj).hasClass(className);

        if(hasClass ) {
            $(obj).removeClass(className);
        }
    }
}

function popupClose() {
    self.close();
}


function resetForm(id) {
    $("#" + id).trigger("reset");
    $("#" + id).find("input[type=hidden]").val('');
}

function checkExcelDownload() {
    $.ajax({
        url : "/common/isExcelDownloading.do",
        type : "POST",
        dataType : "json",
        success : function(res) {
            if (res && res.success) {
                if (res.isExcelDownloading) {
                    setTimeout("checkExcelDownload();", 1000);
                } else {
                    setTimeout("waiting.stop();", 2000);
                }
            } else {
                waiting.stop();
            }
        },
        error : function() {
            waiting.stop();
        }
    });
}

function createFormSubmit(id, url, data) {
    if (url.toLowerCase().indexOf("exceldownload") > -1) {
        waiting.start();
        setTimeout("checkExcelDownload();", 1000);
    }

    $("#" + id + "Form").remove();
    var html = new Array();
    html.push("<form name=\""+id+"Form\" id=\""+id+"Form\" action=\""+url+"\" method=\"post\">");
    if(data != null) {
        if(data.constructor == Object){
            for(var name in data){
                html.push("<input type=\"hidden\" name=\""+name+"\" value=\"" + data[name] + "\">");
            }
        } else if(data.constructor == Array ){
            for(var i in data){
                for(var name in data[i]){
                    html.push("<input type=\"hidden\" name=\""+name+"\" value=\"" + data[i][name] + "\">");
                }
            }
        }
    }
    html.push("</form>");
    $("body").append(html.join(''));
    $("#" + id + "Form").submit();
}

function openTabSelfClose(id, url, data) {

    if(data != null) {
        if (url.indexOf("?") < 0) {
            url += "?";
        }
    
        var first = true;
        
        if(data.constructor == Object){
            for(var name in data){
                if (first) {
                    url += name + "=" + data[name];
                } else {
                    url += "&" + name + "=" + data[name];
                }
            }
        } else if(data.constructor == Array ){
            for(var i in data){
                for(var name in data[i]){
                    if (first) {
                        url += name + "=" + data[i][name];
                    } else {
                        url += "&" + name + "=" + data[i][name];
                    }
                }
            }
        }
    }
    
    var curUrl = window.location.href;
    curUrl = curUrl.replace(/http(s)?:\/\/[^\/]+/gi, "");
    
    var param = {
        closeUrl : curUrl,
        openUrl : url
    };
    window.top.parent.openByclose(param);
};

function openWindowSelfClose(id, url, data) {

    if(data != null) {
        if (url.indexOf("?") < 0) {
            url += "?";
        }
    
        var first = true;
        
        if(data.constructor == Object){
            for(var name in data){
                if (first) {
                    url += name + "=" + data[name];
                } else {
                    url += "&" + name + "=" + data[name];
                }
            }
        } else if(data.constructor == Array ){
            for(var i in data){
                for(var name in data[i]){
                    if (first) {
                        url += name + "=" + data[i][name];
                    } else {
                        url += "&" + name + "=" + data[i][name];
                    }
                }
            }
        }
    }
    
    var curUrl = window.location.href;
    curUrl = curUrl.replace(/http(s)?:\/\/[^\/]+/gi, "");
    
    var param = {
        closeUrl : curUrl,
        openUrl : url,
        winName : id
    };
    window.top.parent.openWindowByclose(param);
};

closeSelf = function() {
    var curUrl = window.location.href;
    curUrl = curUrl.replace(/http(s)?:\/\/[^\/]+/gi, "");
    
    var param = {
        closeUrl : curUrl,
        type : "url"
    };
    window.top.parent.closeTab(param);

};

function createTargetFormSubmit(result) {
    var url = result.url;    
    var parameter = "";
    
    if(result.data != null) {
        if(result.data.constructor == Object){
            for(var name in result.data){
                if (parameter != "") {
                    parameter += "&";
                }                
                parameter += name + "=" + result.data[name];
            }
        } else if(result.data.constructor == Array ){
            for(var i in result.data){
                for(var name in result.data[i]){
                    if (parameter != "") {
                        parameter += "&";
                    }                
                    parameter += name + "=" + result.data[i][name];
                }
            }
        }
    }
    
    if (parameter != "") {
        url += "?" + parameter;
    }
    
    openTab({
        url:url
    });
    
    /*
    $("#" + result.id + "Form").remove();
    var html = new Array();
    if(result.target != null && result.target != undefined) {
        html.push("<form name=\""+result.id+"Form\" id=\""+result.id+"Form\" action=\""+result.url+"\" target=\"" + result.target + "\" method=\"post\">");
    } else {
        html.push("<form name=\""+result.id+"Form\" id=\""+result.id+"Form\" action=\""+result.url+"\" method=\"post\">");
    }

    if(result.data != null) {
        if(result.data.constructor == Object){
            for(var name in result.data){
                html.push("<input type=\"hidden\" name=\""+name+"\" value=\"" + result.data[name] + "\">");
            }
        } else if(result.data.constructor == Array ){
            for(var i in result.data){
                for(var name in result.data[i]){
                    html.push("<input type=\"hidden\" name=\""+name+"\" value=\"" + result.data[i][name] + "\">");
                }
            }
        }
    }
    html.push("</form>");
    $("body").append(html.join(''));
    $("#" + result.id + "Form").submit();
    */
}

//공통팝업 호출 구분
function openWindowPop(config){
    var url = config.url ? config.url : '';
    var target = config.target ? config.target : 'popup';
    var left = (screen.width) ? (screen.width - config.width) / 2 : 0;
    var top = (screen.height) ? (screen.height - config.height) / 2 : 0;
    var settings = new Array();
    settings.push('height=' + config.height);    //세로
    settings.push('width=' + config.width);        //가로
    settings.push('top=' + top);                //y좌표
    settings.push('left=' + left);                //x좌표

    if(config.scrollbars){
        settings.push('scrollbars='+config.scrollbars);    //스크롤바
    }
    if(config.resizable){
        settings.push('resizable='+config.resizable);    //창크기 조절 가능여부
    }
    if(config.toolbar){
        settings.push('toolbar='+config.toolbar);        //뒤로, 앞으로, 검색, 즐겨찾기 등의 버튼이 나오는줄
    }
    if(config.location){
        settings.push('location='+config.location);        //주소창
    }
    if(config.status){
        settings.push('status='+config.status);            //창 상태 유무
    }
    if(config.menubar){
        settings.push('menubar='+config.menubar);        //파일,편집,보기,등의 버튼이 있는줄
    }
    if(config.fullscreen){
        settings.push('fullscreen='+config.fullscreen);        //전체화면 유무 지정
    }
    if(config.data) {
        window.open('', target, settings.join(','));
        $("#" + target + "Form").remove();
        var html = new Array();
        html.push("<form name=\""+target+"Form\" id=\""+target+"Form\" action=\""+url+"\" target=\""+target+"\" method=\"get\">");
        var data = config.data;
        if(data != null) {
            if(data.constructor == Object){
                for(var name in data){
                    html.push("<input type=\"hidden\" name=\""+name+"\" value=\"" + data[name] + "\">");
                }
            } else if(data.constructor == Array ){
                for(var i in data){
                    for(var name in data[i]){
                        html.push("<input type=\"hidden\" name=\""+name+"\" value=\"" + data[i][name] + "\">");
                    }
                }
            }
        }
        html.push("</form>");
        $("body").append(html.join(''));
        $("#" + target + "Form").submit();
    } else {
        window.open(url, target, settings.join(','));
    }
}

function goUrl(url) {
    //document.location.href = url;
    openTab({
        url:url
    });
}


$(document).ready(function(){
    common.all();
});

$(document).submit(function(e){
    if(validation.isNull(e.target.target) && (!validation.isNull(e.target.action) && e.target.action.indexOf("/common/fileDownloadResult.do") == -1 && e.target.action.indexOf("Download.do") == -1)) {
        waiting.start();
    }
});


$(document).on("blur", '.comma', function(e) {
    var value = parseInt(removeComma($(this).val()));

    if(value != null && value != undefined && !isNaN(value)) {
        $(this).val(addComma(value));
    } else {
        $(this).val("");
    }
});

$(document).on("blur", '.rate', function(e) {
    if ($(this).val() == "") {
        $(this).val("0.00");
    }
});

$(document).on("focus", "input[type=text]", function(e) {
    $(this).select();
});

$(document).on("keydown", "input", function(e) {
    if(!$(this).hasClass("ui-pg-input")) {
        if(e.keyCode==13) { return false; }
    }
});

$(window).resize(function() {
    grid.resize();
});

function addComma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

function removeComma(str) {
    str = String(str);
    return str.replace(/[^0-9\.]+/g, '');
}

function Round (n, pos) {
    var digits = Math.pow(10, pos);
    var sign = 1;
    if (n < 0) {
        sign = -1;
    } // 음수이면 양수처리후 반올림 한 후 다시 음수처리
    n = n * sign;
    var num = Math.round(n * digits) / digits;
    num = num * sign;
    return Number(num.toFixed(pos));
}
//지정자리 버림 (값, 자릿수)
function Floor (n, pos) {
    var digits = Math.pow(10, pos);
    var num = Math.floor(n * digits) / digits;
    return Number(num.toFixed(pos));
}
//지정자리 올림 (값, 자릿수)
function Ceiling (n, pos) {
    var digits = Math.pow(10, pos);
    var num = Math.ceil(n * digits) / digits;
    return Number(num.toFixed(pos));
}


/*
기간 조회
*/
function setSearchDate(term, startObj, endObj ) {
    var startDate;
    var dateNew = new Date();
    var endDate;
    if(term == "10") {
        startDate = shiftDate(getCurrentTime(), 0, 0, 0, "-");
    } else if(term == "20") {
        startDate = shiftDate(getCurrentTime(), 0, 0, -7, "-");
    } else if(term == "30") {
        startDate = shiftDate(getCurrentTime(), 0, 0, -15, "-");
    } else if(term == "40") {
        startDate = shiftDate(getCurrentTime(), 0, -1, 0, "-");
    } else if(term == "50") {
        startDate = shiftDate(getCurrentTime(), 0, -3, 0, "-");
    }

    if(term == "thisMonth") {
        var last_day = daysPerMonth(dateNew.getFullYear(), dateNew.getMonth()+1);
        endDate = shiftDate(getCurrentTime(), 0, 0, last_day-dateNew.getDate(), "-");
    } else {
        endDate = toFormatString(getCurrentTime(), "-");
    }

    if(term == "") {
        endDate == "";
    } else {
        endDate = toFormatString(getCurrentTime(), "-");
    }

    $("#" + startObj ).val(startDate);
    $("#" + endObj ).val(endDate);
}
/*
기간 조회
 */
function setSearchDate_new(term, startObj, endObj ) {
    
    var startDate;
    var dateNew = new Date();
    var endDate;

    startDate = shiftDate(getCurrentTime(), 0, 0, -term, "-");
    
    if(term == "thisMonth") {
        var last_day = daysPerMonth(dateNew.getFullYear(), dateNew.getMonth()+1);
        endDate = shiftDate(getCurrentTime(), 0, 0, last_day-dateNew.getDate(), "-");
    } else {
        endDate = toFormatString(getCurrentTime(), "-");
    }
     
    if(term == "") {
        endDate == "";
    } else {
        endDate = toFormatString(getCurrentTime(), "-");
    }
    
    $("#" + startObj ).val(startDate);
    $("#" + endObj ).val(endDate);
}
/*
기간 조회
 */
function setSearchDate_GoodsLayer(term, startObj, endObj ) {
    
    var startDate;
    var dateNew = new Date();
    var endDate;

    startDate = shiftDate(getCurrentTime(), 0, 0, -term, "-");
    
    if(term == "thisMonth") {
        var last_day = daysPerMonth(dateNew.getFullYear(), dateNew.getMonth()+1);
        endDate = shiftDate(getCurrentTime(), 0, 0, last_day-dateNew.getDate(), "-");
    } else {
        endDate = toFormatString(getCurrentTime(), "-");
    }
     
    if(term == "") {
        endDate == "";
    } else {
        endDate = toFormatString(getCurrentTime(), "-");
    }
    
    $("#goodsListForm #" + startObj ).val(startDate);
    $("#goodsListForm #" + endObj ).val(endDate);
}
/**
 * 주어진 Time 과 y년 m월 d일 차이나는 Time을 리턴

 * ex) var time = form.time.value; //'20000101'
 *     alert(shiftTime(time,0,0,-100));
 *     => 2000/01/01 00:00 으로부터 100일 전 Time
 */
function shiftDate (time, y, m, d, dele ) { //moveTime(time,y,m,d)
    var date = toDateObject(time);
    date.setFullYear(date.getFullYear() + y);    //y년을 더함
    date.setMonth(date.getMonth() + m);            //m월을 더함
    date.setDate(date.getDate() + d);            //d일을 더함
    return toDateString(date, dele);
}

/**
 * Time 스트링을 자바스크립트 Date 객체로 변환
 * parameter time: Time 형식의 String
 */
function toDateObject (time ) { //parseTime(time)
    var year  = time.substr(0,4);
    var month = time.substr(4,2) - 1; // 1월=0,12월=11
    var day   = time.substr(6,2);
    return new Date(year,month,day);
}

/**
 * 현재 시각을 Time 형식으로 리턴

 */
function getCurrentTime () {
    return toTimeString(new Date(), 'N' );
}

/**
 * 자바스크립트 Date 객체를 Time 스트링으로 변환
 * parameter date: JavaScript Date Object
 */
function toTimeString (date, secondYn ) { //formatTime(date)
    var year  = date.getFullYear();
    var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
    var day   = date.getDate();
    var hour  = date.getHours();
    var min   = date.getMinutes();
    var second = date.getSeconds();

    if (("" + month).length == 1) { month = "0" + month; }
    if (("" + day).length   == 1) { day   = "0" + day;   }
    if (("" + hour).length  == 1) { hour  = "0" + hour;  }
    if (("" + min).length   == 1) { min   = "0" + min;   }
    if (("" + second).length   == 1) { second   = "0" + second;   }

    if ( secondYn == 'Y' ) {
        return ("" + year + month + day + hour + min + second);
    } else {
        return ("" + year + month + day + hour + min);
    }
}

/*
윤달 포함 달별 일수 Return
*/
function daysPerMonth ()
{
    var DOMonth  = new Array("31","28","31","30","31","30","31","31","30","31","30","31");
    var IDOMonth = new Array("31","29","31","30","31","30","31","31","30","31","30","31");

    if(arguments[1] == 0) arguments[1] = 12;

    if ( (arguments[0]%4) == 0 ) {
        if ( (arguments[0]%100) == 0 && (arguments[0]%400) != 0 )
            return DOMonth[arguments[1]-1];
        return IDOMonth[arguments[1]-1];
    } else {
        return DOMonth[arguments[1]-1];
    }
}

/**
 * Time 스트링을 자바스크립트 Date 객체로 변환
 * parameter time: Time 형식의 String
 */
function toFormatString (time, dele ) { //parseTime(time)
    var year  = time.substr(0,4);
    var month = time.substr(4,2); // 1월=0,12월=11
    var day   = time.substr(6,2);

    return ("" + year + dele + month + dele + day)
}

/**
 * 자바스크립트 Date 객체를 Time 스트링으로 변환
 * parameter date: JavaScript Date Object
 */
function toDateString(date, dele) { //formatTime(date)
    var year  = date.getFullYear();
    var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
    var day   = date.getDate();

    if (("" + month).length == 1) { month = "0" + month; }
    if (("" + day).length   == 1) { day   = "0" + day;   }

    return ("" + year + dele + month + dele + day)
}

function getDateStr (objId ) {
    var date = ''
    if(typeof $("#"+objId+"Dt").val() != 'undefined') {
        date = $("#"+objId+"Dt").val();
    }

    if(typeof $("#"+objId+"Hr option:selected").val() != 'undefined') {
        date += " " + $("#"+objId+"Hr option:selected").val();
    }

    if(typeof $("#"+objId+"Mn option:selected").val() != 'undefined') {
        date += ":" + $("#"+objId+"Mn option:selected").val();
    }

    if(typeof $("#"+objId+"Sec").val() != 'undefined') {
        date += ":" + $("#"+objId+"Sec").val();
    }

    return date;
}

/**
 * 두 날짜 사이의 개월수(윤년 포함)
 * @param start(yyyymmdd)
 * @param end(yyyymmdd)
 * isLeapYear(year)
 * getDifDays(start, end)
 * @returns
 */
function getDiffMonths(start, end) {
    var startYear = start.substring(0, 4);
    var endYear = end.substring(0, 4);
    var startMonth = start.substring(4, 6) - 1;
    var endMonth = end.substring(4, 6) - 1;
    var startDay = start.substring(6, 8);
    var endDay = end.substring(6, 8);

    // 연도 차이가 나는 경우
    if (eval(startYear) > eval(endYear)) {
        // 종료일 월이 시작일 월보다 수치로 빠른 경우
        if (eval(startMonth) > eval(endMonth)) {
            var newEnd = startYear + "1231";
            var newStart = endYear + "0101";

            return (eval(getDiffMonths(start, newEnd)) + eval(getDiffMonths(newStart, end))).toFixed(2);
        // 종료일 월이 시작일 월보다 수치로 같거나 늦은 경우
        } else {
            var formMonth = eval(startMonth) + 1;
            if (eval(formMonth) < 10) formMonth = "0" + formMonth;

            var newStart = endYear + "" + formMonth + "" + startDay;
            var addMonths = (eval(endYear) - eval(startYear)) * 12;

            return (eval(addMonths) + eval(getDiffMonths(newStart, end))).toFixed(2);
        }
    } else {
    // 월별 일수차 (30일 기준 차이 일수)
        var difDaysOnMonth = new Array(1, -2, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1);
        var difDaysTotal = getDifDays(start, end);

        for (i = startMonth; i < endMonth; i++) {
            if (i == 1 && isLeapYear(startYear)) {
                difDaysTotal -= (difDaysOnMonth[i] + 1);
            } else {
                difDaysTotal -= difDaysOnMonth[i];
            }
        }

        return (difDaysTotal / 30).toFixed(2);
    }
}

function isLeapYear(year) {
    // parameter가 숫자가 아니면 false
    if (isNaN(year)) {
        return false;
    } else {
        var nYear = eval(year);
    }

    // 4로 나누어지고 100으로 나누어지지 않으며 400으로는 나눠지면 true(윤년)
    if (nYear % 4 == 0 && nYear % 100 != 0 || nYear % 400 == 0) {
        return true;
    } else {
        return false;
    }
}

function getDifDays(start, end) {
    var dateStart = new Date(start.substring(0, 4), start.substring(4, 6) - 1, start.substring(6, 8));
    var dateEnd = new Date(end.substring(0, 4), end.substring(4, 6) - 1, end.substring(6, 8));
    var difDays = (dateEnd.getTime() - dateStart.getTime()) / (24 * 60 * 60 * 1000);

    return Math.ceil(difDays);
}

function byteCheck(msgbox, smsBytes) {

    var conts = document.getElementById(msgbox);
    var bytes = document.getElementById(smsBytes);

    var i = 0;
    var cnt = 0;
    var exceed = 0;
    var ch = '';

    for (i=0; i<conts.value.length; i++) {
        ch = conts.value.charAt(i);
        if (escape(ch).length > 4) {
            cnt += 2;
        } else {
            cnt += 1;
        }
    }

    bytes.innerHTML = cnt;

    if (cnt > 80) {
        exceed = cnt - 80;
        alert('메시지 내용은 80바이트를 넘을수 없습니다.\n\n작성하신 메세지 내용은 '+ exceed +'byte가 초과되었습니다.\n\n초과된 부분은 자동으로 삭제됩니다.');
        var tcnt = 0;
        var xcnt = 0;
        var tmp = conts.value;
        for (i=0; i<tmp.length; i++) {
            ch = tmp.charAt(i);
            if (escape(ch).length > 4) {
                tcnt += 2;
            } else {
                tcnt += 1;
            }

            if (tcnt > 80) {
                tmp = tmp.substring(0,i);
                break;
            } else {
                xcnt = tcnt;
            }
        }
        conts.value = tmp;
        bytes.innerHTML = xcnt;
        return;
    }
}

//월의 마지막 날짜 가져오기
function getLastDay(year, month) {
    var date = new Date(year, month, 0);
    return date.getDate();
}

//지난달 날짜 가져오기("Y"면, 지난달의 1일, "" 이면 지난달의 마지막 날짜)
function f_today(flag){
    var date = new Date();
    var year  = date.getFullYear();
    var month = date.getMonth(); // 0부터 시작하므로 1더함 더함
    if (("" + month).length == 1) { month = "0" + month; }
    if(month ==0){
        month= "12";
        year = year-1;
    }
    if(flag =="Y"){
        return "" + year + "-" + month + "-" +"01";
    } else {
        return "" + year + month+ getLastDay(year,month);
    }
}

var tag = {
        /*
         * 상품 이미지
         */
        goodsImage : function(imgDomain, goodsId, imgPath , seq, gb, width, height, cls){
            if(gb == null || gb == undefined){
                gb = "";
            }

            if(cls == null || cls == undefined){
                cls = "";
            }

            if(imgPath == null || imgPath == undefined){
                imgPath = "";
            }

            var ext  = imgPath.substr(imgPath.lastIndexOf(".") , imgPath.length);

            //var    src = imgDomain + "/goods/" + goodsId + "/" + goodsId + "_" + seq + gb + "_" + width + "x" + height + ext;
            // 이미지 리사이징 파일을 s3에서 저장했었는데 cloudFront 방식으로 변경
            var src = imgDomain + "/goods/" + goodsId + "/" + goodsId + "_" + seq + gb + ext + "?w=" + width + "&h=" + height + "&f=webp&q=90";
            var imageStr = "";

            imageStr = "<img src=\""+src+"\" class=\""+cls+"\" onerror=\"this.src='/images/noimage.png'\"/>";

            return imageStr;
        },
        goodsImageMainBold : function(imgDomain, goodsId, imgPath , seq, gb, width, height, cls, mainYn){
            if(gb == null || gb == undefined){
                gb = "";
            }

            if(cls == null || cls == undefined){
                cls = "";
            }

            if(imgPath == null || imgPath == undefined){
                imgPath = "";
            }

            if(mainYn != null && mainYn =='Y'){
                mainYn = "style='border:1px solid #000'";
            }else{
                mainYn = "";
            }

            var ext  = imgPath.substr(imgPath.lastIndexOf(".") , imgPath.length);

            //var    src = imgDomain + "/goods/" + goodsId + "/" + goodsId + "_" + seq + gb + "_" + width + "x" + height + ext;
            // 이미지 리사이징 파일을 s3에서 저장했었는데 cloudFront 방식으로 변경
            //var src = imgDomain + "/goods/" + goodsId + "/" + goodsId + "_" + seq + gb + ext + "?w=" + width + "&h=" + height + "&f=webp&q=90";
            var src = imgDomain + "/goods/" + goodsId + "/" + goodsId + "_" + seq + gb + ext + "?w=" + width + "&f=webp&q=90";
            var imageStr = "";


            imageStr = "<img src=\""+src+"\" class=\""+cls+"\" " + mainYn + " onerror=\"this.src='/images/noimage.png'\"/>";

            return imageStr;
        }
    };

function checkSpecial(str) {
    const regExp = /[<>]/gi; 
    if(regExp.test(str)) { 
        return true; 
    }else{ 
        return false; 
    }
}