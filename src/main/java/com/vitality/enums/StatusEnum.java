package com.vitality.enums;

public enum StatusEnum {

        STATUS_ADD("A","新增"),

        STATUS_DELETE("D","删除"),

        STATUS_UPDATE("U", "修改");



        StatusEnum(String value, String msg){
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

