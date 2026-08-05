var layer = {
	create : function(config){
		var width = config.width ? config.width : 800
		var height = config.height ? config.height : 600
		var id = config.id ? config.id : 'layerPopup'
		var top = (screen.height) ? (screen.height - height) / 2 : 0;
		
		if(config.top) {
			top = config.top;
		}
		
		var scrollY = window.top.scrollY;
		top += scrollY;

		if($("#" + id).length == 0) {
			$("body").append("<div class=\"layer_overlay\"></div>");
			var html = new Array();
			html.push("<div class=\"modalLayerPop blankPop\">");
			html.push("	<div class=\"modal fade in\" tabindex=\"-1\"  style=\"display:block; padding-bottom: 200px;\">");
			html.push("		<div class=\"modal-dialog\" style=\"width:" + width + "px; padding-top: " + top + "px; \">");
			html.push("			<div class=\"modal-content\" id=\"" + id + "\" >");
			html.push("				<img src=\"/images/pop_blank_close.png\" style=\"cursor:pointer;position:absolute;right:0px;top:0px;z-index:1\" onclick=\"layer.close(\'" + id + "\');\" alt=\"닫기 버튼\">");
			html.push("				<div class=\"modal-header\">");
			html.push("					<h1 class=\"modal-title\">" + config.title + "</h1>");
			html.push("				</div>");
			html.push("				<div class=\"modal-body\">");
			html.push("					<div class=\"popContent\">");
			html.push(config.body)
			html.push("					</div>");
			html.push("					<div class=\"modal-footer\">");
			if(config.button) {
				html.push(config.button);
				html.push("					<button type=\"button\" onclick=\"layer.close(\'" + id + "\');\" class=\"btn_type2 mgl7\">닫기</button>");
			} else {
				html.push("					<button type=\"button\" onclick=\"layer.close(\'" + id + "\');\" class=\"btn_type2\">닫기</button>");
			}
			html.push("					</div>");
			html.push("				</div>");
			html.push("			</div");
			html.push("		</div>");
			html.push("	</div>");
			html.push("</div>");

			$("body").append(html.join(""));

			common.all();
			
			$("#" + id).closest('body').css("overflow-y","hidden");/*layer에 의한 이중스크롤*/
		}
	}
	, post : function(callBack) {
		if($("#postLayer").length == 0) {
            var scrollY = window.top.scrollY;
            
			$("body").append("<div class=\"layer_overlay\"></div>");
			var html = new Array();
			html.push("<div class=\"modalLayerPop blankPop\">");
			html.push("	<div class=\"modal fade in\" tabindex=\"-1\"  style=\"display:block; padding-bottom: 200px;\">");
			html.push("		<div class=\"modal-dialog\" style=\"width:700px; padding-top: " + (100 + scrollY) + "px; \">");
			html.push("			<div class=\"modal-content\">");
			html.push("				<img src=\"/images/pop_blank_close.png\" style=\"cursor:pointer;position:absolute;right:0px;top:0px;z-index:1\" onclick=\"layer.close(\'postLayer\');\" alt=\"닫기 버튼\">");
			html.push("				<div class=\"modal-body\">");
			html.push("					<div class=\"popContent\" id=\"postLayer\" style=\"width:660px; height:450px;\">");
			html.push("					</div>");
			html.push("					<div class=\"modal-footer\">");
			html.push("						<button type=\"button\" onclick=\"layer.close(\'postLayer\');\" class=\"btn_type2\">닫기</button>");
			html.push("					</div>");
			html.push("				</div>");
			html.push("			</div");
			html.push("		</div>");
			html.push("	</div>");
			html.push("</div>");
			$("body").append(html.join(""));
			// ====================================================================================
			// http://postcode.map.daum.net/guide 참조
			// zonecode : 우편번호
			// address : 기본주소
			// addressEnglish : 기본 영문 주소
			// roadAddress : 도로명 주소
			// roadAddress : 영문 도로명 주소
			// jibunAddress : 지번 주소
			// jibunAddressEnglish : 영문 지번 주소
			// postcode : 구 우편번호
			// ====================================================================================
			new daum.Postcode({
				oncomplete: function(data) {
					callBack(data);
					layer.close('postLayer');
				}
				, width : '100%'
				, height : '100%'
			}).embed(document.getElementById('postLayer'));
		}
	}
	, close : function(id) {
		var $obj = $("#" + id);
		$("#" + id).closest('body').css("overflow-y","auto");/*layer에 의한 이중스크롤*/
		$obj.parents(".modalLayerPop").prev().remove();
		$obj.parents(".modalLayerPop").remove();
	}
}