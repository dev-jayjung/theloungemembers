var fileUpload = {
    fileCallback : function(callback,objId) {
        fileUpload.callBack = callback;
        fileUpload.objId = objId;
        fileUpload.fileForm("file", '');
    }
    ,
    file : function(callback) {
        fileUpload.callBack = callback;
        fileUpload.fileForm("file", '');
    }
    ,
    movie : function(callback) {
        fileUpload.callBack = callback;
        fileUpload.fileForm("movie", 'mp4|mov');
    }
    , fileFilter : function(callback, filter) {
        fileUpload.callBack = callback;
        fileUpload.fileForm("file", filter);
    }
    , xls : function(callback) {
        fileUpload.callBack = callback;
        fileUpload.fileForm("xls", '');
    }
    , image : function(callback){
        fileUpload.callBack = callback;
        fileUpload.fileForm("image", 'jpeg|jpg|png|gif');
    }
    , editorImage : function(editorId, imgPath) {
        fileUpload.callBack = function(data) {
            data.imgPath = imgPath;
            var options = {
                url : "/common/editorImageFileResult.do"
                , data : data
                , callBack : function(result){
                    EditorCommon.pasteHTML(editorId, result.filePath);
                }
            };
            ajax.call(options);
        }
        fileUpload.fileForm("image", '');
    }
    , film : function(callback) {
        fileUpload.callBack = callback;
        fileUpload.fileForm("file", '', (500 * 1024 * 1024));
    }
    , goodsImage : function (callback, objId ) {
        fileUpload.callBack = callback;
        fileUpload.objId = objId;
        fileUpload.fileForm("image", "jpeg|jpg|png|gif");
    }
    , brandImage : function (callback, objId ) {
        fileUpload.callBack = callback;
        fileUpload.objId = objId;
        fileUpload.fileForm("image", 'jpeg|jpg|png|gif');
    }
    , fileForm : function(type, filter, size){
        $("#fileUploadForm").remove();
        var html = new Array();
        html.push("<form name=\"fileUploadForm\" id=\"fileUploadForm\" method=\"post\" enctype=\"multipart/form-data\" >");
        html.push("    <div style=\"display:none;\">");
        
        /**    image 의 경우 모든 이미지 등록 가능하도록 해달라고 요청이옴 **/
        if(type == "image"){
            html.push("        <input type=\"file\" name=\"uploadFile\" id=\"uploadFile\" accept = \".jpeg, .jpg, .png, .gif\" />");
        } else if( type == "movie") {
            html.push("        <input type=\"file\" name=\"uploadFile\" id=\"uploadFile\" accept = \".mp4, .mov\" />");
        } else if( type == "xls") {
            html.push("        <input type=\"file\" name=\"uploadFile\" id=\"uploadFile\" accept = \".xls, .xlsx\" />");
        } else {
            html.push("        <input type=\"file\" name=\"uploadFile\" id=\"uploadFile\" />");
        }
        
        if (size && size > 0) {
            html.push("      <input type=\"hidden\" name=\"uploadSize\" value=\"" + size + "\">");
        }
        html.push("        <input type=\"hidden\" name=\"uploadType\" value=\"" + type + "\">");
        html.push("        <input type=\"hidden\" name=\"filter\" value=\"" + filter + "\">");
        html.push("    </div>");
        html.push("</form>");
        $("body").append(html.join(''));
        $("#uploadFile").click();
    }
    , callBack : null
    , objId : null
}
$(document).on("blur", "#uploadFile", function(){
    alert($(this).val());
});
$(document).on("change", "#uploadFile", function(){
    waiting.start();
    $('#fileUploadForm').ajaxSubmit({
        url : '/common/fileUploadResult.do'
        , dataType : 'json'
        , success : function(result){
            $("#fileUploadForm").remove();
            waiting.stop();
            if(result.exCode != null && result.exCode != undefined && result.exCode != ""){
                alert(result.exMsg);
            } else {
                if(fileUpload.objId != null ) {
                    // 상품 이미지 등록 부분을 위한 처리..
                    fileUpload.callBack(result.file, fileUpload.objId );
                } else {
                    fileUpload.callBack(result.file);
                }
            }
        }
        , error : function(xhr, status, error) {
            waiting.stop();
            if(xhr.status == 1000) {
                location.replace("/login/noSessionView.do");
            } else {
                alert("오류가 발생되었습니다. 관리자에게 문의하십시요.["+xhr.status+"]["+error+"]");
            }
        }
    });
});