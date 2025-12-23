package org.example.cocobuffettserver.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    String name;
    String ticker;
    Long currentPrice;
}
