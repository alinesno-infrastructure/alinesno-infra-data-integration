package com.alinesno.infra.data.integration.vo;

import com.alinesno.infra.common.facade.response.ResultCodeEnum;

public class ResponseBean {
    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return this.code;
    }

    public ResponseBean() {
    }

    public ResponseBean setCode(ResultCodeEnum resultCode) {
        this.code = resultCode.getCode();
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ResponseBean setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public ResponseBean setData(Object data) {
        this.data = data;
        return this;
    }
}
