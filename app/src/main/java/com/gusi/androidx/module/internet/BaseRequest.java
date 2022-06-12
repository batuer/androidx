package com.gusi.androidx.module.internet;

import java.io.Serializable;

/**
 * @author Ylw
 * @since 2022/6/12 9:27
 */
public class BaseRequest<T> implements Serializable {
  public static final int CODE_SUCCESS = 0;
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
}
