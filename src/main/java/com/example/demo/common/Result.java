package com.example.demo.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 该类用于向前端返回封装结果
 */
@Data
@NoArgsConstructor
@ToString
public class Result<T>{
    public static final int WRONG_USERNAME = 1001;
    public static final int WRONG_PASSWORD = 1002;
    public static final int FULL_REGISTRATION = 1003;
    public static final int INFORMATION_ERROR = 1005;
    public static final int UNKNOWN_ERROR = 500;

    private int code;  // 返回码。0：success；1：false
    private String msg;// 信息
    private T data;    //

    public Result(T data){
        this.data = data;
    }

    public static Result<Object> success(){
        Result<Object> result = new Result<>();
        result.setCode(0);
        result.setMsg("success");
        return result;
    }

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>(data);
        result.setCode(0);
        result.setMsg("success");
        return result;
    }

    public static Result error(int code, String msg){
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
