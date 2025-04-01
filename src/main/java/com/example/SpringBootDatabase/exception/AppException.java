package com.example.SpringBootDatabase.exception;


public class AppException extends RuntimeException{
    private Errorcode errorCode;

    public AppException(Errorcode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public Errorcode getErrorCode() {
        return errorCode;
    }
}
