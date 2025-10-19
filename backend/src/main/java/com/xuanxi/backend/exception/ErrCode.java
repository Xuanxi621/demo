package com.xuanxi.backend.exception;

import lombok.Getter;

@Getter
public enum ErrCode {

    ERROR_SYSTEAM(40000,"请求参数错误");

    private final int code;
    private final String msg;

    ErrCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
