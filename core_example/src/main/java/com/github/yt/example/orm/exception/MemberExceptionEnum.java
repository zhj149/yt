package com.github.yt.example.orm.exception;

public enum MemberExceptionEnum {

    ERROR_11("异常11！"),
    ERROR_12("异常12！"),
    ERROR_13("异常13！");

    private String message;

    MemberExceptionEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
