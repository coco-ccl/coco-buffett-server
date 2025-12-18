package org.example.cocobuffettserver.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.request.SigninRequest;
import org.example.cocobuffettserver.dto.request.SignupRequest;
import org.example.cocobuffettserver.dto.response.SigninResponse;
import org.example.cocobuffettserver.entity.MemberEntity;
import org.example.cocobuffettserver.exception.CocoBuffettErrorCode;
import org.example.cocobuffettserver.exception.CocoBuffettException;
import org.example.cocobuffettserver.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MemberService {

    MemberRepository memberRepository;

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
}