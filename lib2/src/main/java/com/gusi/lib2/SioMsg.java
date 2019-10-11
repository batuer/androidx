package com.gusi.lib2;

import java.io.Serializable;

/**
 * @author Ylw
 * @since 2019/9/27 19:31
 */
public class SioMsg implements Serializable {
    private static final long serialVersionUID = 6162261696723531940L;
    private String status;
    private String type;
    private Object data;
    private Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
