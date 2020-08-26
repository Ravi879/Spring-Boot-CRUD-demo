package com.thinkitive.cruddemo.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = -3066144920466166379L;

    private String messageCode;
    private String errorInfo;

    public ResourceAlreadyExistsException(String messageCode, String errorInfo) {
        this.messageCode = messageCode;
        this.errorInfo = errorInfo;
    }

    public ResourceAlreadyExistsException(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

}
