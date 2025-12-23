package org.example.cocobuffettserver.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.constants.DefaultConstants;
import org.example.cocobuffettserver.dto.request.SigninRequest;
import org.example.cocobuffettserver.dto.request.SignupRequest;
import org.example.cocobuffettserver.dto.response.MemberInfoResponse;
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

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MemberService {

    MemberRepository memberRepository;
    ItemRepository itemRepository;
    MemberOwnedItemRepository memberOwnedItemRepository;
    MemberEquippedItemRepository memberEquippedItemRepository;
    AuthService authService;

    @Transactional
    public void signup(SignupRequest request) {
        if (memberRepository.existsById(request.getMemberId())) {
            throw new CocoBuffettException(CocoBuffettErrorCode.DUPLICATED_MEMBER_ID);
        }

        MemberEntity memberEntity = MemberEntity.builder()
                .memberId(request.getMemberId())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .balance(DefaultConstants.INITIAL_BALANCE)
                .build();

        memberRepository.save(memberEntity);

        // 기본 아이템 소유
        for (String itemId : DefaultConstants.DEFAULT_ITEM_IDS) {
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
                .faceItemId(DefaultConstants.DEFAULT_FACE_ITEM_ID)
                .hairItemId(DefaultConstants.DEFAULT_HAIR_ITEM_ID)
                .topItemId(DefaultConstants.DEFAULT_TOP_ITEM_ID)
                .bottomItemId(DefaultConstants.DEFAULT_BOTTOM_ITEM_ID)
                .shoesItemId(DefaultConstants.DEFAULT_SHOES_ITEM_ID)
                .build();

        memberEquippedItemRepository.save(equippedItem);
    }

    @Transactional
    public SigninResponse signIn(SigninRequest request) {
        MemberEntity member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.MEMBER_NOT_FOUND));

        if (!member.getPassword().equals(request.getPassword())) {
            throw new CocoBuffettException(CocoBuffettErrorCode.INVALID_PASSWORD);
        }

        String accessToken = authService.generateToken(member.getMemberId());

        return SigninResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public MemberInfoResponse getMemberInfo(String memberId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CocoBuffettException(CocoBuffettErrorCode.MEMBER_NOT_FOUND));

        return MemberInfoResponse.builder()
                .id(member.getMemberId())
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

}