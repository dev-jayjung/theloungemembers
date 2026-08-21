package com.theloungemembers.web.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.theloungemembers.core.api.ApiMemberResult;
import com.theloungemembers.core.api.ApiMemberService;
import com.theloungemembers.core.api.ApiMenuGroupQuery;
import com.theloungemembers.core.api.ApiMenuGroupResult;
import com.theloungemembers.core.api.ApiMenuGroupService;
import com.theloungemembers.core.api.ApiMenuQuery;
import com.theloungemembers.core.api.ApiMenuResult;
import com.theloungemembers.core.api.ApiMenuService;
import com.theloungemembers.core.helper.JsonMapperHelper;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.type.ServiceStatus;
import com.theloungemembers.web.api.dto.ApiMemberResponse;
import com.theloungemembers.web.api.dto.ApiMenuGroupResponse;
import com.theloungemembers.web.api.dto.ApiMenuResponse;

import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;

@Controller
@RequestMapping("/api-members")
@RequiredArgsConstructor
public class ApiMemberViewController {

    private final ApiMemberService apiMemberService;
    private final ApiMenuGroupService apiMenuGroupService;
    private final ApiMenuService apiMenuService;
    private final ModelMapperHelper modelMapperHelper;
    private final JsonMapperHelper jsonMapperHelper;

    @GetMapping
    public String apiMemberList(Model model) {
        List<ApiMenuGroupResult> apiMenuGroupList = apiMenuGroupService.getList(new ApiMenuGroupQuery());
        model.addAttribute("apiMenuGroupList", modelMapperHelper.mapList(apiMenuGroupList, ApiMenuGroupResponse.class));

        ApiMenuQuery apiMenuQuery = new ApiMenuQuery();
        apiMenuQuery.setOnService(ServiceStatus.IN_SERVICE);
        List<ApiMenuResult> apiMenuList = apiMenuService.getList(apiMenuQuery);
        model.addAttribute("apiMenuList", modelMapperHelper.mapList(apiMenuList, ApiMenuResponse.class));

        return "api/member_list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        ApiMemberResponse member = new ApiMemberResponse();

        member.setAllowMsgNotify("1");
        member.setOnService(ServiceStatus.STOPPED);
        member.setIsEncrypted((short) 0);

        model.addAttribute("member", member);

        List<ApiMenuGroupResult> apiMenuGroupList = apiMenuGroupService.getList(new ApiMenuGroupQuery());
        model.addAttribute("apiMenuGroupList", modelMapperHelper.mapList(apiMenuGroupList, ApiMenuGroupResponse.class));

        return "api/member_form";
    }

    @GetMapping("/detail")
    public String detailForm(@RequestParam Long id, Model model) {
        ApiMemberResult member = apiMemberService.get(id);

        if (member != null) {
            model.addAttribute("member", modelMapperHelper.map(member, ApiMemberResponse.class));

            if (member.getEncData() != null) {
                Map<String, String> map = jsonMapperHelper.readValue(member.getEncData(),
                        new TypeReference<Map<String, String>>() {
                        });

                model.addAttribute("encInfo", map.get("enc_info"));
                model.addAttribute("encKey", map.get("enc_key"));
                model.addAttribute("encIv", map.get("enc_iv"));
            }
        }

        List<ApiMenuGroupResult> apiMenuGroupList = apiMenuGroupService.getList(new ApiMenuGroupQuery());
        model.addAttribute("apiMenuGroupList", modelMapperHelper.mapList(apiMenuGroupList, ApiMenuGroupResponse.class));

        return "api/member_form";
    }
}