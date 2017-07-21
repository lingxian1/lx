package com.exam.common;

public class ExamException extends RuntimeException {
    private int status = 500;

    public ExamException(int status, String message) {
        this(status, message, null);
    }

    public ExamException(int status, String message, Exception e) {
        super(message, e);
        this.status = status;
    }

    public ExamException(ErrorCode errorCode, Exception e) {
        this(errorCode.getCode(),errorCode.getDescription(),e);
    }

    public ExamException(ErrorCode errorCode) {
        this(errorCode,null);
    }

    public int getStatus() {
        return status;
    }
}
