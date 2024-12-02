package com.framework.app.common.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ErrorMessageSourceFactory {

    @Autowired @Qualifier("errorMessageSourceAccessor")
    MessageSourceAccessor messageSourceAccessor;

    public MessageSourceAccessor getMessageSourceAccessor() {
        return messageSourceAccessor;
    }

    public String getMessage(String key) {
        return getMessage(key, null);
    }

    public String getMessage(String key, Object[] args) {
        try {
            return messageSourceAccessor.getMessage(key, args);
        } catch(Exception e) {
            log.error("ErrorMessageSourceFactory.getMessage Exception!");
            return key;
        }
    }
}
