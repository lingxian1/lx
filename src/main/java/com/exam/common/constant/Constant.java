package com.exam.common.constant;

import com.exam.common.entity.ExamExamineeEntity;

public class Constant {

    public static final String AUTHORIZATION = "token";
    public static final String CURRENT_USER_ID = "current_user_id";
    public static final int ORDER_ACCEPT_UNCOMPLETED_COUNT_LIMIT = 10;
    public static final int USER_CAN_CREATE_ORDER_CREDIT_LIMIT = 60;
    public static final int USER_CREATE_ORDER_MAX_COUNT = 10;
    public static final String SYSTEM_MESSAGE_SENDER = "0";
//    private static OrderEntity defaultOrderEntity;

    public static int TOKEN_EXPIRES_HOUR = 10;
    private static ExamExamineeEntity  defaultUserEntity;


}
