package com.leyou.common.exception;


import com.leyou.common.enums.ResultCodeEnum;
/**
 * 通用异常
 * @Author: caidi
 * @Date: file/11/24 12:36
 * @Version 1.0
 */
public class CustomeException extends RuntimeException{
    private String code;

    private String msg;

    public CustomeException(String msg) {
        super(msg);
    }

    public CustomeException(ResultCodeEnum resultCodeEnum) {
        this(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }

    public CustomeException(ResultCodeEnum resultCodeEnum, String msg) {
        super(msg);
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getCode();
    }

    public CustomeException(Integer code, String msg) {
        super(msg);
        this.code = code.toString();
        this.msg = msg;
    }

    public CustomeException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
