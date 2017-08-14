package com.exam.common;

/**
 * 错误信息
 */
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
    USER_ERROR(10002, "用户未登陆请返回登陆页面"),
    USER_EMPTY(10003, "用户不存在"),
    EXAM_FINISHED(10004, "考试已完成"),
    DATA_ERROR(10005, "数据字段格式或长度错误"),
    EXAM_ID_ERROR(10006, "考试Id错误"),
    EXAM_PUBLISH_ERROR(10007, "考试已发布"),
    EXAM_PUBLISH_SCORE_ERROR(10008, "server：考试分值或题目数量不一致"),
    EXAM_SUBMIT_ERROR(10009, "提交失败"),
    EXAM_SUBMIT_OUT_OF_DATE(10010, "提交失败"),
    EXAM_GRADE_NULL(10011, "没有考试成绩信息"),
    EXAM_PHONE_ERROR(10012, "手机号码格式错误或重复"),
    QUESTION_TYPE_ERROR(10013, "该试题分类试题数量为空"),
    QUESTION_CLASS_ERROR(10014, "分类上限错误"),
    SYSTEM_PERSISTENT_INCORRECT_KEY(2, "持久化层查询键错误/编程错误"),
    SYS_NULL_OBJECT(3, "为空的对象"),
    SYS_LOGIN_TIMEOUT(4, "登陆过期"),
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
