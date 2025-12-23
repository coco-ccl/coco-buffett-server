package org.example.cocobuffettserver.scheduler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.cocobuffettserver.service.StockService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StockPriceScheduler {

    StockService stockService;

    @Scheduled(fixedRate = 5000)
    public void updateStockPrices() {
        log.info("주식 가격 업데이트 시작");
        stockService.updateAllStockPrices();
        log.info("주식 가격 업데이트 완료");
    }
}
