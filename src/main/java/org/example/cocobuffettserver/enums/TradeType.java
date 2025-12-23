package org.example.cocobuffettserver.enums;

import lombok.Getter;

@Getter
public enum TradeType {
    BUY("buy"),
    SELL("sell");

    private final String value;

    TradeType(String value) {
        this.value = value;
    }
}
