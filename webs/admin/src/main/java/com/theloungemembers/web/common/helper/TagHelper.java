package com.theloungemembers.web.common.helper;

import org.springframework.stereotype.Component;

@Component // 타임리프에서 @tagHelper로 호출
public class TagHelper {

//    public String compNo(HttpSession httpSession,
//                         String funcNm,
//                         String requireYn,
//                         String defaultCompNo,
//                         String defaultCompNm,
//                         String placeholder,
//                         String disableSearchYn,
//                         String idIndex,
//                         Boolean forceDefaultValue) {
//
//        StringBuilder sb = new StringBuilder(1024);
//
//        // 기본값 방어 로직 (기존 Tag doStartTag 방어코드 동일)
//        if (StringUtils.isEmpty(defaultCompNo)) {
//            defaultCompNo = "";
//        }
//        if (StringUtils.isEmpty(defaultCompNm)) {
//            defaultCompNm = "";
//        }
//        if (StringUtils.isEmpty(requireYn)) {
//            requireYn = "N";
//        }
//        if (StringUtils.isEmpty(funcNm)) {
//            funcNm = "searchCompany";
//        }
//        if (StringUtils.isEmpty(disableSearchYn)) {
//            disableSearchYn = "N";
//        }
//        if (idIndex == null) {
//            idIndex = "";
//        }
//        if (forceDefaultValue == null) {
//            forceDefaultValue = false;
//        }
//
//        // 세션 정보 조회 (스프링 HttpSession 활용)
//        AdminSession session = (AdminSession) httpSession.getAttribute("adminSession");
//
//        if (session != null) {
//            // 1. 시스템 관리자일 경우 (USR_GRP_10)
//            if (AdminConstants.USR_GRP_10.equals(session.getUsrGrpCd())) {
//                sb.append("<input type=\"hidden\"");
//                if ("Y".equals(requireYn)) {
//                    sb.append(" class=\"validate[required, custom[onlyNum]]\"");
//                }
//                sb.append(" name=\"compNo").append(idIndex).append("\"");
//                sb.append(" id=\"compNo").append(idIndex).append("\"");
//                sb.append(" title=\"업체번호\"");
//                sb.append(" value=\"").append(defaultCompNo).append("\" />");
//
//                sb.append("<input type=\"text\" readonly");
//                if ("Y".equals(requireYn)) {
//                    sb.append(" class=\"wth120 readonly validate[required]\"");
//                } else {
//                    sb.append(" class=\"wth120 readonly\"");
//                }
//                sb.append(" id=\"compNm").append(idIndex).append("\"");
//                sb.append(" name=\"compNm").append(idIndex).append("\"");
//                sb.append(" title=\"업체명\"");
//                sb.append(" value=\"").append(defaultCompNm).append("\"");
//                sb.append(" placeholder=\"").append(placeholder).append("\"");
//
//                if ("Y".equals(disableSearchYn)) {
//                    sb.append(" disabled />");
//                } else {
//                    sb.append(" />");
//                    sb.append("&nbsp;<button type=\"button\" class=\"btn_h25_type1\" onclick=\"")
//                      .append(funcNm).append("('").append(idIndex).append("');\">검색</button>");
//                }
//
//            } else {
//                // 2. 일반 업체/사용자일 경우
//                String compNoVal = StringUtils.isEmpty(defaultCompNo) ?
//                        (forceDefaultValue ? defaultCompNo : String.valueOf(session.getCompNo())) : defaultCompNo;
//                String compNmVal = StringUtils.isEmpty(defaultCompNm) ?
//                        (forceDefaultValue ? defaultCompNm : session.getCompNm()) : defaultCompNm;
//
//                sb.append("<input type=\"hidden\"");
//                if ("Y".equals(requireYn)) {
//                    sb.append(" class=\"validate[required, custom[onlyNum]]\"");
//                }
//                sb.append(" name=\"compNo").append(idIndex).append("\"");
//                sb.append(" id=\"compNo").append(idIndex).append("\"");
//                sb.append(" title=\"업체번호\"");
//                sb.append(" value=\"").append(compNoVal != null ? compNoVal : "").append("\" />");
//
//                sb.append("<input type=\"text\" readonly");
//                if ("Y".equals(requireYn)) {
//                    sb.append(" class=\"wth120 readonly validate[required]\"");
//                } else {
//                    sb.append(" class=\"wth120 readonly\"");
//                }
//                sb.append(" id=\"compNm").append(idIndex).append("\"");
//                sb.append(" name=\"compNm").append(idIndex).append("\"");
//                sb.append(" title=\"업체명\"");
//                sb.append(" value=\"").append(compNmVal != null ? compNmVal : "").append("\"");
//                sb.append(" placeholder=\"").append(placeholder).append("\"");
//
//                if (StringUtils.equals(AdminConstants.USR_GB_2010, session.getUsrGbCd())) {
//                    if ("Y".equals(disableSearchYn)) {
//                        sb.append(" disabled />");
//                    } else {
//                        sb.append(" />");
//                        sb.append("&nbsp;<button type=\"button\" class=\"btn_h25_type1\" onclick=\"")
//                          .append(funcNm).append("('").append(idIndex).append("');\">검색</button>");
//                    }
//                } else {
//                    sb.append(" disabled />");
//                }
//            }
//        }
//
//        return sb.toString();
//    }

    // 파라미터를 편하게 넘기기 위한 오버로딩 메서드 (기본값 세팅용)
//    public String compNo(HttpSession httpSession, String funcNm, String disableSearchYn, String placeholder) {
//        return compNo(httpSession, funcNm, "N", "", "", placeholder, disableSearchYn, "", false);
//    }
}