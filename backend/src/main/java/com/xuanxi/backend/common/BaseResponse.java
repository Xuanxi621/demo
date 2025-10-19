package com.xuanxi.backend.common;

import com.xuanxi.backend.exception.ErrCode;
import lombok.Data;

import java.io.Serializable;


@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;
    private T data;


    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(ErrCode errCode) {
        this(errCode.getCode(), errCode.getMsg());
    }
}
