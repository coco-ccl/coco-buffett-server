package org.example.cocobuffettserver.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class MemberStockInfo {

    String name;
    String ticker;
    Integer quantity;
    Long currentPrice;
}
