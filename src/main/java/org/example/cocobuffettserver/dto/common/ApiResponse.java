package org.example.cocobuffettserver.dto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ApiResponse {
    Integer code;
    String message;

    public static ApiResponse success() {
        return new ApiResponse(0, "success");
    }
}