package com.zhupeiting.bisheproject.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复！"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试！"),
    SYSTEM_ERROR(2004,"服务器冒烟了，请稍后再试！"),
    TYPE_PARAM_NOT_FOUND(2005,"评论类型错误或不存在！"),
    COMMENT_NOT_FOUND(2006, "你要回复的评论不存在了，要不换个试试？"),
    COMMENT_IS_EMPTY(2007,"输入内容为空，请重新编辑！"),
    READ_NOTIFICATION_FAIL(2008,"你没有权限阅读该消息！"),
    NOTIFICATION_NOT_FOUND(2009,"你的消息不见了！");

    private String message;
    private Integer code;
    CustomizeErrorCode(Integer code ,String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
