package org.example.cocobuffettserver.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.request.SignupRequest;
import org.example.cocobuffettserver.entity.MemberEntity;
import org.example.cocobuffettserver.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MemberService {

    MemberRepository memberRepository;

    public void signup(SignupRequest request) {
        if (memberRepository.existsById(request.getMemberId())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        MemberEntity memberEntity = MemberEntity.builder()
                .memberId(request.getMemberId())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .build();

        memberRepository.save(memberEntity);
    }
}