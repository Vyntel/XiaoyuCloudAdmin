package com.xiaoyu.common.core.exception;

/**
 * 业务异常
 */
public class ServiceException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(500, message);
    }

    public ServiceException(Integer code, String message) {
        super(code, message);
    }

    public ServiceException(String message, Throwable cause) {
        super(500, message, cause);
    }
}