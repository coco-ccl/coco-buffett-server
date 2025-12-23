package org.example.cocobuffettserver.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.common.ApiResponse;
import org.example.cocobuffettserver.dto.response.StockResponse;
import org.example.cocobuffettserver.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cocobuffett/v1/stocks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StockController {

    StockService stockService;

    @GetMapping("/list")
    public ApiResponse<List<StockResponse>> getStockList() {
        List<StockResponse> stocks = stockService.getStockList();
        return ApiResponse.success(stocks);
    }
}
