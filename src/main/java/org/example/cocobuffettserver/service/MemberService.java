package org.example.cocobuffettserver.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.constants.DefaultItemConstants;
import org.example.cocobuffettserver.dto.request.SigninRequest;
import org.example.cocobuffettserver.dto.request.SignupRequest;
import org.example.cocobuffettserver.dto.response.SigninResponse;
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

import java.util.Base64;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MemberService {

    MemberRepository memberRepository;
    ItemRepository itemRepository;
    MemberOwnedItemRepository memberOwnedItemRepository;
    MemberEquippedItemRepository memberEquippedItemRepository;

    public void signup(SignupRequest request) {
        if (memberRepository.existsById(request.getMemberId())) {
            throw new CocoBuffettException(CocoBuffettErrorCode.DUPLICATED_MEMBER_ID);
        }

        MemberEntity memberEntity = MemberEntity.builder()
                .memberId(request.getMemberId())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .build();

        memberRepository.save(memberEntity);

        // 기본 아이템 소유
        for (String itemId : DefaultItemConstants.DEFAULT_ITEM_IDS) {
            ItemEntity item = itemRepository.findById(itemId).orElseThrow();
            MemberOwnedItemEntity ownedItem = MemberOwnedItemEntity.builder()
                    .member(memberEntity)
                    .item(item)
                    .build();
            memberOwnedItemRepository.save(ownedItem);
        }

        // 기본 아이템 착용
        MemberEquippedItemEntity equippedItem = MemberEquippedItemEntity.builder()
                .memberId(request.getMemberId())
                .faceItemId(DefaultItemConstants.DEFAULT_FACE_ITEM_ID)
                .hairItemId(DefaultItemConstants.DEFAULT_HAIR_ITEM_ID)
                .topItemId(DefaultItemConstants.DEFAULT_TOP_ITEM_ID)
                .bottomItemId(DefaultItemConstants.DEFAULT_BOTTOM_ITEM_ID)
                .shoesItemId(DefaultItemConstants.DEFAULT_SHOES_ITEM_ID)
                .build();

        memberEquippedItemRepository.save(equippedItem);
    }

    public SigninResponse signin(SigninRequest request) {
        MemberEntity member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.MEMBER_NOT_FOUND));

        if (!member.getPassword().equals(request.getPassword())) {
            throw new CocoBuffettException(CocoBuffettErrorCode.INVALID_PASSWORD);
        }

        String accessToken = Base64.getEncoder().encodeToString(member.getMemberId().getBytes());

        return SigninResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public String extractMemberIdFromToken(String token) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(token);
            String memberId = new String(decodedBytes);

            // 회원이 존재하는지 검증
            if (!memberRepository.existsById(memberId)) {
                throw new CocoBuffettException(CocoBuffettErrorCode.INVALID_TOKEN);
            }

            return memberId;
        } catch (IllegalArgumentException e) {
            throw new CocoBuffettException(CocoBuffettErrorCode.INVALID_TOKEN);
        }
    }
}