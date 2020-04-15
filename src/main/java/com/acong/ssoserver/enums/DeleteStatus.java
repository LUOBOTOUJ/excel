package com.acong.ssoserver.enums;

public enum DeleteStatus {
    DELETE_STATUS_YES(1,"已删除"),

    /** 操作失败 */
    DELETE_STATUS_NO(0, "未删除");

    DeleteStatus(Integer value, String msg){
        this.val = value;
        this.msg = msg;
    }

    public Integer val() {
        return val;
    }

    public String msg() {
        return msg;
    }

    private Integer val;
    private String msg;
}
