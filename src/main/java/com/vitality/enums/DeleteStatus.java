package com.vitality.enums;

public enum DeleteStatus {
    DELETE_STATUS_YES("I","已删除"),

    /** 操作失败 */
    DELETE_STATUS_NO("A", "未删除");

    DeleteStatus(String value, String msg){
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
