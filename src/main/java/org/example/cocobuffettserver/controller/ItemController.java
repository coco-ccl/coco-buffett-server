package org.example.cocobuffettserver.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.common.ApiResponse;
import org.example.cocobuffettserver.dto.request.ItemEquipRequest;
import org.example.cocobuffettserver.dto.request.ItemPurchaseRequest;
import org.example.cocobuffettserver.dto.response.EquippedItemResponse;
import org.example.cocobuffettserver.dto.response.ItemResponse;
import org.example.cocobuffettserver.exception.CocoBuffettErrorCode;
import org.example.cocobuffettserver.exception.CocoBuffettException;
import org.example.cocobuffettserver.service.ItemService;
import org.example.cocobuffettserver.service.MemberService;
import org.springframework.web.bind.annotation.*;

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

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new CocoBuffettException(CocoBuffettErrorCode.UNAUTHORIZED);
        }

        String token = authorization.substring(7);

        String memberId = memberService.extractMemberIdFromToken(token);

        List<ItemResponse> items = itemService.getItemList(memberId);

        return ApiResponse.success(items);
    }

    @PostMapping("/purchase")
    public ApiResponse<Void> purchaseItem(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ItemPurchaseRequest request) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new CocoBuffettException(CocoBuffettErrorCode.UNAUTHORIZED);
        }

        String token = authorization.substring(7);

        String memberId = memberService.extractMemberIdFromToken(token);

        itemService.purchaseItem(memberId, request.getItemId());

        return ApiResponse.success();
    }

    @PostMapping("/equip")
    public ApiResponse<Void> equipItem(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ItemEquipRequest request) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new CocoBuffettException(CocoBuffettErrorCode.UNAUTHORIZED);
        }

        String token = authorization.substring(7);

        String memberId = memberService.extractMemberIdFromToken(token);

        itemService.equipItem(memberId, request.getItemId());

        return ApiResponse.success();
    }

    @GetMapping("/equipped")
    public ApiResponse<List<EquippedItemResponse>> getEquippedItems(
            @RequestHeader(value = "Authorization", required = false) String authorization) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new CocoBuffettException(CocoBuffettErrorCode.UNAUTHORIZED);
        }

        String token = authorization.substring(7);

        String memberId = memberService.extractMemberIdFromToken(token);

        List<EquippedItemResponse> equippedItems = itemService.getEquippedItems(memberId);

        return ApiResponse.success(equippedItems);
    }
}
