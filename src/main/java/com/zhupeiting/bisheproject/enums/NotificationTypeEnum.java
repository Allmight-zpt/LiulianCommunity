package com.zhupeiting.bisheproject.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");

    private int noteType;
    private String name;

    public int getNoteType() {
        return noteType;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int noteType, String name) {
        this.noteType = noteType;
        this.name = name;
    }
    public static String nameOfType(int type){
        for (NotificationTypeEnum value : NotificationTypeEnum.values()) {
            if(value.getNoteType() == type){
                return value.getName();
            }
        }
        return "";
    }

}
