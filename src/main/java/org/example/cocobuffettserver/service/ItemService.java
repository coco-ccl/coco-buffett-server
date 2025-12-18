package org.example.cocobuffettserver.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.response.ItemResponse;
import org.example.cocobuffettserver.entity.ItemEntity;
import org.example.cocobuffettserver.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemService {

    ItemRepository itemRepository;

    public List<ItemResponse> getItemList(String memberId) {
        List<ItemEntity> allItems = itemRepository.findAll();

        return allItems.stream()
                .map(item -> ItemResponse.builder()
                        .itemId(item.getItemId())
                        .type(item.getType())
                        .name(item.getName())
                        .price(item.getPrice())
                        .isOwned(true)      // 수정 필요
                        .build())
                .collect(Collectors.toList());
    }
}
