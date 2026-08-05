//--------------------------------------------------------------------------------//
var companyLayerOptions = {
	callBack : undefined
	, multiselect : false
};
var layerCompanyList = {
	create : function (option) {
		$.extend(companyLayerOptions, option);
		var options = {
			url : _COMPANY_SEARCH_LAYER_URL
			, dataType : "html"
			, data : {
				showLowerCompany : option.showLowerCompany == undefined ? undefined : option.showLowerCompany
				, compDispTpCd :  option.compDispTpCd == undefined ? undefined : option.compDispTpCd
				, compStatCd :  option.compStatCd == undefined ? undefined : option.compStatCd
				, readOnlyCompStatCd : option.readOnlyCompStatCd == undefined ? undefined : option.readOnlyCompStatCd
			}
			, callBack : function(result) {
				var config = {
					id : "layerCompanyView"
					, top : 100
					, width : 1200
					, height : 600
					, title : "업체 조회"
					, body : result
					, button : "<button type=\"button\" onclick=\"layerCompanyList.confirm();\" class=\"btn_type1\">확인</button>"
				}
				layer.create(config);
				layerCompanyList.grid();
			}
		}
		ajax.call(options );
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerCompanyList" );
		var rowids = null;
		if(companyLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		companyLayerOptions.callBack (jsonArray );
		layer.close("layerCompanyView");
	}
	, grid : function() {
		var baseColModels = {
			compBaseCols_1 : [
				{name:"compNo", label:_COMPANY_SEARCH_GRID_LABEL.compNo, width:"80", align:"center", classes:'pointer fontbold'}
				, {name:"compNm", label:_COMPANY_SEARCH_GRID_LABEL.compNm, width:"130", align:"center"}
				, {name:"stIds", label:_COMPANY_SEARCH_GRID_LABEL.stIds, width:"100", align:"center", sortable:false, hidden:true } /* 사이트 아이디 */
				, {name:"stNms", label:_COMPANY_SEARCH_GRID_LABEL.stNms, width:"200", align:"center", sortable:false, hidden:true } /* 사이트 명 */
			],

			compBaseCols_2 : [
				{name:"compDispTpCd", label:_COMPANY_SEARCH_GRID_LABEL.compDispTpCd, width:"80", align:"center", formatter:"select", editoptions:{value:_COMP_DISP_TP_CD}}
				, {name:"compStatCd", label:_COMPANY_SEARCH_GRID_LABEL.compStatCd, width:"80", align:"center", formatter:"select", editoptions:{value:_COMP_STAT_CD}}
				, {name:"compGbCd", label:_COMPANY_SEARCH_GRID_LABEL.compGbCd, width:"80", align:"center", formatter:"select", editoptions:{value:_COMP_GB_CD}}
				, {name:"compTpCd", label:_COMPANY_SEARCH_GRID_LABEL.compTpCd, width:"80", align:"center", formatter:"select", editoptions:{value:_COMP_TP_CD}}
			],

			compBaseCols_3 : [
				{name:"bizNo", label:_COMPANY_SEARCH_GRID_LABEL.bizNo, width:"120", align:"center"}
				, {name:"ceoNm", label:_COMPANY_SEARCH_GRID_LABEL.ceoNm, width:"100", align:"center"}
				, {name:"fax", label:_COMPANY_SEARCH_GRID_LABEL.fax, width:"100", align:"center"}
				, {name:"tel", label:_COMPANY_SEARCH_GRID_LABEL.tel, width:"100", align:"center"}
				, {name:"csChrgNm", label:_COMPANY_SEARCH_GRID_LABEL.csChrgNm, width:"100", align:"center"}
				, {name:"csChrgTel", label:_COMPANY_SEARCH_GRID_LABEL.csChrgTel, width:"100", align:"center"}
			],

			compBrandCols : [
				{name:"bndNmKo", label:_GOODS_SEARCH_GRID_LABEL.bndNmKo, width:"150", align:"center"}
				, {name:"bndNmEn", label:_GOODS_SEARCH_GRID_LABEL.bndNmEn, width:"150", align:"center"}
			],

			commonCols : [
				{name:"sysRegrNm", label:_COMPANY_SEARCH_GRID_LABEL.sysRegrNm, width:"100", align:"center"}
				, {name:"sysRegDtm", label:_COMPANY_SEARCH_GRID_LABEL.sysRegDtm, width:"130", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
				, {name:"sysUpdrNm", label:_COMPANY_SEARCH_GRID_LABEL.sysUpdrNm, width:"100", align:"center"}
				, {name:"sysUpdDtm", label:_COMPANY_SEARCH_GRID_LABEL.sysUpdDtm, width:"130", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
			]
		};

		var gridColModels = baseColModels.compBaseCols_1;
		gridColModels = gridColModels.concat(baseColModels.compBrandCols);
		gridColModels = gridColModels.concat(baseColModels.compBaseCols_2);
		gridColModels = gridColModels.concat(baseColModels.compBaseCols_3);
		gridColModels = gridColModels.concat(baseColModels.commonCols);

		var options = {
			url : _POPUP_COMPANY_GRID_URL
			, height : 200
			, searchParam : $("#layerCompanySearchForm").serializeJson()
			, colModels :gridColModels
			, multiselect : companyLayerOptions.multiselect
			, ondblClickRow : function(){
				layerCompanyList.confirm();
			}
		};
		grid.create("layerCompanyList", options);
	}
	, reload : function () {
		var options = {
			searchParam : $("#layerCompanySearchForm").serializeJson()
		};
		grid.reload("layerCompanyList", options);
	}
	, searchReset : function (defaultVal) {
		resetForm("layerCompanySearchForm");
		$("#layerCompanySearchForm #showLowerCompany").val(defaultVal);
		$("#layerCompanySearchForm #showOnlyMainCompany").val('Y');
	}
}

//----------------------------------------------------------------------------------------------------------
// 업체관리쪽에 업체검색 에서 쓰는 업체검색팝업
//----------------------------------------------------------------------------------------------------------
var companyMgtCompanyLayerOptions = {
		callBack : undefined
		, multiselect : false
	};
var companyMgtLayerCompanyList = {

	create : function (option) {
		$.extend(companyMgtCompanyLayerOptions, option);
		var options = {
			url : _COMPANY_SEARCH_LAYER_URL
			, dataType : "html"
			, data : {
				showLowerCompany : option.showLowerCompany == undefined ? undefined : option.showLowerCompany
			}
			, callBack : function(result) {
				var config = {
					id : "layerCompanyView"
					, top : 100
					, width : 1200
					, height : 800
					, title : "업체 조회"
					, body : result
					, button : "<button type=\"button\" onclick=\"companyMgtLayerCompanyList.confirm();\" class=\"btn_type1\">확인</button>"
				}
				layer.create(config);
				companyMgtLayerCompanyList.grid();
			}
		}
		ajax.call(options);
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerCompanyList" );
		var rowids = null;
		if(companyMgtCompanyLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		companyMgtCompanyLayerOptions.callBack (jsonArray );
		layer.close("layerCompanyView");
	}
	, grid : function() {
		var $form = $('#layerCompanySearchForm');
		var options = {
			url : _POPUP_COMPANY_GRID_URL
			, height : 300/*이중스크롤 방지에 따른 확인 버튼 안보임 해결 */
			, searchParam : $("#layerCompanySearchForm").serializeJson()
			, colModels : [
				{name:"compNo", label:_COMPANY_SEARCH_GRID_LABEL.compNo, width:"100", align:"center", classes:'pointer fontbold'}
				, {name:"compNm", label:_COMPANY_SEARCH_GRID_LABEL.compNm, width:"150", align:"center"}
				, {name:"compDispTpCd", label:_COMPANY_SEARCH_GRID_LABEL.compDispTpCd, width:"100", align:"center", formatter:"select", editoptions:{value:_COMP_DISP_TP_CD}}
				, {name:"bizNo", label:_COMPANY_SEARCH_GRID_LABEL.bizNo, width:"150", align:"center"}
				, {name:"compStatCd", label:_COMPANY_SEARCH_GRID_LABEL.compStatCd, width:"100", align:"center", formatter:"select", editoptions:{value:_COMP_STAT_CD}}
				, {name:"ceoNm", label:_COMPANY_SEARCH_GRID_LABEL.ceoNm, width:"100", align:"center"}
				, {name:"compGbCd", label:_COMPANY_SEARCH_GRID_LABEL.compGbCd, width:"100", align:"center", formatter:"select", editoptions:{value:_COMP_GB_CD}}
				, {name:"compTpCd", label:_COMPANY_SEARCH_GRID_LABEL.compTpCd, width:"100", align:"center", formatter:"select", editoptions:{value:_COMP_TP_CD}}
				, {name:"fax", label:_COMPANY_SEARCH_GRID_LABEL.fax, width:"150", align:"center"}
				// , {name:"tel", label:_COMPANY_SEARCH_GRID_LABEL.tel, width:"150", align:"center"}
				, {name:"bndNmKo", label:_GOODS_SEARCH_GRID_LABEL.bndNmKo, width:"120", align:"center"}
				, {name:"bndNmEn", label:_GOODS_SEARCH_GRID_LABEL.bndNmEn, width:"120", align:"center"}
				, {name:"sysRegrNm", label:_COMPANY_SEARCH_GRID_LABEL.sysRegrNm, width:"150", align:"center"}
				, {name:"sysRegDtm", label:_COMPANY_SEARCH_GRID_LABEL.sysRegDtm, width:"150", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
				, {name:"sysUpdrNm", label:_COMPANY_SEARCH_GRID_LABEL.sysUpdrNm, width:"150", align:"center"}
				, {name:"sysUpdDtm", label:_COMPANY_SEARCH_GRID_LABEL.sysUpdDtm, width:"150", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
			]
			, multiselect : companyMgtCompanyLayerOptions.multiselect
		};
		grid.create("layerCompanyList", options);
	}
	, reload : function () {
		var $form = $('#layerCompanySearchForm');
		$("<input></input>").attr({type:"hidden", id:"adminYn", name:"adminYn", value:"Y"}).appendTo($form);
		$("<input></input>").attr({type:"hidden", id:"searchCompanyGb",name:"searchCompanyGb", value:"UP"}).appendTo($form);

		var options = {
			searchParam : $("#layerCompanySearchForm").serializeJson()
		};
		grid.reload("layerCompanyList", options);
	}
	, searchReset : function () {
		resetForm("layerCompanySearchForm");
	}
}

//---------------------------------------------------------------------------------------------------------
var stLayerOptions = {
	callBack : undefined
	, multiselect : false
};
var layerStList = {
	create : function (data) {
		$.extend(stLayerOptions, data);
		var options = {
			url : _ST_SEARCH_LAYER_URL
			, dataType : "html"
			, callBack : function(result) {
				var config = {
					id : "layerStView"
					, top : 100
					, width : 1200
					, height : 800
					, title : "사이트 조회"
					, body : result
					, button : "<button type=\"button\" onclick=\"layerStList.confirm();\" class=\"btn_type1\">확인</button>"
				}
				layer.create(config);
				layerStList.grid();
			}
		}
		ajax.call(options );
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerStList" );
		var rowids = null;
		if(stLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		stLayerOptions.callBack (jsonArray );
		layer.close("layerStView");
	}
	, grid : function() {
		var options = {
			url : _ST_GRID_URL
			, height : 400
			, searchParam : $("#layerStSearchForm").serializeJson()
			, colModels : [
				{name:"stId", label:_ST_SEARCH_GRID_LABEL.stId, width:"100", align:"center", formatter:'integer', classes:'pointer fontbold'}
				, {name:"stNm", label:_ST_SEARCH_GRID_LABEL.stNm, width:"150", align:"center"}
				, {name:"stUrl", label:_ST_SEARCH_GRID_LABEL.stUrl, width:"250", align:"center"}
				, {name:"stSht", label:_ST_SEARCH_GRID_LABEL.stSht, width:"150", align:"center"}
				, {name:"useYn", label:_ST_SEARCH_GRID_LABEL.useYn, width:"100", align:"center", formatter:"select", editoptions:{value:_SHOW_YN } }
				, {name:"compNm", label:_ST_SEARCH_GRID_LABEL.compNm, width:"100", align:"center"}
				, {name:"compStatCd", label:_ST_SEARCH_GRID_LABEL.compStatCd, width:"100", align:"center", formatter:"select", editoptions:{value:_COMP_STAT_CD } }
				, {name:"sysRegrNm", label:_ST_SEARCH_GRID_LABEL.sysRegrNm, width:"150", align:"center"}
				, {name:"sysRegDtm", label:_ST_SEARCH_GRID_LABEL.sysRegDtm, width:"150", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
				, {name:"sysUpdrNm", label:_ST_SEARCH_GRID_LABEL.sysUpdrNm, width:"150", align:"center"}
				, {name:"sysUpdDtm", label:_ST_SEARCH_GRID_LABEL.sysUpdDtm, width:"150", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
			]
			, multiselect : stLayerOptions.multiselect
		};
		grid.create("layerStList", options);
	}
	, reload : function () {
		var options = {
			searchParam : $("#layerStSearchForm").serializeJson()
		};
		grid.reload("layerStList", options);
	}
	, searchReset : function () {
		resetForm("layerStSearchForm");
	}
}

var memberLayerOptions = {
	callBack : undefined
	, multiselect : false
	, param : {}
};
var layerMemberList = {
	create : function (data) {
		$.extend(memberLayerOptions, data);
		var options = {
			url : _MEMBER_SEARCH_LAYER_URL
			, dataType : "html"
			, data : memberLayerOptions.param
			, callBack : function(result) {
				var config = {
					id : "layerMemberView"
					, top : 100
					, width : 1200
					, height : 800
					, title : "회원 목록 조회"
					, body : result
					, button : "<button type=\"button\" onclick=\"layerMemberList.confirm();\" class=\"btn_type1\">확인</button>"
				}
				layer.create(config);
				layerMemberList.grid();
			}
		}
		ajax.call(options );
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerMemberList" );
		var rowids = null;
		if(memberLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		memberLayerOptions.callBack (jsonArray );
		layer.close("layerMemberView");
	}
	, grid : function() {
		var options = {
			url : _MEMBER_GRID_URL
			, height : 400
			, searchParam : $("#layerMemberSearchForm").serializeJson()
			, colModels : [
				{name:"mbrNo", label:_MEMBER_SEARCH_GRID_LABEL.mbrNo, width:"100", align:"center", classes:'pointer fontbold'}
				, {name:"mbrNm", label:_MEMBER_SEARCH_GRID_LABEL.mbrNm, width:"100", align:"center"}
				, {name:"mbrStatCd", label:_MEMBER_SEARCH_GRID_LABEL.mbrStatCd, width:"100", align:"center", formatter:"select", editoptions:{value:_MBR_STAT_CD}}
				, {name:"mbrGrdCd", label:_MEMBER_SEARCH_GRID_LABEL.mbrGrdCd, width:"100", align:"center", formatter:"select", editoptions:{value:_MBR_GRD_CD}}
				, {name:"loginId", label:_MEMBER_SEARCH_GRID_LABEL.loginId, width:"100", align:"center"}
				// , {name:"tel", label:_MEMBER_SEARCH_GRID_LABEL.tel, width:"100", align:"center"}
				, {name:"mobile", label:_MEMBER_SEARCH_GRID_LABEL.mobile, width:"100", align:"center"}
				, {name:"email", label:_MEMBER_SEARCH_GRID_LABEL.email, width:"150", align:"center"}
				, {name:"birth", label:_MEMBER_SEARCH_GRID_LABEL.birth, width:"100", align:"center"}
				, {name:"emailRcvYn", label:_MEMBER_SEARCH_GRID_LABEL.emailRcvYn, width:"100", align:"center", formatter:"select", editoptions:{value:_RCV_YN}}
				, {name:"smsRcvYn", label:_MEMBER_SEARCH_GRID_LABEL.smsRcvYn, width:"100", align:"center", formatter:"select", editoptions:{value:_RCV_YN}}
				// , {name:"svmnRmnAmt", label:_MEMBER_SEARCH_GRID_LABEL.svmnRmnAmt, width:"100", align:"center", formatter:'integer'}
				// , {name:"blcRmnAmt", label:_MEMBER_SEARCH_GRID_LABEL.blcRmnAmt, width:"100", align:"center", formatter:'integer'}
				, {name:"gdGbCd", label:_MEMBER_SEARCH_GRID_LABEL.gdGbCd, width:"100", align:"center", formatter:"select", editoptions:{value:_GD_GB_CD}}
				, {name:"ntnGbCd", label:_MEMBER_SEARCH_GRID_LABEL.ntnGbCd, width:"100", align:"center", formatter:"select", editoptions:{value:_NTN_GB_CD}}
				// , {name:"joinPathCd", label:_MEMBER_SEARCH_GRID_LABEL.joinPathCd, width:"150", align:"center", formatter:"select", editoptions:{value:_JOIN_PATH_CD}}
				, {name:"joinDtm", label:_MEMBER_SEARCH_GRID_LABEL.joinDtm, width:"130", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
				// , {name:"updrIp", label:_MEMBER_SEARCH_GRID_LABEL.updrIp, width:"120", align:"center"}
				// , {name:"sysRegrNm", label:_MEMBER_SEARCH_GRID_LABEL.sysRegrNm, width:"100", align:"center"}
				// , {name:"sysRegDtm", label:_MEMBER_SEARCH_GRID_LABEL.sysRegDtm, width:"130", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
				, {name:"sysUpdrNm", label:_MEMBER_SEARCH_GRID_LABEL.sysUpdrNm, width:"100", align:"center"}
				, {name:"sysUpdDtm", label:_MEMBER_SEARCH_GRID_LABEL.sysUpdDtm, width:"130", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
			]
			, multiselect : memberLayerOptions.multiselect
			, ondblClickRow : function(){
				if(memberLayerOptions.multiselect ) {
					layerMemberList.confirm();
				}else{
					layerMemberList.confirm();
				}
			}
		};
		grid.create("layerMemberList", options);
	}
	, searchMemberList : function () {
		var options = {
			searchParam : $("#layerMemberSearchForm").serializeJson()
		};
		grid.reload("layerMemberList", options);
	}
	, searchReset : function () {
		resetForm("layerMemberSearchForm");
	}
}


var userLayerOptions = {
		  callBack : undefined
		, multiselect : false
		, param : {}
	};
var layerUserList = {
	create : function (data) {
		$.extend(userLayerOptions, data);
		var options = {
			url : _USER_SEARCH_LAYER_URL
			, dataType : "html"
			, data : userLayerOptions.param
			, callBack : function(result) {
				var config = {
					id : "layerUserView"
					, top : 100
					, width : 800
					, height : 600
					, title : "사용자 목록 조회"
					, body : result
					, button : "<button type=\"button\" onclick=\"layerUserList.confirm();\" class=\"btn_type1\">확인</button>"
				}
				layer.create(config);
				layerUserList.grid();
			}
		}
		ajax.call(options );
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerUserList" );
		var rowids = null;
		if(userLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		userLayerOptions.callBack (jsonArray );
		layer.close("layerUserView");
	}
	, grid : function() {
		var options = {
			url : _USER_GRID_URL
			, height : 300
			, searchParam : $("#layerUserSearchForm").serializeJson()
			, colModels : [
				  {name:"usrNo", label:_USER_SEARCH_GRID_LABEL.usrNo, width:"90", align:"center", classes:'pointer fontbold'}
				, {name:"loginId", label:_USER_SEARCH_GRID_LABEL.loginId, width:"100", align:"center"}
				, {name:"usrNm", label:_USER_SEARCH_GRID_LABEL.usrNm, width:"80", align:"center"}
				, {name:"usrStatCd", label:_USER_SEARCH_GRID_LABEL.usrStatCd, width:"80", align:"center", formatter:"select", editoptions:{value:_USR_STAT}}
				, {name:"usrGbCd", label:_USER_SEARCH_GRID_LABEL.usrGbCd, width:"80", align:"center", formatter:"select", editoptions:{value:_USR_GB}}
				, {name:"compNm", label:_USER_SEARCH_GRID_LABEL.compNm, width:"100", align:"center"}
				, {name:"arrBndNm", label:_USER_SEARCH_GRID_LABEL.arrBndNm, width:"300", align:"center"}
				, {name:"mobile", label:_USER_SEARCH_GRID_LABEL.mobile, width:"100", align:"center"}
				, {name:"email", label:_USER_SEARCH_GRID_LABEL.email, width:"150", align:"center"}
//				, {name:"sysRegrNm", label:_USER_SEARCH_GRID_LABEL.sysRegrNm, width:"100", align:"center"}
//				, {name:"sysRegDtm", label:_USER_SEARCH_GRID_LABEL.sysRegDtm, width:"150", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
//				, {name:"sysUpdrNm", label:_USER_SEARCH_GRID_LABEL.sysUpdrNm, width:"100", align:"center"}
//				, {name:"sysUpdDtm", label:_USER_SEARCH_GRID_LABEL.sysUpdDtm, width:"150", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
			]
			, multiselect : userLayerOptions.multiselect
			, ondblClickRow : function(){
				layerUserList.confirm();
			}
		};
		grid.create("layerUserList", options);
	}
	, searchUserList : function () {
		var options = {
			searchParam : $("#layerUserSearchForm").serializeJson()
		};
		grid.reload("layerUserList", options);
	}
	, searchReset : function () {
		resetForm("layerUserSearchForm");
	}
};


var layerUserInfo = {
	create : function() {
		var options = {
			url : _USER_INFO_LAYER_URL
			, dataType : "html"
			, callBack : function(result) {
				var config = {
					id : "layerUserInfoView"
					, top : 100
					, width : 1000
					, height : 600
					, title : "사용자 정보 수정"
					, body : result
					, button : "<button type=\"button\" onclick=\"layerUserInfo.update();\" class=\"btn_type1\">수정</button>"
				}
				layer.create(config);
			}
		}
		ajax.call(options );
	}
	, update : function () {
		if(validate.check("userInfoLayerForm")) {
			
			if ($("#pswd").val() != '' && $("#ordPswd").val() == '') {
				alert('비밀번호 수정시, 현재 비밀번호를 입력해주세요.');
				return false;
			}
			
			if ($("#pswd").val() != '') {
				if(!valid.password.test($("#pswd").val())){
					alert("공백없이 8~20자의 영문/숫자/특수문자를 조합하여 주세요.");
					$("#join_pswd").focus();
					return false;
				}
			}
			
			if(confirm(_CONFIRM_UPDATE)) {					
				var options = {
					url : _USER_INFO_UPDATE_URL
					, data : $("#userInfoLayerForm").serializeJson()
					, callBack : function(result){
						layer.close("layerUserInfoView");
					}
				};
				ajax.call(options);
			}
		}

	}
}

//--------------------------------------------------------------------------------//
//goods 검색 Layer
var goodsLayerOptions = {
	compNo : null
	, callBack : undefined
	, multiselect : false
	, compStatCd : undefined
	, compDispTpCd : undefined
	, readOnlyCompStatCd : undefined
};
var layerGoodsList = {
	create : function (option ) {
		goodsLayerOptions = $.extend( {}, goodsLayerOptions, option );
		var options = {
			url : _GOODS_SEARCH_LAYER_URL
			, data : {
				goodsTpCd : option.goodsTpCd,
				disableAttrGoodsTpCd : option.disableAttrGoodsTpCd,
				bndNo : option.bndNo == undefined ? undefined : option.bndNo,
				bndNmKo : option.bndNmKo == undefined ? undefined : option.bndNmKo,
				compNo : option.compNo == undefined ? undefined : option.compNo,
				compNm : option.compNm == undefined ? undefined : option.compNm,
				stId : option.stId == undefined ? undefined : option.stId,
				stNm : option.stNm == undefined ? undefined : option.stNm,
				compDispTpCd : option.compDispTpCd == undefined ? undefined : option.compDispTpCd,
				compStatCd :  option.compStatCd == undefined ? undefined : option.compStatCd,

				readOnlyCompDispTpCd : option.readOnlyCompDispTpCd == undefined ? undefined : option.readOnlyCompDispTpCd,
				readOnlyCompStatCd : option.readOnlyCompStatCd == undefined ? undefined : option.readOnlyCompStatCd,
				forceStSearchReadOnly : option.forceStSearchReadOnly == undefined ? undefined : option.forceStSearchReadOnly
			}
			, dataType : "html"
			, callBack : layerGoodsList.callBackCreate
		}
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "goodsSearch"
			, width : 1400
			, height : 900
			, title : "상품 조회"
			, body : data
			, button : "<button type=\"button\" onclick=\"layerGoodsList.confirm();\" class=\"btn_type1\">확인</button>"
		}
		layer.create(config);
		layerGoodsList.initGoodsGrid(data);
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerGoodsList" );
		var rowids = null;
		if(goodsLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}

		goodsLayerOptions.callBack (jsonArray );
		layer.close("goodsSearch");
	}
	, initGoodsGrid : function (data) {
		var gridOptions = {
			url : _GOODS_GRID_URL
			, height : 200
			, searchParam : $("#goodsListForm").serializeJson()
			, colModels : [
				 {name:"goodsId", label:_GOODS_SEARCH_GRID_LABEL.goodsId, width:"100", key: true, align:"center", classes:'pointer fontbold'} /* 상품 번호 */
			   , {name:"compGoodsId", label:_GOODS_SEARCH_GRID_LABEL.compGoodsId, width:"100", align:"center"} /* 업체 상품 번호 */
			   , {name:"imgPath", label:_GOODS_SEARCH_GRID_LABEL.imgPaths, width:"70", align:"center", formatter: function(cellvalue, options, rowObject) {
						if(rowObject.imgPath != "" &&   rowObject.imgPath != undefined ) {
							return tag.goodsImage(_IMG_URL, rowObject.goodsId, rowObject.imgPath , rowObject.imgSeq, "", _IMAGE_GOODS_SIZE_70_0, _IMAGE_GOODS_SIZE_70_1, "hgt40 wth40");
						} else {
							return '<img src="/images/noimage.png" style="width:40px; height:40px;" alt="" />';
						}
					}
				 }
				, {name:"imgSeq", label:_GOODS_SEARCH_GRID_LABEL.imgSeq, width:"70", align:"center", sortable:false, hidden:true } /* 이미지 순번 */
				, {name:"bndNmKo", label:_GOODS_SEARCH_GRID_LABEL.bndNmKo, width:"120", align:"center", sortable:false } /* 브랜드명 */
				, {name:"goodsNm", label:_GOODS_SEARCH_GRID_LABEL.goodsNm, width:"300", align:"center", sortable:false } /* 상품명 */
				, {name:"goodsTpCd", label:_GOODS_SEARCH_GRID_LABEL.goodsTpCd, width:"80", align:"center", sortable:false, formatter:"select", editoptions:{value:_GOODS_TP_CD } } /* 상품 유형 */
				, {name:"goodsStatCd", label:_GOODS_SEARCH_GRID_LABEL.goodsStatCd, width:"80", align:"center", sortable:false, formatter:"select", editoptions:{value:_GOODS_STAT_CD } } /* 상품 상태 */
				//, {name:"mdlNm", label:_GOODS_SEARCH_GRID_LABEL.mdlNm, width:"200", align:"center", sortable:false } /* 모델명 */
				, {name:"saleAmt", label:_GOODS_SEARCH_GRID_LABEL.saleAmt, width:"90", align:"center", sortable:false, formatter: 'currency', formatoptions:{decimalSeparator:'.', decimalPlaces:0, suffix: ' 원', thousandsSeparator:','} } /* 판매가 */
				, {name:"compNm", label:_GOODS_SEARCH_GRID_LABEL.compNm, width:"100", align:"center", sortable:false } /* 업체명 */
				, {name:"mmft", label:_GOODS_SEARCH_GRID_LABEL.mmft, width:"100", align:"center", sortable:false } /* 제조사 */
				, {name:"saleStrtDtm", label:_GOODS_SEARCH_GRID_LABEL.saleStrtDtm, width:"120", align:"center", sortable:false, formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
				, {name:"saleEndDtm", label:_GOODS_SEARCH_GRID_LABEL.saleEndDtm, width:"120", align:"center", sortable:false, formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
				, {name:"showYn", label:_GOODS_SEARCH_GRID_LABEL.showYn, width:"80", align:"center", sortable:false, formatter:"select", editoptions:{value:_SHOW_YN } } /* 노출여부 */
				, {name:"bigo", hidden:true , label:_GOODS_SEARCH_GRID_LABEL.bigo, width:"200", align:"center", sortable:false } /* 비고 */
				, {name:"stIds", label:_GOODS_SEARCH_GRID_LABEL.stIds, width:"100", align:"center", sortable:false, hidden:true } /* 사이트 아이디 */
				, {name:"stNms", label:_GOODS_SEARCH_GRID_LABEL.stNms, width:"120", align:"center", sortable:false, hidden:false } /* 사이트 명 */
				, {name:"sysRegrNm", label:_GOODS_SEARCH_GRID_LABEL.sysRegrNm, width:"100", align:"center"}
				, {name:"sysRegDtm", label:_GOODS_SEARCH_GRID_LABEL.sysRegDtm, width:"150", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
				, {name:"mdlNo" , hidden:false,	 label:'모델번호', width:"130", align:"center"}
				, {name:"sysUpdDtm" , hidden:true,	 label:'최종변경일시', width:"130", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
				, {name:"sysUpdrNm" , hidden:true,	 label:'최종변경자', width:"100", align:"center"}				]
			, multiselect : goodsLayerOptions.multiselect
			, goodsTpCd : data.goodsTpCd
		}
		grid.create("layerGoodsList", gridOptions);
	}
	, searchGoodsList : function () {
		var options = {
			searchParam : $("#goodsListForm").serializeJson()
		};
		grid.reload("layerGoodsList", options);
	}
	, searchReset : function () {
		resetForm ("goodsListForm" );
	}
	, searchCompany : function () {
		var options = {
			multiselect : false
			, callBack : this.searchCompanyCallback
			, compStatCd : goodsLayerOptions.compStatCd == undefined ? undefined : goodsLayerOptions.compStatCd
			, compDispTpCd : goodsLayerOptions.compDispTpCd == undefined ? undefined : goodsLayerOptions.compDispTpCd
			, readOnlyCompStatCd : goodsLayerOptions.readOnlyCompStatCd == undefined ? undefined : goodsLayerOptions.readOnlyCompStatCd
		}
		layerCompanyList.create (options );
	}
	, searchCompanyCallback : function (compList ) {
		if(compList.length > 0 ) {
			$("#goodsListForm #compNo").val (compList[0].compNo );
			$("#goodsListForm #compNm").val (compList[0].compNm );
		}
	}
	, selectBrandSeries : function (gubun ) {
		var options = null;
		if(gubun == "brand") {
			options = {
				multiselect : false
				, bndGbCd : '20'
				, callBack : this.searchBrandCallback
			}
		} else {
			options = {
				multiselect : false
				, bndGbCd : '10'
				, callBack : this.searchSeriesCallback
			}
		}
		layerBrandList.create (options );
	}
	, searchBrandCallback : function (brandList ) {
		if(brandList != null && brandList.length > 0 ) {
			$("#bndNo").val (brandList[0].bndNo );
			$("#bndNm").val (brandList[0].bndNmKo );
		}
	}
	, searchSeriesCallback : function (brandList ) {
		if(brandList != null && brandList.length > 0 ) {
			$("#seriesNo").val (brandList[0].bndNo );
			$("#seriesNm").val (brandList[0].bndNmKo );
		}
	}
	, searchDateChange : function () {
		var term = $('#goodsListForm .btn_type2').attr('value');
		if(term == "") {
			$("#goodsListForm #sysRegDtmStart").val("");
			$("#goodsListForm #sysRegDtmEnd").val("");
		} else {
			setSearchDate_GoodsLayer(term, "sysRegDtmStart", "sysRegDtmEnd");
		}
	}
}

//--------------------------------------------------------------------------------//
//item 검색 Layer
var itemLayerOptions = {
	compNo : null
	, callBack : undefined
	, multiselect : false
};
var layerItemList = {
	create : function (option ) {
		itemLayerOptions = $.extend( {}, itemLayerOptions, option );
		var options = {
			url : _ITEM_SEARCH_LAYER_URL
			, data : itemLayerOptions
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "itemSearch"
			, width : 1000
			, height : 900
			, title : "단품 조회"
			, body : data
			, button : "<button type=\"button\" onclick=\"layerItemList.confirm();\" class=\"btn_type1\">확인</button>"
		};
		layer.create(config);
		layerItemList.initItemGrid();
	}
	, initItemGrid : function () {
		var gridOptions = {
			url : _ITEM_GRID_URL
			, datatype : 'local'
			, height : 200
			, searchParam : $("#itemListForm").serializeJson()
			, colModels : [
				{name:"goodsId", label:_ITEM_SEARCH_GRID_LABEL.goodsId, width:"100", align:"center"} /* 상품 번호 */
				, {name:"goodsNm", label:_ITEM_SEARCH_GRID_LABEL.goodsNm, width:"300", align:"center", sortable:false } /* 상품명 */
				, {name:"itemNo", label:_ITEM_SEARCH_GRID_LABEL.itemNo, width:"100", key: true, align:"center"} /* 단품 번호 */
				, {name:"itemNm", label:_ITEM_SEARCH_GRID_LABEL.itemNm, width:"300", align:"center", sortable:false } /* 단품명 */
				, {name:"itemStatCd", label:_ITEM_SEARCH_GRID_LABEL.itemStatCd, width:"150", align:"center", sortable:false, formatter:"select", editoptions:{value:_ITEM_STAT_CD } } /* 단품 상태 */
				, {name:"saleAmt", label:_ITEM_SEARCH_GRID_LABEL.saleAmt, width:"100", align:"center", sortable:false, formatter: 'currency', formatoptions:{decimalSeparator:'.', decimalPlaces:0, suffix: ' 원', thousandsSeparator:','} } /* 판매가 */
				, {name:"addSaleAmt", label:_ITEM_SEARCH_GRID_LABEL.addSaleAmt, width:"100", align:"center", sortable:false, formatter: 'currency', formatoptions:{decimalSeparator:'.', decimalPlaces:0, suffix: ' 원', thousandsSeparator:','} } /* 추가 금액 */
				, {name:"webStkQty", label:_ITEM_SEARCH_GRID_LABEL.webStkQty, width:"100", align:"center"} /* 재고수량 */
				, {name:"bomCd", label:_ITEM_SEARCH_GRID_LABEL.bomCd, width:"100", align:"center", sortable:false } /* BOM */
				, {name:"cstrtGoodsId", label:_ITEM_SEARCH_GRID_LABEL.cstrtGoodsId, width:"100", align:"center", hidden:true } /* 구성 상품 번호 */
				, {name:"goodsStatCd", label:_ITEM_SEARCH_GRID_LABEL.goodsStatCd, width:"150", align:"center", sortable:false, formatter:"select", editoptions:{value:_GOODS_STAT_CD } } /* 상품 상태 */
				, {name:"goodsTpCd", label:_ITEM_SEARCH_GRID_LABEL.goodsTpCd, width:"150", align:"center", sortable:false, formatter:"select", editoptions:{value:_GOODS_TP_CD } } /* 상품 유형 */
				, {name:"mdlNm", label:_ITEM_SEARCH_GRID_LABEL.mdlNm, width:"200", align:"center", sortable:false } /* 모델명 */
				, {name:"compNm", label:_ITEM_SEARCH_GRID_LABEL.compNm, width:"200", align:"center", sortable:false } /* 업체명 */
				, {name:"bndNmKo", label:_ITEM_SEARCH_GRID_LABEL.bndNmKo, width:"200", align:"center", sortable:false } /* 브랜드명 */
				, {name:"saleStrtDtm", label:_ITEM_SEARCH_GRID_LABEL.saleStrtDtm, width:"200", align:"center", sortable:false, formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
				, {name:"saleEndDtm", label:_ITEM_SEARCH_GRID_LABEL.saleEndDtm, width:"200", align:"center", sortable:false, formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
				]
			, multiselect : itemLayerOptions.multiselect
		};
		grid.create("layerItemList", gridOptions);
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerItemList" );
		var rowids = null;
		if(itemLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		itemLayerOptions.callBack (jsonArray );
		layer.close("itemSearch");
	}
	, searchItemList : function () {
		var options = {
			searchParam : $("#itemListForm").serializeJson()
		};
		grid.reload("layerItemList", options);
	}
	, searchReset : function () {
		resetForm ("itemListForm" );
	}
	, searchCompany : function () {
		var options = {
			multiselect : false
			, callBack : this.searchCompanyCallback
		}
		layerCompanyList.create (options );
	}
	, searchCompanyCallback : function (compList ) {
		if(compList.length > 0 ) {
			$("#itemListForm #compNo").val (compList[0].compNo );
			$("#itemListForm #compNm").val (compList[0].compNm );
		}
	}
	, selectBrandSeries : function (gubun ) {
		var options = null;
		if(gubun == "brand") {
			options = {
				multiselect : false
				, callBack : this.searchBrandCallback
			}
		}
		layerBrandList.create (options );
	}
	, searchBrandCallback : function (brandList ) {
		if(brandList != null && brandList.length > 0 ) {
			$("#bndNo").val (brandList[0].bndNo );
			$("#bndNm").val (brandList[0].bndNmKo );
		}
	}
}

//--------------------------------------------------------------------------------//
//coupon 검색 Layer
var couponLayerOptions = {
	compNo : null
	, callBack : undefined
	, multiselect : false
};
var layerCouponList = {
	create : function (option ) {
		couponLayerOptions = $.extend( {}, couponLayerOptions, option );
		var options = {
			url : _COUPON_SEARCH_LAYER_URL
			, data : {
				compNo : couponLayerOptions.compNo,
				couponTpCd : option.couponTpCd,
				disableAttrCouponTpCd : option.disableAttrCouponTpCd
			}
			, dataType : "html"
			, callBack : layerCouponList.callBackCreate
		}
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "couponSearch"
			, width : 1400
			, height : 900
			, title : "쿠폰 조회"
			, body : data
			, button : "<button type=\"button\" onclick=\"layerCouponList.confirm();\" class=\"btn_type1\">확인</button>"
		}
		layer.create(config);
		layerCouponList.initCouponGrid(data);
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerCouponList" );
		var rowids = null;
		if(couponLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		couponLayerOptions.callBack (jsonArray );
		layer.close("couponSearch");
	}
	, initCouponGrid : function (data) {
		var gridOptions = {
			url : _COUPON_GRID_URL
			, height : 200
			, searchParam : $("#couponListForm").serializeJson()
			, colModels : [{name:"cpNo", label:_COUPON_SEARCH_GRID_LABEL.cpNo, width:"100", key: true, align:"center"} /* 쿠폰 번호 */
				, {name:"cpNm", label:_COUPON_SEARCH_GRID_LABEL.cpNm, width:"300", align:"center", sortable:false } /* 쿠폰 명 */
				, {name:"stNms", label:_COUPON_SEARCH_GRID_LABEL.stNms, width:"200", align:"center", sortable:false } /* 사이트 명 */
				, {name:"cpTgCd", label:_COUPON_SEARCH_GRID_LABEL.cpTgCd, width:"200", align:"center", sortable:false } /* 사이트 명 */
				, {name:"cpKindCd", label:_COUPON_SEARCH_GRID_LABEL.cpKindCd, width:"80", align:"center", sortable:false, formatter:"select", editoptions:{value:_CP_KIND_CD } }
				, {name:"cpStatCd", label:_COUPON_SEARCH_GRID_LABEL.cpStatCd, width:"80", align:"center", sortable:false, formatter:"select", editoptions:{value:_CP_STAT_CD } }
				, {name:"cpAplCd", label:_COUPON_SEARCH_GRID_LABEL.cpAplCd, width:"80", align:"center", sortable:false, formatter:"select", editoptions:{value:_CP_APL_CD } }
				, {name:"aplVal", label:_COUPON_SEARCH_GRID_LABEL.aplVal, width:"300", align:"center", sortable:false }
				, {name:"minBuyAmt", label:_COUPON_SEARCH_GRID_LABEL.minBuyAmt, width:"300", align:"center", sortable:false }
				, {name:"maxDcAmt", label:_COUPON_SEARCH_GRID_LABEL.maxDcAmt, width:"300", align:"center", sortable:false }
				, {name:"aplStrtDtm", label:_COUPON_SEARCH_GRID_LABEL.aplStrtDtm, width:"200", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
				, {name:"aplEndDtm", label:_COUPON_SEARCH_GRID_LABEL.aplEndDtm, width:"200", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
				, {name:"dupleUseYn", label:_COUPON_SEARCH_GRID_LABEL.dupleUseYn, width:"80", align:"center", sortable:false, formatter:"select", editoptions:{value:_DUPLE_USE_YN } }
				, {name:"sysRegrNm", label:_COUPON_SEARCH_GRID_LABEL.sysRegrNm, width:"150", align:"center"}
				, {name:"sysRegDtm", label:_COUPON_SEARCH_GRID_LABEL.sysRegDtm, width:"200", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
			]
			, multiselect : couponLayerOptions.multiselect
			, couponTpCd : data.couponTpCd
		}
		grid.create("layerCouponList", gridOptions);
	}
	, searchCouponList : function () {
		var options = {
			searchParam : $("#couponListForm").serializeJson()
		};
		grid.reload("layerCouponList", options);
	}
	, searchReset : function () {
		resetForm ("couponListForm" );
	}
	, searchSt : function () {
		var options = {
			multiselect : false
			, callBack : searchStCallback
		}
		layerStList.create (options );
	}
	, searchStCallback : function (stList ) {
		if(stList.length > 0 ) {
			$("#stId").val (stList[0].stId );
			$("#stNm").val (stList[0].stNm );
		}
	}
}

//--------------------------------------------------------------------------------//
//Brand 검색 Layer
var brandLayerOptions = {
	compNo : null
	, bndGbCd : null
	, callBack : undefined
	, multiselect : false
};
var layerBrandList = {
	create : function (option ) {
		brandLayerOptions = $.extend( {}, brandLayerOptions, option );
		var options = {
			url : _BRAND_SEARCH_LAYER_URL
			, data : {
				compNo : brandLayerOptions.compNo
				, compNm : brandLayerOptions.compNm
			}
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "brandSearch"
			, width : 1200
			, height : 900
			, title : "브랜드 조회"
			, body : data
			, button : "<button type=\"button\" onclick=\"layerBrandList.confirm();\" class=\"btn_type1\">확인</button>"
		};
		layer.create(config);
		layerBrandList.initBrandGrid();
	}
	, initBrandGrid : function () {
		var gridOptions = {
			url : _BRAND_GRID_URL
			, height : 200
			, searchParam : $("#brandListForm").serializeJson()
			, colModels : [
				{name:"bndNo", label:_BRAND_SEARCH_GRID_LABEL.bndNo , width:"80", key: true, align:"center", sortable:false, classes:'pointer fontbold'} /* 브랜드 번호 */
				, {name:"bndNmKo", label:_BRAND_SEARCH_GRID_LABEL.bndNmKo , width:"200", align:"center", sortable:false } /* 브랜드 국문 */
				, {name:"bndNmEn", label:_BRAND_SEARCH_GRID_LABEL.bndNmEn , width:"200", align:"center", sortable:false } /* 브랜드 영문 */
				, {name:"useYn", label:_BRAND_SEARCH_GRID_LABEL.useYn , width:"60", align:"center", sortable:false, formatter:"select", editoptions:{value:_USE_YN } } /* 사용여부 */
				//, {name:"sortSeq", label:_BRAND_SEARCH_GRID_LABEL.sortSeq , width:"100", align:"center", sortable:false } /* 정렬순서 */
				//, {name:"compNm", label:_BRAND_SEARCH_GRID_LABEL.compNm , width:"200", align:"center", sortable:false } /* 업체명 */
				, {name:"stIds", label:_GOODS_SEARCH_GRID_LABEL.stIds, width:"100", align:"center", sortable:false, hidden:true } /* 사이트 아이디 */
				, {name:"stNms", label:_GOODS_SEARCH_GRID_LABEL.stNms, width:"120", align:"center", sortable:false, hidden:false } /* 사이트 명 */
				, {name:"sysRegrNm", label:_BRAND_SEARCH_GRID_LABEL.sysRegrNm , width:"100", align:"center"}
				, {name:"sysRegDtm", label:_BRAND_SEARCH_GRID_LABEL.sysRegDtm , width:"130", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
			]
			, multiselect : brandLayerOptions.multiselect
		}
		grid.create("layerBrandList", gridOptions);
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerBrandList" );
		var rowids = null;
		if(brandLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		brandLayerOptions.callBack (jsonArray );
		layer.close("brandSearch");
	}
	, searchBrandList : function () {
		var options = {
			searchParam : $("#brandListForm").serializeJson()
		};
		grid.reload("layerBrandList", options);
	}
	, searchReset : function () {
		resetForm ("brandListForm" );
	}
	, searchCompany : function () {
		var options = {
			multiselect : false
			, callBack : this.searchCompanyCallback
		}
		layerCompanyList.create (options );
	}
	, searchCompanyCallback : function (compList ) {
		if(compList.length > 0 ) {
			$("#brandListForm #compNo").val (compList[0].compNo );
			$("#brandListForm #compNm").val (compList[0].compNm );
		}
	}
}
//--------------------------------------------------------------------------------//
//company카테고리 검색 Layer
var companyCategoryLayerOptions = {
	compNo : null

};
var layerCompanyCategoryList = {
	create : function (option ) {
		companyCategoryLayerOptions = $.extend( {}, companyLayerOptions, option );
		var options = {
			url : _COMPANY_CATEGORY_LAYER_URL
			, data : {
				compNo : companyCategoryLayerOptions.compNo
			}
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "companyCategory"
			, width : 1000
			, height : 900
			, title : "업체 카테고리 조회"
			, body : data
			, button : "<button type=\"button\" onclick=\"layerCompanyCategoryList.confirm();\" class=\"btn_type1\">확인</button>"
		};
		layer.create(config);
		layerCompanyCategoryList.initCompanyCategoryGrid();
	}
	, initCompanyCategoryGrid : function () {

		var gridOptions = {
			url : _COMPANY_CATEGORY_GRID_URL
			, height : 200
			, searchParam : $("#companyCategoryListForm").serializeJson()
			, colModels : [
				    {name:"stNm", label:_COMPANY_CATEGORY_GRID_LABEL.stNm, width:"200", align:"center", sortable:false } /* 사이트 명 */
				  , {name:"dispClsfNo", label:_COMPANY_CATEGORY_GRID_LABEL.dispClsfNo, width:"100", align:"center", key: true, sortable:false } /* 전시분류 번호 */
				  , {name:"dispClsfNm", label:_COMPANY_CATEGORY_GRID_LABEL.dispClsfNm, width:"150", align:"center", sortable:false } /* 전시분류 명 */
				  , {name:"ctgPath", label:_COMPANY_CATEGORY_GRID_LABEL.ctgPath, width:"300", align:"center", sortable:false } /* 대분류 */
				  , {name:"goodsId", label:_COMPANY_CATEGORY_GRID_LABEL.goodsId, width:"100", align:"center", hidden:true, sortable:false } /* 전시분류 번호 */
				  , {name:"stId", label:_COMPANY_CATEGORY_GRID_LABEL.stId, width:"100", align:"center", hidden:true} /* 사이트 ID */
				]

			, multiselect : true
		}
		grid.create("layerCompanyCategoryList", gridOptions);
	}
	, confirm : function () {

		var jsonArray = new Array();
		var grid = $("#layerCompanyCategoryList" );
		var rowids = null;
		//if(companyLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		/*} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}*/
		companyCategoryLayerOptions.callBack (jsonArray );
		layer.close("companyCategory");
	}

}

//--------------------------------------------------------------------------------//
// 전시목록 검색 Layer
var layerCategoryList = {
	option : {
		callBack : undefined
		, multiselect : false
		, plugins : [ "themes" ]
		, arrDispClsfCd : undefined
		, stId : undefined
		, compNo : undefined
		, dispClsfCd : undefined
		, filterGb : undefined
		, upDispYn : undefined
	}
	, create : function (option ) {
		var stIdVal= option.stId;
		var dispClsfCdVal = option.dispclsfCd;
		var compNoVal = option.compNo;
		var filterGbVal = option.filterGb;
		var upDispYn = option.upDispYn;
		this.option = $.extend( {}, this.option, option );
		var options = {
			url : _CATEGORY_SEARCH_LAYER_URL
			, data : { "stId" : stIdVal , "dispClsfCd" : dispClsfCdVal, "compNo" : compNoVal, "filterGb": filterGbVal, "upDispYn": upDispYn}
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "categorySearch"
			, width : 500
			, height : 900
			, title : "전시목록 조회"
			, body : data
			, button : "<button type=\"button\" onclick=\"layerCategoryList.confirm();\" class=\"btn_type1\">확인</button>"
		};
		layer.create(config);
		layerCategoryList.initDisplayTree();
	}
	, initDisplayTree : function () {
		var arrDispClsfCd = this.option.arrDispClsfCd;
		var stId = this.option.stId; // 사이트아이디
		var dispClsfCd = this.option.dispClsfCd; // 전시카테고리분류
		var compNo = this.option.compNo; // 업체번호
		var filterGb = this.option.filterGb; // 필터링 조건. 상품상세의 경우 G
		var upDispYn = this.option.upDispYn; // 상위 전시여부
		$("#layerCategoryList").jstree({
			core : {
				multiple : this.option.multiselect
				, data : {
					type : "POST"
					, url : function(node) {
	//					return "/display/displayListTree.do";
						return "/display/displayListTreeFilter.do";
					}
					, data : function (node) {
						var data = null;
						if(arrDispClsfCd != undefined && arrDispClsfCd != null && arrDispClsfCd.length > 0) {
							data = {
								arrDispClsfCd : arrDispClsfCd,
								stId : stId,
								dispClsfCd :dispClsfCd,
								compNo : compNo,
								filterGb : filterGb,
								upDispYn : upDispYn
							};
						} else {
							data = {
								stId : stId,
								dispClsfCd :dispClsfCd,
								compNo : compNo,
								filterGb : filterGb,
								upDispYn : upDispYn
							};
						}
						return data;
					}
				}
			}
			, plugins : this.option.plugins
		})
		.bind("ready.jstree", function (event, data) {
				$("#layerCategoryList").jstree("open_node", $("#layerCategoryList > ul > li"));
         });
	}
	, confirm : function () {
		var arrId = $("#layerCategoryList").jstree().get_selected();
		var result = new Array();
		for(var i in arrId) {
			var data = $("#layerCategoryList").jstree().get_node(arrId[i]);

			if(this.option.plugins.indexOf("checkbox") > -1) {
				if(data.children == null || data.children.length == 0){
					result.push({
						  dispNo : data.id
						, dispNm : data.text
						, dispClsfCd : data.original.dispClsfCd
						, dispPath : data.original.dispPath
						, dispLvl : data.original.dispLvl
						, upDispNo : data.original.parent
						, stId : data.original.stId
						, stNm : data.original.stNm
						, bbsId : data.original.bbsId
						, bbsGbLev : data.original.bbsGbLev
					});
				}
			} else {
				result.push({
					  dispNo : data.id
					, dispNm : data.text
					, dispClsfCd : data.original.dispClsfCd
					, dispPath : data.original.dispPath
					, dispLvl : data.original.dispLvl
					, upDispNo : data.original.parent
					, stId : data.original.stId
					, stNm : data.original.stNm
					, bbsId : data.original.bbsId
					, bbsGbLev : data.original.bbsGbLev
				});
			}
		}

		this.option.callBack(result);

		layer.close("categorySearch");
	}
}

//상품평 검색 Layer
var goodsCommentLayerOptions = {
	compNo : null
	, callBack : undefined
	, multiselect : false
};
var layerGoodsCommentList = {
	create : function (option ) {
		goodsCommentLayerOptions = $.extend( {}, goodsCommentLayerOptions, option );
		var options = {
			url : _GOODS_COMMENT_SEARCH_LAYER_URL
			, data : {
				compNo : goodsCommentLayerOptions.compNo
			}
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "goodsCommentSearch"
			, width : 1000
			, height : 900
			, title : "상품평 조회"
			, body : data
			, button : "<button type=\"button\" onclick=\"layerGoodsCommentList.confirm();\" class=\"btn_type1\">확인</button>"
		};
		layer.create(config);
		layerGoodsCommentList.initGoodsCommentGrid();
	}
	, initGoodsCommentGrid : function () {
		var gridOptions = {
			url : _GOODS_COMMENT_GRID_URL
			, height : 200
			, searchParam : $("#displayGoodsCommentListForm").serializeJson()
			, colModels : [
				{name:"goodsEstmNo", label:_GOODS_COMMENT_GRID_LABEL.goodsEstmNo, width:"100", key: true, align:"center"} /* 상품 평가 번호 */
				, {name:"estmId", label:_GOODS_COMMENT_GRID_LABEL.estmId, width:"100", align:"center", sortable:false } /* 로그인 ID */
				, {name:"mbrNm", label:_GOODS_COMMENT_GRID_LABEL.mbrNm, width:"100", align:"center", sortable:false } /* 회원명 */
				, {name:"ttl", label:_GOODS_COMMENT_GRID_LABEL.ttl, width:"300", align:"center", sortable:false } /* 제목 */
				, {name:"imgRegYn", label:_GOODS_COMMENT_GRID_LABEL.imgRegYn, width:"150", align:"center", sortable:false, formatter:"select", editoptions:{value:_IMAGE_YN } } /* 이미지 여부 */
				, {name:"sysDelYn", label:_GOODS_COMMENT_GRID_LABEL.sysDelYn, width:"150", align:"center", sortable:false, formatter:"select", editoptions:{value:_DEL_YN } } /* 삭제여부 */
				, {name:"goodsId", label:_GOODS_COMMENT_GRID_LABEL.goodsId, width:"100", align:"center", sortable:false } /* 상품 ID */
				, {name:"goodsNm", label:_GOODS_COMMENT_GRID_LABEL.goodsNm, width:"200", align:"center", sortable:false } /* 상품명 */
				, {name:"sysRegDtm", label:_GOODS_COMMENT_GRID_LABEL.sysRegDtm, width:"200", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT } ]
			, multiselect : goodsCommentLayerOptions.multiselect
		}
		grid.create("layerGoodsCommentList", gridOptions);
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerGoodsCommentList" );
		var rowids = null;
		if(goodsCommentLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		goodsCommentLayerOptions.callBack (jsonArray );
		layer.close("goodsCommentSearch");
	}
	, searchGoodsCommentList : function () {
		var options = {
			searchParam : $("#displayGoodsCommentListForm").serializeJson()
		};
		grid.reload("layerGoodsCommentList", options);
	}
	, searchReset : function () {
		resetForm ("displayGoodsCommentListForm" );
	}
	, searchCompany : function () {
		var options = {
			multiselect : false
			, callBack : this.searchCompanyCallback
		}
		layerCompanyList.create (options );
	}
	, searchCompanyCallback : function (compList ) {
		if(compList.length > 0 ) {
			$("#displayGoodsCommentListForm #compNo").val (compList[0].compNo );
			$("#displayGoodsCommentListForm #compNm").val (compList[0].compNm );
		}
	}
}

//--------------------------------------------------------------------------------//
// History Layer
var historyLayerOptions = {
	histGb : null
	, goodsId : null
	, bomCd : null
}
var layerHistoryList = {
	create : function (option ) {
		historyLayerOptions = $.extend( {}, historyLayerOptions, option );
		var options = {
			url : _HISTORY_VIEW_LAYER_URL
			, data : historyLayerOptions
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "historyView"
			, width : 1100
			, height : 600
			, title : "이력 조회"
			, body : data
		};
		layer.create(config );
		layerHistoryList.initHistoryGrid();
	}
	, initHistoryGrid : function () {
		var histGb = $("#historyListForm #histGb").val();
		var gridUrl = '';
		var colModels;
		if(histGb == 'GOODS_DETAIL' ) {
			gridUrl = _GOODS_HISTORY_GRID_URL;
			colModels = [
							{name:"sysRegDtm", label:_HISTORY_VIEW_GRID_LABEL.sysRegDtm , width:"130", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
							, {name:"sysRegrNm", label:_HISTORY_VIEW_GRID_LABEL.sysRegrNm , width:"100", align:"center"}
							, {name:"histNo", label:_HISTORY_VIEW_GRID_LABEL.histNo , width:"80", align:"center"}
							, {name:"goodsId", label:_HISTORY_VIEW_GRID_LABEL.goodsId , width:"120", align:"center"}
							, {name:"goodsNm", label:_HISTORY_VIEW_GRID_LABEL.goodsNm , width:"250", align:"center"}
							, {name:"bndNo", label:_HISTORY_VIEW_GRID_LABEL.bndNo , width:"100", align:"center"}
							, {name:"goodsStatCd", label:_HISTORY_VIEW_GRID_LABEL.goodsStatCd , width:"80", align:"center", width:"150", align:"center", formatter:"select", editoptions:{value:_GOODS_STAT_CD } }
							, {name:"ntfId", label:_HISTORY_VIEW_GRID_LABEL.ntfId , width:"80", align:"center"}
							, {name:"mdlNm", label:_HISTORY_VIEW_GRID_LABEL.mdlNm , width:"120", align:"center"}
							, {name:"compNo", label:_HISTORY_VIEW_GRID_LABEL.compNo , width:"120", align:"center"}
							, {name:"kwd", label:_HISTORY_VIEW_GRID_LABEL.kwd , width:"150", align:"center"}
							, {name:"ctrOrg", label:_HISTORY_VIEW_GRID_LABEL.ctrOrg , width:"150", align:"center"}
							, {name:"minOrdQty", label:_HISTORY_VIEW_GRID_LABEL.minOrdQty , width:"100", align:"center"}
							, {name:"maxOrdQty", label:_HISTORY_VIEW_GRID_LABEL.maxOrdQty , width:"100", align:"center"}
							, {name:"dlvrMtdCd", label:_HISTORY_VIEW_GRID_LABEL.dlvrMtdCd , width:"100", align:"center", width:"150", align:"center", formatter:"select", editoptions:{value:_COMP_DLVR_MTD_CD } }
							, {name:"dlvrcPlcNo", label:_HISTORY_VIEW_GRID_LABEL.dlvrcPlcNo , width:"100", align:"center"}
							, {name:"compPlcNo", label:_HISTORY_VIEW_GRID_LABEL.compPlcNo , width:"100", align:"center"}
							, {name:"prWds", label:_HISTORY_VIEW_GRID_LABEL.prWds , width:"150", align:"center"}
							, {name:"freeDlvrYn", label:_HISTORY_VIEW_GRID_LABEL.freeDlvrYn, width:"100", align:"center", formatter:"select", editoptions:{value:_COMM_YN } }
							, {name:"importer", label:_HISTORY_VIEW_GRID_LABEL.importer , width:"100", align:"center"}
							, {name:"mmft", label:_HISTORY_VIEW_GRID_LABEL.mmft , width:"100", align:"center"}
							, {name:"taxGbCd", label:_HISTORY_VIEW_GRID_LABEL.taxGbCd, width:"100", align:"center", formatter:"select", editoptions:{value:_TAX_GB_CD } }
							, {name:"stkMngYn", label:_HISTORY_VIEW_GRID_LABEL.stkMngYn, width:"100", align:"center", formatter:"select", editoptions:{value:_COMM_YN } }
							, {name:"mdUsrNo", label:_HISTORY_VIEW_GRID_LABEL.mdUsrNo , width:"100", align:"center"}
							, {name:"pplrtRank", label:_HISTORY_VIEW_GRID_LABEL.pplrtRank , width:"100", align:"center"}
							, {name:"pplrtSetCd", label:_HISTORY_VIEW_GRID_LABEL.pplrtSetCd, width:"100", align:"center", formatter:"select", editoptions:{value:_PPLRT_SET_CD } }
							, {name:"saleStrtDtm", label:_HISTORY_VIEW_GRID_LABEL.saleStrtDtm, width:"130", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
							, {name:"saleEndDtm", label:_HISTORY_VIEW_GRID_LABEL.saleEndDtm, width:"130", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
							, {name:"showYn", label:_HISTORY_VIEW_GRID_LABEL.showYn, width:"100", align:"center", formatter:"select", editoptions:{value:_SHOW_YN } }
							, {name:"compGoodsId", label:_HISTORY_VIEW_GRID_LABEL.compGoodsId , width:"150", align:"center"}
							, {name:"webMobileGbCd", label:_HISTORY_VIEW_GRID_LABEL.webMobileGbCd, width:"100", align:"center", formatter:"select", editoptions:{value:_WEB_MOBILE_GB_CD } }
							, {name:"rtnPsbYn", label:_HISTORY_VIEW_GRID_LABEL.rtnPsbYn, width:"100", align:"center", formatter:"select", editoptions:{value:_COMM_YN } }
							, {name:"rtnMsg", label:_HISTORY_VIEW_GRID_LABEL.rtnMsg , width:"150", align:"center"}
							, {name:"prWdsShowYn", label:_HISTORY_VIEW_GRID_LABEL.prWdsShowYn, width:"100", align:"center", formatter:"select", editoptions:{value:_SHOW_YN } }
							, {name:"itemMngYn", label:_HISTORY_VIEW_GRID_LABEL.itemMngYn, width:"100", align:"center", formatter:"select", editoptions:{value:_COMM_YN } }
							, {name:"goodsTpCd", label:_HISTORY_VIEW_GRID_LABEL.goodsTpCd, width:"100", align:"center", formatter:"select", editoptions:{value:_GOODS_TP_CD } }
							, {name:"bigo", label:_HISTORY_VIEW_GRID_LABEL.bigo , width:"150", align:"center"}
							//, {name:"vdLinkUrl", label:_HISTORY_VIEW_GRID_LABEL.vdLinkUrl , width:"150", align:"center"}
							//, {name:"hits", label:_HISTORY_VIEW_GRID_LABEL.hits , width:"150", align:"center"}
						];
		}/* else if (histGb == 'BOM_DETAIL' ) {
			gridUrl = _BOM_HISTORY_GRID_URL;
		} */else if (histGb == 'ITEM_DETAIL' ) {
			gridUrl = _ITEM_HISTORY_GRID_URL;
			colModels = [
							{name:"columnId", label:_HISTORY_VIEW_GRID_LABEL.columnId , width:"100", align:"center", hidden:true}
							, {name:"sysUpdDtm", label:_HISTORY_VIEW_GRID_LABEL.sysUpdDtm , width:"130", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
							, {name:"columnNm", label:_HISTORY_VIEW_GRID_LABEL.columnNm , width:"120", align:"center", sortable:false }
							, {name:"value1", label:_HISTORY_VIEW_GRID_LABEL.value1 , width:"200", align:"center", sortable:false }
							, {name:"value2", label:_HISTORY_VIEW_GRID_LABEL.value2 , width:"200", align:"center", sortable:false }
							, {name:"sysUpdrNm", label:_HISTORY_VIEW_GRID_LABEL.sysUpdrNm , width:"100", align:"center"}
						];
		}else if (histGb == 'ITEM_DETAIL' ) {
			gridUrl = _ITEM_HISTORY_GRID_URL;
			colModels = [
							{name:"columnId", label:_HISTORY_VIEW_GRID_LABEL.columnId , width:"100", align:"center", hidden:true}
							, {name:"sysUpdDtm", label:_HISTORY_VIEW_GRID_LABEL.sysUpdDtm , width:"130", align:"center", formatter:gridFormat.date, dateformat:_COMMON_DATE_FORMAT }
							, {name:"columnNm", label:_HISTORY_VIEW_GRID_LABEL.columnNm , width:"120", align:"center", sortable:false }
							, {name:"value1", label:_HISTORY_VIEW_GRID_LABEL.value1 , width:"200", align:"center", sortable:false }
							, {name:"value2", label:_HISTORY_VIEW_GRID_LABEL.value2 , width:"200", align:"center", sortable:false }
							, {name:"sysUpdrNm", label:_HISTORY_VIEW_GRID_LABEL.sysUpdrNm , width:"100", align:"center"}
						];
		}
		var gridOptions = {
			url : gridUrl
			, height : 300
			, searchParam : $("#historyListForm").serializeJson()
			, paging : false
			, colModels : colModels
		}
		grid.create("historyList", gridOptions);
	}
	, searchHistoryList : function () {
		var options = {
			searchParam : $("#historyListForm").serializeJson()
		};
		grid.reload("historyList", options);
	}
	, searchReset : function () {
		resetForm ("historyListForm" );
	}
	, searchDateChange : function () {
		var term = $("#historyListForm #checkOptDate").children("option:selected").val();
		if(term == "") {
			$("#historyListForm #sysRegDtmStart").val("");
			$("#historyListForm #sysRegDtmEnd").val("");
		} else {
			setSearchDate(term, "sysRegDtmStart", "sysRegDtmEnd");
		}
	}

};

//--------------------------------------------------------------------------------//
// ----------------------
// 임직원 구매한도 사용 이력 조회
// ----------------------

var staffPrcUseHistoryLayerOptions = {
	 mbrNo : null
   , staffNo : null
   , staffScrtCode : null
   , baseDateSt : null
   , baseDateEd : null
}
var staffPrcUseLayerHistoryList = {
	create : function (option ) {
		staffPrcUseHistoryLayerOptions = $.extend( {}, staffPrcUseHistoryLayerOptions, option );

		var options = {
			url : _STAFF_PRC_USE_HISTORY_VIEW_LAYER_URL
			, data : staffPrcUseHistoryLayerOptions
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "staffPrcUseHistView"
			, width : 800
			, height : 600
			, title : "사용이력조회"
			, body : data
		};
		layer.create(config );
		staffPrcUseLayerHistoryList.initHistoryGrid();
	}
	, initHistoryGrid : function () {
		var gridUrl = '';
		var colModels;

		gridUrl = _STAFF_PRC_USE_HISTORY_GRID_URL;
		colModels = [
		              {name:"no", label:_STAFF_PRC_USE_HISTORY_VIEW_GRID_LABEL.no , width:"40", align:"center"}
					, {name:"ordNo", label:_STAFF_PRC_USE_HISTORY_VIEW_GRID_LABEL.ordNo , width:"120", align:"center"}
					, {name:"ordMdaCd", label:_STAFF_PRC_USE_HISTORY_VIEW_GRID_LABEL.ordMdaCd , width:"80", align:"center"}
					, {name:"ordAcptDtm", label:_STAFF_PRC_USE_HISTORY_VIEW_GRID_LABEL.ordStartDtm , width:"120", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
					, {name:"ordCpltDtm", label:_STAFF_PRC_USE_HISTORY_VIEW_GRID_LABEL.ordEndDtm , width:"120", align:"center" , formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
					, {name:"ordStat", label:_STAFF_PRC_USE_HISTORY_VIEW_GRID_LABEL.ordStat , width:"80", align:"center"}
					, {name:"prcDedAmt", label:_STAFF_PRC_USE_HISTORY_VIEW_GRID_LABEL.prcDedAmt , width:"120", align:"center"}
		];
		var gridOptions = {
			url : gridUrl
			, height : 300
			, searchParam : $("#staffPrcUseHistoryListForm").serializeJson()
			, paging : false
			, colModels : colModels
		}
		grid.create("staffPrcUseHistoryList", gridOptions);
	}

};

//--------------------------------------------------------------------------------//
// ----------------------
// 프리미엄 회원 이력 조회
// ----------------------

var mbrPrmHistoryLayerOptions = {
	 mbrNo : null
}
var mbrPrmLayerHistoryList = {
	create : function (option ) {
		mbrPrmHistoryLayerOptions = $.extend( {}, mbrPrmHistoryLayerOptions, option );

		var options = {
			url : _PREMIUM_HISTORY_VIEW_LAYER_URL
			, data : mbrPrmHistoryLayerOptions
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "premiumHistView"
			, width : 800
			, height : 600
			, title : "프리미엄 회원 이력 조회"
			, body : data
		};
		layer.create(config );
		mbrPrmLayerHistoryList.initHistoryGrid();
	}
	, initHistoryGrid : function () {
		var gridUrl = '';
		var colModels;

		gridUrl = _PREMIUM_HISTORY_GRID_URL;
		colModels = [
		              {name:"prmNo", label:_PREMIUM_HISTORY_VIEW_GRID_LABEL.prmNo , width:"40", align:"center"}
					, {name:"prmOrdNo", label:_PREMIUM_HISTORY_VIEW_GRID_LABEL.prmOrdNo , width:"120", align:"center"}
					, {name:"prmStDt", label:_PREMIUM_HISTORY_VIEW_GRID_LABEL.prmStDt , width:"120", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
					, {name:"prmEdDt", label:_PREMIUM_HISTORY_VIEW_GRID_LABEL.prmEdDt , width:"120", align:"center" , formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
					, {name:"prmStatCd", label:_PREMIUM_HISTORY_VIEW_GRID_LABEL.prmStatCd , width:"80", align:"center"}
					, {name:"autoExtPayYn", label:_PREMIUM_HISTORY_VIEW_GRID_LABEL.autoExtPayYn , width:"120", align:"center"}
		];
		var gridOptions = {
			url : gridUrl
			, height : 300
			, searchParam : $("#premiumHistoryListForm").serializeJson()
			, paging : false
			, colModels : colModels
		}
		grid.create("premiumHistoryList", gridOptions);
	}

};

//--------------------------------------------------------------------------------//
//Brand 콘텐츠 검색 Layer
var brandCntsLayerOptions = {
	callBack : undefined
	, multiselect : false
};
var layerBrandCntsList = {
	create : function (option ) {
		brandCntsLayerOptions = $.extend( {}, brandCntsLayerOptions, option );
		var options = {
			url : _BRAND_CNTS_SEARCH_LAYER_URL
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "brandCntsSearch"
			, width : 1000
			, height : 900
			, title : "브랜드 콘텐츠 조회"
			, body : data
			, button : "<button type=\"button\" onclick=\"layerBrandCntsList.confirm();\" class=\"btn_type1\">확인</button>"
		};
		layer.create(config);
		layerBrandCntsList.initBrandCntsGrid();
	}
	, initBrandCntsGrid : function () {
		var gridOptions = {
			url : _BRAND_CNTS_GRID_URL
			, height : 200
			, searchParam : $("#brandCntsListForm").serializeJson()
			, colModels : [
			               {name:"bndCntsNo", label:_BRAND_CNTS_SEARCH_GRID_LABEL.bndCntsNo, width:"100", key: true, align:"center"} /* 브랜드 콘텐츠 번호 */
			               , {name:"bndNo", label:_BRAND_CNTS_SEARCH_GRID_LABEL.bndNo, width:"100", align:"center"} /* 브랜드 번호 */
			               , {name:"bndNmKo", label:_BRAND_CNTS_SEARCH_GRID_LABEL.bndNmKo, width:"200", align:"center", sortable:false } /* 브랜드 국문 */
			               , {name:"bndNmEn", label:_BRAND_CNTS_SEARCH_GRID_LABEL.bndNmEn, width:"200", align:"center", sortable:false } /* 브랜드 영문 */
			               // 콘텐츠 구분 코드
			               , {name:"cntsGbCd", label:_BRAND_CNTS_SEARCH_GRID_LABEL.cntsGbCd, width:"100", align:"center", sortable:false, formatter:"select", editoptions:{value:_CNTS_GB } }
			               // 타이틀
			               , {name:"cntsTtl", label:_BRAND_CNTS_SEARCH_GRID_LABEL.cntsTtl, width:"200", align:"center"}
			               // 콘텐츠 이미지 경로
			               , {name:"cntsImgPath", label:_BRAND_CNTS_SEARCH_GRID_LABEL.cntsImgPath, width:"100", align:"center", formatter: function(cellvalue, options, rowObject) {
				            	   if(rowObject.cntsImgPath != "") {
				            		   return '<img src="<frame:imgUrl />' + rowObject.cntsImgPath + '" style="width:100px; height:100px;" alt="' + rowObject.cntsImgPath + '" />';
									} else {
										return '<img src="/images/noimage.png" style="width:100px; height:100px;" alt="NoImage" />';
									}
								}
							}
			               // 콘텐츠 모바일 이미지 경로
			               , {name:"cntsMoImgPath", label:_BRAND_CNTS_SEARCH_GRID_LABEL.cntsMoImgPath, width:"100", align:"center", formatter: function(cellvalue, options, rowObject) {
			            	   		if(rowObject.cntsMoImgPath != "") {
			            		   		return '<img src="<frame:imgUrl />' + rowObject.cntsMoImgPath + '" style="width:100px; height:100px;" alt="' + rowObject.cntsMoImgPath + '" />';
			            	   		} else {
			            	   			return '<img src="/images/noimage.png" style="width:100px; height:100px;" alt="NoImage" />';
									}
								}
							}
							// 썸네일 이미지 경로
							, {name:"tnImgPath", label:_BRAND_CNTS_SEARCH_GRID_LABEL.tnImgPath, width:"100", align:"center", formatter: function(cellvalue, options, rowObject) {
									if(rowObject.tnImgPath != "") {
										return '<img src="<frame:imgUrl />' + rowObject.tnImgPath + '" style="width:100px; height:100px;" alt="' + rowObject.tnImgPath + '" />';
									} else {
										return '<img src="/images/noimage.png" style="width:100px; height:100px;" alt="NoImage" />';
									}
								}
							}
							// 썸네일 모바일 이미지 경로
							, {name:"tnMoImgPath", label:_BRAND_CNTS_SEARCH_GRID_LABEL.tnMoImgPath, width:"100", align:"center", formatter: function(cellvalue, options, rowObject) {
									if(rowObject.tnMoImgPath != "") {
										return '<img src="<frame:imgUrl />' + rowObject.tnMoImgPath + '" style="width:100px; height:100px;" alt="' + rowObject.tnMoImgPath + '" />';
									} else {
										return '<img src="/images/noimage.png" style="width:100px; height:100px;" alt="NoImage" />';
									}
								}
							}
			]
			, multiselect : brandCntsLayerOptions.multiselect
		}
		grid.create("layerBrandCntsList", gridOptions);
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerBrandCntsList" );
		var rowids = null;
		if(brandCntsLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		brandCntsLayerOptions.callBack (jsonArray );
		layer.close("brandCntsSearch");
	}
	, searchBrandCntsList : function () {
		var options = {
			searchParam : $("#brandCntsListForm").serializeJson()
		};
		grid.reload("layerBrandCntsList", options);
	}
	, searchReset : function () {
		resetForm ("brandCntsListForm" );
	}
};

//--------------------------------------------------------------------------------//
//제외상품 일괄업로드 Layer
var goodsExListExcelUploadLayerOptions = {
	callBack : undefined
	, multiselect : false
};
var layerGoodsExListExcelUpload = {
	create : function (option ) {
		goodsExListExcelUploadLayerOptions = $.extend( {}, goodsExListExcelUploadLayerOptions, option );
		var options = {
			url : _GOODS_EX_LIST_EXCEL_UPLOAD_LAYER_URL
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "goodsExListExcelUpload"
			, width : 1000
			, height : 800
			, title : "제외상품 일괄업로드"
			, body : data
			, button : "<button type=\"button\" onclick=\"layerGoodsExListExcelUpload.confirm();\" class=\"btn_type1\">확인</button>"
		}
		layer.create(config);
	}
	, confirm : function () {
		var jsonArray = new Array();
		var message = new Array();
		var grid = $("#resultList" );
		var rowids = null;
		var check = true;
		rowids = grid.jqGrid('getGridParam', 'selarrrow');
		if (rowids == undefined || rowids == null || rowids == ''){
			alert("선택된 대상이 없습니다.");
			return;
		}
		for (var i = rowids.length - 1; i >= 0; i--) {
			if(jsonArray.length > 0 ){
				for (var j = jsonArray.length - 1; j >= 0; j--) {
					if (jsonArray[j].goodsId  ==  grid.jqGrid('getRowData', rowids[i]).goodsId ){
						message.push( grid.jqGrid('getRowData', rowids[i]).goodsId + " 중복된 상품입니다.");
						check = false ;
					}
				}
			}
			if (grid.jqGrid('getRowData', rowids[i]).resultYN  == '성공'){
				jsonArray.push( grid.jqGrid('getRowData', rowids[i])      );
			}else if(grid.jqGrid('getRowData', rowids[i]).resultYN  == '실패') {
				check = false ;
				message.push( grid.jqGrid('getRowData', rowids[i]).goodsId + " 조회가 안된 상품입니다.");
			}
		}
		if (!check){
			if(message != null && message.length > 0) {
				alert(message.join("\n"));
			}
		}else{
			fnGoodsExListExcelUploadPopCallBack(jsonArray);
		}
		//layer.close("goodsExListExcelUpload");
	}
};

//----------------------------------------------------------------------------------------------------------
// Email 발송 이력 내용
//----------------------------------------------------------------------------------------------------------
var emailSendHistoryListLayerOptions = {
		callBack : undefined
		, multiselect : false
		, param : {}
	};
var emailSendHistoryListLayer = {
	create : function (option) {
		$.extend(emailSendHistoryListLayerOptions, option);
		var options = {
			url : _EMAIL_SEND_HISTORY_LIST_LAYER_URL
			, dataType : "html"
			, data : emailSendHistoryListLayerOptions.param
			, callBack : function(result) {
				var config = {
					id : "emailSendHistoryListLayerView"
					, top : 100
					, width : 1200
					, height : 250
					, title : "Email 내용"
					, body : result
				}
				layer.create(config);
				emailSendHistoryListLayer.grid(option);
			}
		}
		ajax.call(options);
	}
	, grid : function(option) {
		$("#histNo").val(option.data.histNo);
		var options = {
			url : _EMAIL_SEND_HISTORY_LIST_LAYER_GRID_URL
				, height : 250
				, searchParam : $("#emailSendHistoryListLayerSearchForm").serializeJson()
				, colModels : [
	               {name:"histNo", label:_EMAIL_SEND_HISTORY_LIST_LAYER_GRID_LABEL.histNo, width:"70", sortable:false, align:"center"}
					, {name:"contents", label:_EMAIL_SEND_HISTORY_LIST_LAYER_GRID_LABEL.contents, width:"500", align:"left"}
					, {name:"detailContents", label:_EMAIL_SEND_HISTORY_LIST_LAYER_GRID_LABEL.detailContents, width:"1500", align:"left"}
				]
			, multiselect : emailSendHistoryListLayerOptions.multiselect
		};
		grid.create("emailSendHistoryListLayer", options);
	}
};

//--------------------------------------------------------------------------------//
// ----------------------
// 이벤트 투표 이력 조회
// ----------------------

var votingResultsLayerOptions = {
	 eventNo : null
}
var votingResultsList = {
	create : function (option ) {
		votingResultsLayerOptions = $.extend( {}, votingResultsLayerOptions, option );

		var options = {
			url : _VOTING_RESULTS_VIEW_LAYER_URL
			, data : votingResultsLayerOptions
			, dataType : "html"
			, callBack : this.callBackCreate
		};
		ajax.call(options );
	}
	, callBackCreate : function (data ) {
		var config = {
			id : "votingResultsView"
			, width : 600
			, height : 200
			, title : "투표 결과 조회"
			, body : data
		};
		layer.create(config );
		votingResultsList.initHistoryGrid();
	}
	, initHistoryGrid : function () {
		var gridUrl = '';
		var colModels;

		gridUrl = _VOTING_RESULTS_GRID_URL;
		colModels = [
//		              {name:"qstNo", label:_VOTING_RESULTS_VIEW_GRID_LABEL.qstNo , width:"40", align:"center"}
//					,
					{name:"qstNm", label:_VOTING_RESULTS_VIEW_GRID_LABEL.qstNm , width:"300", align:"center"}
					, {name:"letCnt", label:_VOTING_RESULTS_VIEW_GRID_LABEL.letCnt , width:"120", align:"center"}
					, {name:"letRate", label:_VOTING_RESULTS_VIEW_GRID_LABEL.letRate , width:"120", align:"center"}
		];
		var gridOptions = {
			url : gridUrl
			, rownumbers : true
			, rownumWidth : 40
			, height : 300
			, searchParam : $("#votingResultsListForm").serializeJson()
			, paging : false
			, colModels : colModels
		}
		grid.create("votingResultsList", gridOptions);
	}

};



//----------------------------------------------------------------------------------------------------------
// 개인정보점검 관리 - 개인정보 자율 점검을 위한 질의 사항 등록 layer
//----------------------------------------------------------------------------------------------------------
var pInfoCheckLayerOptions = {
		callBack : undefined
		, multiselect : false
	};
var pInfoCheckInsertLayer = {	// 점검 차수 등록 / 수정 레이어
	create : function (option) {
		$.extend(pInfoCheckLayerOptions, option);
		var options = {
			url : _PINFO_CHECK_ITEMS_LAYER_URL
			, dataType : "html"
			, data : {
				chkRnd : option.chkRnd == undefined ? undefined : option.chkRnd
				, chkNo : option.chkNo == undefined ? undefined : option.chkNo
			}
			, callBack : function(result) {
				var config = {
					id : "layerCheckListView"
					, top : 100
					, width : 1000
					, height : 800
					, title : "개인정보 자율 점검을 위한 질의 사항 등록"
					, body : result
					, button : option.button == undefined ? null : option.button
				}
				layer.create(config);
			}
		}
		ajax.call(options);
	}
}

// 점검 결과 등록/수정
var pInfoCheckResultLayer = {
	create : function (option) {
		$.extend(pInfoCheckLayerOptions, option);
		var options = {
			url : _PINFO_CHECK_RESULT_LAYER_URL
			, dataType : "html"
			, data : option.data
			, callBack : function(result) {
				var config = {
					id : "layerCheckResultView"
					, top : 100
					, width : 1500
					, height : 800
					, title : "개인정보 자율 점검 체크리스트"
					, body : result 
					, button : option.button == undefined ? null : option.button
				}
				layer.create(config);
			}
		}
		ajax.call(options);
	}
}


// 점검 관리 이력 layer
var pInfoCheckMgtLayer = {
	create : function (option) {
		$.extend(pInfoCheckLayerOptions, option);
		var options = {
			url : _PINFO_CHECK_MGT_LAYER_URL
			, dataType : "html"
			, data : option.data
			, callBack : function(result) {
				var config = {
					id : "layerCheckMgtView"
					, top : 100
					, width : 800
					, height : 1000
					, title : "개인정보 점검 관리이력 등록/확인"
					, body : result 
					, button : "<button type=\"button\" onclick=\"checkMgtSave();\" class=\"btn_type1\">등록</button>"
				}
				layer.create(config);
			}
		}
		ajax.call(options);
	}
}

// 연관콘텐츠 등록/수정
var contentsLayerOptions = {
	callBack : undefined
	, multiselect : true
	, param : {}
};
var layerContentsList = {
	create : function (data) {
		$.extend(contentsLayerOptions, data);
		var options = {
			url : _CONTENTS_SEARCH_LAYER_URL
			, dataType : "html"
			, data : contentsLayerOptions.param
			, callBack : function(result) {
				var config = {
					id : "layerContentsView"
					, top : 100
					, width : 1300
					, height : 800
					, title : "콘텐츠 조회"
					, body : result
					, button : "<button type=\"button\" onclick=\"layerContentsList.confirm();\" class=\"btn_type1\">확인</button>"
				}
				layer.create(config);
				layerContentsList.grid();
			}
		}
		ajax.call(options );
	}
	, confirm : function () {
		var jsonArray = new Array();
		var grid = $("#layerContentsList" );
		var rowids = null;
		if(contentsLayerOptions.multiselect ) {
			rowids = grid.jqGrid('getGridParam', 'selarrrow');
			for (var i = rowids.length - 1; i >= 0; i--) {
				jsonArray.push(grid.jqGrid('getRowData', rowids[i]));
			}
		} else {
			rowids = grid.jqGrid('getGridParam','selrow');
			jsonArray.push(grid.jqGrid('getRowData', rowids));
		}
		contentsLayerOptions.callBack (jsonArray );
		layer.close("layerContentsView");
	}
	, grid : function() {
		var options = {
			url : _CONTENTS_GRID_URL
			, height : 400
			, searchParam : $("#layerContentsSearchForm").serializeJson()
			, colModels : [
				  {name:"cntsNo", label:_CONTENTS_SEARCH_GRID_LABEL.cntsNo, width:"100", align:"center", classes:'pointer fontbold'}
				, {name:"cntsNm", label:_CONTENTS_SEARCH_GRID_LABEL.cntsNm, width:"100", align:"center"}
				, {name:"cntsStatCd", label:_CONTENTS_SEARCH_GRID_LABEL.cntsStatCd, width:"100", align:"center", hidden:true}
				, {name:"cntsStatNm", label:_CONTENTS_SEARCH_GRID_LABEL.cntsStatNm, width:"100", align:"center"}
				, {name:"cntsKindCd", label:_CONTENTS_SEARCH_GRID_LABEL.cntsKindCd, width:"100", align:"center", hidden:true}
				, {name:"cntsKindNm", label:_CONTENTS_SEARCH_GRID_LABEL.cntsKindNm, width:"100", align:"center"}
				, {name:"dispYn", label:_CONTENTS_SEARCH_GRID_LABEL.dispYn, width:"100", align:"center"}
				, {name:"dispStrtDtm", label:_CONTENTS_SEARCH_GRID_LABEL.dispStrtDtm, width:"100", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
				, {name:"dispEndDtm", label:_CONTENTS_SEARCH_GRID_LABEL.dispEndDtm, width:"100", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
				, {name:"stNm", label:_CONTENTS_SEARCH_GRID_LABEL.stNm, width:"150", align:"center"}
				, {name:"categoryGb", label:_CONTENTS_SEARCH_GRID_LABEL.categoryGb, width:"150", align:"center"}
				, {name:"sysRegrNm", label:_CONTENTS_SEARCH_GRID_LABEL.sysRegrNm, width:"100", align:"center"}
				, {name:"sysRegDtm", label:_CONTENTS_SEARCH_GRID_LABEL.sysRegDtm, width:"130", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
				, {name:"sysUpdrNm", label:_CONTENTS_SEARCH_GRID_LABEL.sysUpdrNm, width:"100", align:"center"}
				, {name:"sysUpdDtm", label:_CONTENTS_SEARCH_GRID_LABEL.sysUpdDtm, width:"130", align:"center", formatter:gridFormat.date, dateformat:"yyyy-MM-dd HH:mm:ss"}
			]
			, multiselect : contentsLayerOptions.multiselect
			, ondblClickRow : function(){
				if(contentsLayerOptions.multiselect ) {
					layerContentsList.confirm();
				}
			}
		};
		grid.create("layerContentsList", options);
	}
	, searchContentsList : function () {
		var options = {
			searchParam : $("#layerContentsSearchForm").serializeJson()
		};
		grid.reload("layerContentsList", options);
	}
	, searchReset : function () {
		resetForm("layerContentsSearchForm");
	}
}

//--------------------------------------------------------------------------------//
//택배사 등록 Layer
var hdcLayerOptions = {
	callBack : undefined
	, multiselect : false
};
var layerHdcList = {
	create : function (data) {
			$.extend(layerHdcList, hdcLayerOptions);
			var options = {
				url : _HDC_SEARCH_LAYER_URL
				, dataType : "html"
				, data : data
				, callBack : function(result) {
					var config = {
						id : "layerHdcView"
						, top : 100
						, width : 400
						, height : 200
						, title : "택배사 추가"
						, body : result
						, button : "<button type=\"button\" onclick=\"layerHdcList.confirm();\" class=\"btn_type1\">등록</button>"
					}
					layer.create(config);
				}
			}
			ajax.call(options);
	}
	, confirm : function () {
		var jsonArray = new Array();
		// var grid = $("#hdcList");
		// var rowids = null;
		var data = {
				dlvCompCd 	: $('#layerHdcCdSelect').val()
				, dlvCompNo : 1
				, ctrCode 	: $('#layerCtrCode').val()
				, apprYn 	: 'N'
		}
		// rowids = grid.jqGrid('getGridParam','selrow');
		jsonArray.push(data);
		addHdcCallBack(data);
		// hdcLayerOptions.callBack(jsonArray);
		layer.close("layerHdcView");
	}
}
//--------------------------------------------------------------------------------//

