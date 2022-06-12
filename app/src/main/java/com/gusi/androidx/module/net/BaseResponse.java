package com.gusi.androidx.module.net;

import java.io.Serializable;

/**
 * @author Ylw
 * @since 2022/6/12 9:27
 */
public class BaseResponse<T> implements Serializable {
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_IO_ERROR = 1;
    private T data;
    private int errorCode;
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "data=" + data +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
