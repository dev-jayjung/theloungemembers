package com.theloungemembers.web.sample.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.theloungemembers.core.api.ApiMenuGroupQuery;
import com.theloungemembers.core.api.ApiMenuGroupResult;
import com.theloungemembers.core.api.ApiMenuGroupService;
import com.theloungemembers.core.api.ApiMenuQuery;
import com.theloungemembers.core.api.ApiMenuResult;
import com.theloungemembers.core.api.ApiMenuService;
import com.theloungemembers.core.helper.ModelMapperHelper;
import com.theloungemembers.core.type.ServiceStatus;
import com.theloungemembers.web.api.dto.ApiMenuGroupResponse;
import com.theloungemembers.web.api.dto.ApiMenuResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/sample")
@RequiredArgsConstructor
public class SampleViewController {

    private final ApiMenuGroupService apiMenuGroupService;
    private final ApiMenuService apiMenuService;
    private final ModelMapperHelper modelMapperHelper;

    @GetMapping("/guide")
    public String guide() {
        return "sample/guide";
    }

    @GetMapping("/form")
    public String form() {
        return "sample/form";
    }

    @GetMapping("/grid")
    public String grid(Model model) {
        List<ApiMenuGroupResult> apiMenuGroupList = apiMenuGroupService.getList(new ApiMenuGroupQuery());
        model.addAttribute("apiMenuGroupList", modelMapperHelper.mapList(apiMenuGroupList, ApiMenuGroupResponse.class));

        ApiMenuQuery apiMenuQuery = new ApiMenuQuery();
        apiMenuQuery.setOnService(ServiceStatus.IN_SERVICE);
        List<ApiMenuResult> apiMenuList = apiMenuService.getList(apiMenuQuery);
        model.addAttribute("apiMenuList", modelMapperHelper.mapList(apiMenuList, ApiMenuResponse.class));

        return "sample/grid";
    }

    @GetMapping("/editor")
    public String editor() {
        return "sample/editor";
    }

    @GetMapping("/upload")
    public String upload() {
        return "sample/upload";
    }

    @GetMapping("/tree")
    public String tree() {
        return "sample/tree";
    }

    @GetMapping("/popup")
    public String popup() {
        return "sample/popup";
    }

    @GetMapping("/grid-layer")
    public String layer() {
        return "sample/popup/grid_layer";
    }

    @GetMapping("/tab")
    public String tab() {
        return "sample/tab";
    }

    @GetMapping("/date")
    public String date() {
        return "sample/date";
    }

    @GetMapping("/format-string")
    public String formatString() {
        return "sample/format_string";
    }

    @GetMapping("/form-validation")
    public String formValidation() {
        return "sample/form_validation";
    }
}