package org.example.cocobuffettserver.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorResponse {

    Integer code;
    String message;

    public static ErrorResponse of(CocoBuffettErrorCode errorCode){
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(CocoBuffettErrorCode errorCode, String detailMessage){
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(detailMessage != null ? detailMessage : errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(HttpStatus httpStatus){
        return ErrorResponse.builder()
                .code(httpStatus.value())
                .message(httpStatus.getReasonPhrase())
                .build();
    }
}
