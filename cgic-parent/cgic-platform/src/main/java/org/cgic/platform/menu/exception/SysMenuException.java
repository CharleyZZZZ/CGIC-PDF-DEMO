package org.cgic.platform.menu.exception;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.cgic.commons.exception.BaseException;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2020/4/13 11:38
 * @Version 1.0
 */
public class SysMenuException extends BaseException {


    protected SysMenuException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public SysMenuException(String code, String message) {
        this(code, message, (Object[]) null);
    }

    public enum SysMenuError {
        GET_TOP_MENU_ERROR("4010201", "无法获取顶层菜单.");

        private String code;
        private String message;

        private SysMenuError(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
