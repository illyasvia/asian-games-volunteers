package com.example.demo.common;

import javax.sound.sampled.Line;

/**
 * ClassName: InformationException
 * Description:
 * date: 2022/5/27 10:44
 *
 * @author zyu
 * @since JDK 1.8
 */
public class InformationException extends Exception {
    public int code;

    public InformationException(String message) {
        super(message);
    }
    public InformationException(Integer code, String message){
        super(message);
        this.code = code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
