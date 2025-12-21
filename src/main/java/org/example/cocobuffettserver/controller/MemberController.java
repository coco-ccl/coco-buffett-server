package org.example.cocobuffettserver.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.request.SigninRequest;
import org.example.cocobuffettserver.dto.request.SignupRequest;
import org.example.cocobuffettserver.dto.common.ApiResponse;
import org.example.cocobuffettserver.dto.response.SigninResponse;
import org.example.cocobuffettserver.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cocobuffett/v1/members")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MemberController {

    MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<Void> signUp(@RequestBody SignupRequest request) {
        memberService.signup(request);
        return ApiResponse.success();
    }

    @PostMapping("/signin")
    public ApiResponse<SigninResponse> signIn(@RequestBody SigninRequest request) {
        SigninResponse response = memberService.signIn(request);
        return ApiResponse.success(response);
    }
}