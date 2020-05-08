package com.vitality.enums;

public enum TaskStatus {
    DELETE_STATUS_YES("N","未同步"),

    /** 操作失败 */
    DELETE_STATUS_NO("P", "已同步");

    TaskStatus(String value, String msg){
        this.val = value;
        this.msg = msg;
    }

    public String val() {
        return val;
    }

    public String msg() {
        return msg;
    }

    private String val;
    private String msg;
}
