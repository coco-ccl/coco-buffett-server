package org.example.cocobuffettserver.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.enums.TradeType;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class StockTradeRequest {
    String ticker;
    TradeType tradeType;
    Integer quantity;
    Long price;
}
