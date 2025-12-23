package org.example.cocobuffettserver.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class MemberStocksResponse {

    Long deposit;
    List<MemberStockInfo> stocks;
}
