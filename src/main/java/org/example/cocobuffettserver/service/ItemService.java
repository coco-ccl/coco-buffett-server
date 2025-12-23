package org.example.cocobuffettserver.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.response.EquippedItemResponse;
import org.example.cocobuffettserver.dto.response.ItemPurchaseResponse;
import org.example.cocobuffettserver.dto.response.ItemResponse;
import org.example.cocobuffettserver.dto.response.OwnedItemResponse;
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

        // memberId가 null이면 모든 아이템의 isOwned를 false로 설정
        if (memberId == null) {
            return allItems.stream()
                    .map(item -> ItemResponse.builder()
                            .itemId(item.getItemId())
                            .type(item.getType().getValue())
                            .name(item.getName())
                            .price(item.getPrice())
                            .color(item.getColor())
                            .isOwned(false)
                            .build())
                    .collect(Collectors.toList());
        }

        List<MemberOwnedItemEntity> ownedItems = memberOwnedItemRepository.findByMember_MemberId(memberId);

        var ownedItemIds = ownedItems.stream()
                .map(ownedItem -> ownedItem.getItem().getItemId())
                .collect(Collectors.toSet());

        return allItems.stream()
                .map(item -> ItemResponse.builder()
                        .itemId(item.getItemId())
                        .type(item.getType().getValue())
                        .name(item.getName())
                        .price(item.getPrice())
                        .color(item.getColor())
                        .isOwned(ownedItemIds.contains(item.getItemId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemPurchaseResponse purchaseItem(String memberId, String itemId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.MEMBER_NOT_FOUND));

        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.ITEM_NOT_FOUND));

        // 이미 소유한 아이템인지 확인
        if (memberOwnedItemRepository.existsByMember_MemberIdAndItem_ItemId(memberId, itemId)) {
            throw new CocoBuffettException(CocoBuffettErrorCode.ALREADY_OWNED_ITEM);
        }

        // 잔액 확인
        if (member.getBalance() < item.getPrice()) {
            throw new CocoBuffettException(CocoBuffettErrorCode.INSUFFICIENT_BALANCE);
        }

        // 잔액 차감
        member.deductBalance(item.getPrice());

        MemberOwnedItemEntity ownedItem = MemberOwnedItemEntity.builder()
                .member(member)
                .item(item)
                .build();

        memberOwnedItemRepository.save(ownedItem);

        return ItemPurchaseResponse.builder()
                .remainingBalance(member.getBalance())
                .build();
    }

    @Transactional
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
                .type(item.getType().getValue())
                .color(item.getColor())
                .build()));
        }
    }

    public List<OwnedItemResponse> getOwnedItems(String memberId) {
        List<MemberOwnedItemEntity> ownedItems = memberOwnedItemRepository.findByMember_MemberId(memberId);

        return ownedItems.stream()
                .map(ownedItem -> {
                    ItemEntity item = ownedItem.getItem();
                    return OwnedItemResponse.builder()
                            .itemId(item.getItemId())
                            .type(item.getType().getValue())
                            .color(item.getColor())
                            .price(item.getPrice())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
