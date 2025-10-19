package com.xuanxi.backend.common;

import com.xuanxi.backend.exception.ErrCode;

public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(200,"success",data);
    }

    public static <T> BaseResponse<T> error(){
        return new BaseResponse<>(ErrCode.ERROR_SYSTEAM);
    }
}
