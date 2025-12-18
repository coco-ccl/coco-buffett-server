package org.example.cocobuffettserver.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.common.ApiResponse;
import org.example.cocobuffettserver.dto.response.ItemResponse;
import org.example.cocobuffettserver.exception.CocoBuffettErrorCode;
import org.example.cocobuffettserver.exception.CocoBuffettException;
import org.example.cocobuffettserver.service.ItemService;
import org.example.cocobuffettserver.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cocobuffett/v1/items")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemController {

    ItemService itemService;
    MemberService memberService;

    @GetMapping("/list")
    public ApiResponse<List<ItemResponse>> getItems(
            @RequestHeader(value = "Authorization", required = false) String authorization) {

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new CocoBuffettException(CocoBuffettErrorCode.UNAUTHORIZED);
        }

        // Bearer 토큰 추출
        String token = authorization.substring(7);

        // 토큰에서 memberId 추출
        String memberId = memberService.extractMemberIdFromToken(token);

        // 아이템 리스트 조회
        List<ItemResponse> items = itemService.getItemList(memberId);

        return ApiResponse.success(items);
    }
}
