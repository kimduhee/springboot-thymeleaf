package com.framework.app.common.exception;

public class BizException extends BaseException{

    public BizException() {
        super("ERRCM000000");
    }

    public BizException(String errCode) {
        super(errCode);
    }
    public BizException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public BizException(String errCode, Object[] args) {
        super(errCode, args);
    }

    public BizException(String errCode, Object[] args, String errMessage) {
        super(errCode, args, errMessage);
    }

    public BizException(Exception e) {
        super(e);
    }

    public BizException(String errCode, Exception e) {
        super(errCode, e);
    }

    public BizException(String errCode, Object[] args, Exception e) {
        super(errCode, args, e);
    }

    public BizException(String errCode, Object[] args, String errMessage, Exception e) {
        super(errCode, args, errMessage, e);
    }
}
