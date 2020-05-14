package org.cgic.commons.exception;

/**
 * @Description 通用异常
 * @Author: charleyZZZZ
 * @Date: 2020/4/13 10:50
 * @Version 1.0
 */
public class CommonException extends BaseException {


    protected CommonException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public CommonException(String code, String message) {
        this(code, message, (Object[]) null);
    }

    public CommonException(String code, Throwable e, Object... parameters) {
        super(code, e, parameters);
    }
}
