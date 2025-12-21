package org.example.cocobuffettserver.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.common.ApiResponse;
import org.example.cocobuffettserver.dto.request.ItemEquipRequest;
import org.example.cocobuffettserver.dto.request.ItemPurchaseRequest;
import org.example.cocobuffettserver.dto.response.EquippedItemResponse;
import org.example.cocobuffettserver.dto.response.ItemResponse;
import org.example.cocobuffettserver.dto.response.OwnedItemResponse;
import org.example.cocobuffettserver.service.AuthService;
import org.example.cocobuffettserver.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cocobuffett/v1/items")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemController {

    ItemService itemService;
    AuthService authService;

    @GetMapping("/list")
    public ApiResponse<List<ItemResponse>> getItems(
            @RequestHeader(value = "Authorization", required = false) String authorization) {

        String memberId = authService.extractMemberIdFromAuthorizationHeaderOrNull(authorization);

        List<ItemResponse> items = itemService.getItemList(memberId);

        return ApiResponse.success(items);
    }

    @PostMapping("/purchase")
    public ApiResponse<Void> purchaseItem(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ItemPurchaseRequest request) {

        String memberId = authService.extractMemberIdFromAuthorizationHeader(authorization);

        itemService.purchaseItem(memberId, request.getItemId());

        return ApiResponse.success();
    }

    @PostMapping("/equip")
    public ApiResponse<Void> equipItem(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ItemEquipRequest request) {

        String memberId = authService.extractMemberIdFromAuthorizationHeader(authorization);

        itemService.equipItem(memberId, request.getItemId());

        return ApiResponse.success();
    }

    @GetMapping("/equipped")
    public ApiResponse<List<EquippedItemResponse>> getEquippedItems(
            @RequestHeader(value = "Authorization", required = false) String authorization) {

        String memberId = authService.extractMemberIdFromAuthorizationHeader(authorization);

        List<EquippedItemResponse> equippedItems = itemService.getEquippedItems(memberId);

        return ApiResponse.success(equippedItems);
    }

    @GetMapping("/owned/me")
    public ApiResponse<List<OwnedItemResponse>> getOwnedItems(
            @RequestHeader(value = "Authorization", required = false) String authorization) {

        String memberId = authService.extractMemberIdFromAuthorizationHeader(authorization);

        List<OwnedItemResponse> ownedItems = itemService.getOwnedItems(memberId);

        return ApiResponse.success(ownedItems);
    }
}
