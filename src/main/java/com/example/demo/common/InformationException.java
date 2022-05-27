package com.example.demo.common;

/**
 * ClassName: InformationException
 * Description:
 * date: 2022/5/27 10:44
 *
 * @author zyu
 * @since JDK 1.8
 */
public class InformationException extends Exception {
    public static final int code = Result.INFORMATION_ERROR;

    public InformationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
