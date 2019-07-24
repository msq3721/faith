package com.msq.faith.entity;

import lombok.Data;

@Data
public class JsonEntity<T> {
    private String status;
    private String msg;
    T data;

    public JsonEntity(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public JsonEntity(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
