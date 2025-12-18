package org.example.cocobuffettserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CocoBuffettErrorCode {

    // common
    INVALID_PARAM(HttpStatus.BAD_REQUEST, 10001, "invalid param"),

    // member 20000 ~
    DUPLICATED_MEMBER_ID(HttpStatus.CONFLICT, 20001, "duplicated member id"),
    MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED, 20002, "member not found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, 20003, "invalid password"),

    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, 99999, "unknown exception occured");


    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    CocoBuffettErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}