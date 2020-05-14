package org.cgic.commons.exception;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.io.Serializable;

/**
 * @Author: charleyZZZZ
 * @Date: 2019/6/19 11:39
 * @Version 1.0
 */
public class BaseException extends Exception implements Serializable,IBaseException {
    private static final long serialVersionUID = -3139953521399947760L;

    private String code;
    private String descriptionKey;
    private Object[] parameters;

    protected BaseException(String code, String descriptionKey, Object[] parameters) {
        super(descriptionKey);
        this.code = code;
        this.descriptionKey = descriptionKey;
        this.parameters = parameters;
    }
    protected BaseException(String code,Throwable cause, Object[] parameters){
        super(code, cause);
        this.parameters = parameters;
        this.code = code;
    }


    public String getCode() {
        return this.code;
    }

    public String getDescriptionKey() {
        return this.descriptionKey;
    }

    public Object[] getParameters() {
        return this.parameters;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
