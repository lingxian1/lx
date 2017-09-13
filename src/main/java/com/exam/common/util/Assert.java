package com.exam.common.util;

import com.exam.common.ErrorCode;
import com.exam.common.ExamException;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Objects;

public class Assert {

    public static void notNull(Object o, int status, String message) {
        if (o == null) {
            throw new ExamException(status, message);
        }
    }

    public static void notNull(Object o, ErrorCode message) {
        if (o == null) {
            throw new ExamException(message);
        }
    }

    public static void equals(Object src, Object tar, int staus, String message) {
        if (!Objects.equals(src, tar)) {
            throw new ExamException(staus, message);
        }
    }

    public static void notEqual(@Null Object type, Object target, ErrorCode errorCode) {
        if (Objects.equals(type, target)) {
            throw new ExamException(errorCode);
        }
    }


    public static void isNull(Object o, String message) {
        isNull(o, 400, message);
    }

    public static void isNull(Object o, int status, String message) {
        if (o != null) {
            throw new ExamException(status, message);
        }
    }

    public static void isNull(Object o, ErrorCode message) {
        if (o != null) {
            throw new ExamException(message);
        }
    }

    public static void isTrue(boolean b, String message) {
        if (!b) {
            throw new ExamException(400, message);
        }
    }

    public static void isTrue(boolean b, int status, String message) {
        if (!b) {
            throw new ExamException(status, message);
        }
    }

    public static void isTrue(boolean b,ErrorCode message) {
        if (!b) {
            throw new ExamException(message);
        }
    }

    public static void isFalse(boolean b, String message) {
        if (b) {
            throw new ExamException(400, message);
        }
    }

    public static void isFalse(boolean b, int status, String message) {
        if (b) {
            throw new ExamException(status, message);
        }
    }

    public static void isFalse(boolean b,ErrorCode message) {
        if (b) {
            throw new ExamException(message);
        }
    }


    public static void strExist(String s) {
        if (s == null || "".equals(s.trim())) {
            throw new IllegalArgumentException();
        }
    }

    public static void strExist(String s, int status, String message) {
        if (s == null || "".equals(s.trim())) {
            throw new ExamException(status, message);
        }
    }

    /**
     * 判断list为空
     * @param s
     * @param message
     */
    public static void listExist(List s, ErrorCode message) {
        if (s == null || s.isEmpty()) {
            throw new ExamException(message);
        }
    }

    public static void notZero(int value, int status, String message) {
        if (value == 0) {
            throw new ExamException(status, message);
        }
    }

    public static void notZero(double value, int status, String message) {
        if (value == 0) {
            throw new ExamException(status, message);
        }
    }

}
