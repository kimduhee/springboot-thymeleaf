package com.framework.app.common.exception;

import com.framework.app.common.factory.ErrorMessageSourceFactory;
import com.framework.app.common.util.BeanUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@Slf4j
public class BaseException extends RuntimeException{

    protected String errCode = "ERRCM000000";
    protected String errMessage = "";
    protected Object[] args;

    public BaseException() {
        super();
    }

    public BaseException(String errCode) {
        super(errCode);
        this.errCode = errCode;
    }

    public BaseException(String errCode, Object[] args) {
        super(errCode);
        this.errCode = errCode;
        this.args = args;
    }

    public BaseException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public BaseException(String errCode, Object[] args, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.args = args;
    }

    public BaseException(Exception e) {
        super(e);
    }

    public BaseException(String errCode, Exception e) {
        super(errCode, e);
        this.errCode = errCode;
    }

    public BaseException(String errCode, Object[] args, Exception e) {
        super(errCode, e);
        this.errCode = errCode;
        this.args = args;
    }

    public BaseException(String errCode, String errMessage, Exception e) {
        super(errMessage, e);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public BaseException(String errCode, Object[] args, String errMessage, Exception e) {
        super(errMessage, e);
        this.errCode = errCode;
        this.errMessage = errMessage;
        this.args = args;
    }

    public String getMessage() {

        if(StringUtils.isEmpty(errMessage)) {
            ErrorMessageSourceFactory errorMessageSourceFactory = BeanUtil.getBean(ErrorMessageSourceFactory.class);
            return errorMessageSourceFactory.getMessage(errCode, args);
        }

        return errMessage;
    }

    public String getSystemMessage() {
        return super.getMessage();
    }
}
