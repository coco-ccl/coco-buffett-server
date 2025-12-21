package org.example.cocobuffettserver.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.response.EquippedItemResponse;
import org.example.cocobuffettserver.dto.response.ItemResponse;
import org.example.cocobuffettserver.entity.ItemEntity;
import org.example.cocobuffettserver.entity.MemberEntity;
import org.example.cocobuffettserver.entity.MemberEquippedItemEntity;
import org.example.cocobuffettserver.entity.MemberOwnedItemEntity;
import org.example.cocobuffettserver.exception.CocoBuffettErrorCode;
import org.example.cocobuffettserver.exception.CocoBuffettException;
import org.example.cocobuffettserver.repository.ItemRepository;
import org.example.cocobuffettserver.repository.MemberEquippedItemRepository;
import org.example.cocobuffettserver.repository.MemberOwnedItemRepository;
import org.example.cocobuffettserver.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemService {

    ItemRepository itemRepository;
    MemberRepository memberRepository;
    MemberOwnedItemRepository memberOwnedItemRepository;
    MemberEquippedItemRepository memberEquippedItemRepository;

    public List<ItemResponse> getItemList(String memberId) {
        List<ItemEntity> allItems = itemRepository.findAll();

        return allItems.stream()
                .map(item -> ItemResponse.builder()
                        .itemId(item.getItemId())
                        .type(item.getType())
                        .name(item.getName())
                        .price(item.getPrice())
                        .color(item.getColor())
                        .isOwned(true)      // 수정 필요
                        .build())
                .collect(Collectors.toList());
    }

    public void purchaseItem(String memberId, String itemId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.MEMBER_NOT_FOUND));

        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.ITEM_NOT_FOUND));

        // 이미 소유한 아이템인지 확인
        if (memberOwnedItemRepository.existsByMember_MemberIdAndItem_ItemId(memberId, itemId)) {
            throw new CocoBuffettException(CocoBuffettErrorCode.ALREADY_OWNED_ITEM);
        }

        MemberOwnedItemEntity ownedItem = MemberOwnedItemEntity.builder()
                .member(member)
                .item(item)
                .build();

        memberOwnedItemRepository.save(ownedItem);
    }

    public void equipItem(String memberId, String itemId) {

        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.ITEM_NOT_FOUND));

        if (!memberOwnedItemRepository.existsByMember_MemberIdAndItem_ItemId(memberId, itemId)) {
            throw new CocoBuffettException(CocoBuffettErrorCode.ITEM_NOT_OWNED);
        }

        MemberEquippedItemEntity equippedItem = memberEquippedItemRepository.findById(memberId)
                .orElse(MemberEquippedItemEntity.builder()
                        .memberId(memberId)
                        .build());

        equippedItem.equipItem(item.getType(), itemId);

        memberEquippedItemRepository.save(equippedItem);
    }

    public List<EquippedItemResponse> getEquippedItems(String memberId) {

        MemberEquippedItemEntity equippedItem = memberEquippedItemRepository.findById(memberId)
                .orElse(null);

        List<EquippedItemResponse> result = new ArrayList<>();

        if (equippedItem == null) {
            return result;
        }

        addEquippedItemIfExists(result, equippedItem.getFaceItemId());
        addEquippedItemIfExists(result, equippedItem.getHairItemId());
        addEquippedItemIfExists(result, equippedItem.getTopItemId());
        addEquippedItemIfExists(result, equippedItem.getBottomItemId());
        addEquippedItemIfExists(result, equippedItem.getShoesItemId());

        return result;
    }

    private void addEquippedItemIfExists(List<EquippedItemResponse> result, String itemId) {
        if (itemId != null) {
            itemRepository.findById(itemId).ifPresent(item -> result.add(EquippedItemResponse.builder()
                .itemId(item.getItemId())
                .type(item.getType().name().toLowerCase())
                .color(item.getColor())
                .build()));
        }
    }
}
