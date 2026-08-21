package com.theloungemembers.web.sample.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theloungemembers.core.dto.ApiResponse;
import com.theloungemembers.core.exception.BusinessException;
import com.theloungemembers.core.util.ResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sample")
@RequiredArgsConstructor
public class SampleRestController {

    @GetMapping("/tree")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getCategoryTree() {
        return ResponseUtil.success(createNodeList());
    }

    private List<Map<String, Object>> createNodeList() {
        List<Map<String, Object>> treeList = new ArrayList<>();

        // 최상위 노드
        treeList.add(createNode("ROOT", "#", "전체 카테고리", true));

        // 1뎁스
        treeList.add(createNode("CAT_100", "ROOT", "의류/패션", true));
        treeList.add(createNode("CAT_200", "ROOT", "가전/디지털", false));
        treeList.add(createNode("CAT_300", "ROOT", "식품", false));

        // 2뎁스 (의류/패션 하위)
        treeList.add(createNode("CAT_101", "CAT_100", "남성의류", false));
        treeList.add(createNode("CAT_102", "CAT_100", "여성의류", false));

        // 2뎁스 (가전/디지털 하위)
        treeList.add(createNode("CAT_201", "CAT_200", "모바일/태블릿", false));

        return treeList;
    }

    // 더미 노드 생성 헬퍼 메소드
    private Map<String, Object> createNode(String id, String parent, String text, boolean opened) {
        Map<String, Object> node = new HashMap<>();
        node.put("catId", id);
        node.put("parentCatId", parent);
        node.put("catName", text);
        
        Map<String, Object> state = new HashMap<>();
        state.put("opened", opened);
        node.put("state", state);

        return node;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCategoryDetail(@PathVariable String id) {

        List<Map<String, Object>> nodeList = createNodeList(); // 트리 데이터를 생성 (실제 구현에서는 데이터베이스에서 조회해야 함)

        Map<String, Object> category = nodeList.stream()
                .filter(node -> id.equals(node.get("catId")))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Category not found for id: " + id));

        return ResponseUtil.success(category);
    }
}