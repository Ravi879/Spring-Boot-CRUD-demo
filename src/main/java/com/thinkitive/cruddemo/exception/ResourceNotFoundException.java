package com.thinkitive.cruddemo.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2152219922352821559L;

    private String messageCode;
    private String errorInfo = "";

    public ResourceNotFoundException(String messageCode) {
        this.messageCode = messageCode;
    }

    public ResourceNotFoundException(String messageCode, String errorInfo) {
        this.messageCode = messageCode;
        this.errorInfo = errorInfo;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

}
