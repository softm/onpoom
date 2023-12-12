package com.quickex.core.exception;


import com.quickex.core.util.MessageUtils;
import com.quickex.core.util.StringUtils;

/**
 * Basic anomaly
 *
 * @author ffzh.thero
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Module
     */
    private final String module;

    /**
     * code
     */
    private final String code;

    /**
     * args
     */
    private final Object[] args;

    /**
     * error msg
     */
    private final String defaultMessage;

    public BaseException(String module, String code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public BaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BaseException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public BaseException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(code)) {
            message = MessageUtils.message(code, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }

    public String getModule() {
        return module;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
