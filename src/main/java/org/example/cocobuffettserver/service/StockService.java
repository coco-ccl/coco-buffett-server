package org.example.cocobuffettserver.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.dto.response.StockResponse;
import org.example.cocobuffettserver.entity.StockEntity;
import org.example.cocobuffettserver.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StockService {

    StockRepository stockRepository;

    public List<StockResponse> getStockList() {
        List<StockEntity> stocks = stockRepository.findAll();

        return stocks.stream()
                .map(stock -> StockResponse.builder()
                        .name(stock.getName())
                        .ticker(stock.getTicker())
                        .currentPrice(stock.getCurrentPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
