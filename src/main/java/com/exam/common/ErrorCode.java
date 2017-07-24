package com.exam.common;

public enum ErrorCode {
    /**
     * 默认的成功信息.
     */
    DEFAULT_SUCCESS(200, "ok"),
    /**
     * 默认的失败信息.
     */
    DEFAULT_ERROR(500, "error"),

    PHONE_OR_PASSWORD_ERROR(10001, "用户名或者密码不正确"),


    USER_ERROR(10002, "用户未登陆请返回登陆页面signin"),
    EXAM_FINISHED(10003, "考试已完成"),
    ORDER_OPERATION_LOG_PUBLISH_ERROR(20014, "", false),
    SYSTEM_PERSISTENT_INCORRECT_KEY(2, "持久化层查询键错误/编程错误"),
    SYSTEM_NULL_OBJECT(3, "为空的对象"),
    CREDIT_SAVE_RECORD_ERROR(80001, ""),
   ;

    String description;
    int code;
    boolean returnError = true;

    ErrorCode(int code) {
        this.code = code;
        this.description = this.name();
    }

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    ErrorCode(int code, String description, boolean returnError) {
        this.code = code;
        this.description = description;
    }


    public boolean isReturnError() {
        return returnError;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
