package org.example.cocobuffettserver.exception;

import lombok.Getter;

@Getter
public class CocoBuffettException extends RuntimeException {

    CocoBuffettErrorCode errorCode;

    public CocoBuffettException(CocoBuffettErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CocoBuffettException(CocoBuffettErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}