package org.example.cocobuffettserver.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.exception.CocoBuffettErrorCode;
import org.example.cocobuffettserver.exception.CocoBuffettException;
import org.example.cocobuffettserver.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {

    MemberRepository memberRepository;

    public String generateToken(String memberId) {
        return Base64.getEncoder().encodeToString(memberId.getBytes());
    }

    public String validateToken(String token) {
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

    public String extractMemberIdFromAuthorizationHeader(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new CocoBuffettException(CocoBuffettErrorCode.UNAUTHORIZED);
        }

        String token = authorization.substring(7);
        return validateToken(token);
    }

    public String extractMemberIdFromAuthorizationHeaderOrNull(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        try {
            String token = authorization.substring(7);
            return validateToken(token);
        } catch (CocoBuffettException e) {
            return null;
        }
    }
}
