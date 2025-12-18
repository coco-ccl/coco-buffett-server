package org.example.cocobuffettserver.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    String itemId;
    String type;
    String name;
    Integer price;
    Boolean isOwned;
}
