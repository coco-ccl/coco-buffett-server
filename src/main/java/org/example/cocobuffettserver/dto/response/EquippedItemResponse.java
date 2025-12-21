package org.example.cocobuffettserver.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class EquippedItemResponse {

    @JsonProperty("item_id")
    String itemId;

    String type;

    String color;
}
