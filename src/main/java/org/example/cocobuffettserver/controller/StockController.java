package org.example.cocobuffettserver.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.common.ApiResponse;
import org.example.cocobuffettserver.dto.request.StockTradeRequest;
import org.example.cocobuffettserver.dto.response.StockResponse;
import org.example.cocobuffettserver.service.AuthService;
import org.example.cocobuffettserver.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cocobuffett/v1/stocks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StockController {

    StockService stockService;
    AuthService authService;

    @GetMapping("/list")
    public ApiResponse<List<StockResponse>> getStockList() {
        List<StockResponse> stocks = stockService.getStockList();
        return ApiResponse.success(stocks);
    }

    @PostMapping("/trade")
    public ApiResponse<Void> trade(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody StockTradeRequest request) {

        String memberId = authService.extractMemberIdFromAuthorizationHeader(authorization);

        stockService.trade(memberId, request);

        return ApiResponse.success();
    }
}
