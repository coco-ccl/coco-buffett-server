package org.example.cocobuffettserver.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.request.SigninRequest;
import org.example.cocobuffettserver.dto.request.SignupRequest;
import org.example.cocobuffettserver.dto.common.ApiResponse;
import org.example.cocobuffettserver.dto.response.MemberStocksResponse;
import org.example.cocobuffettserver.dto.response.SigninResponse;
import org.example.cocobuffettserver.service.MemberService;
import org.example.cocobuffettserver.service.StockService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cocobuffett/v1/members")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MemberController {

    MemberService memberService;
    StockService stockService;

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

    @GetMapping("/{member_id}/stocks")
    public ApiResponse<MemberStocksResponse> getMemberStocks(@PathVariable("member_id") String memberId) {
        MemberStocksResponse response = stockService.getMemberStocks(memberId);
        return ApiResponse.success(response);
    }
}