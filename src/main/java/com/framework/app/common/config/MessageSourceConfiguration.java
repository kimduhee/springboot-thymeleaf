package com.framework.app.common.config;

import com.framework.app.common.factory.GlobalEnvironmentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Configuration
@ComponentScan("com.framework.app")
public class MessageSourceConfiguration {

    @Autowired
    GlobalEnvironmentFactory globalEnvironmentFactory;

    @Bean(name="errorMessageSource")
    public MessageSource errorMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/message/error/"+globalEnvironmentFactory.getActiveProfile()+".error", "classpath:/message/error/error");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }

    @Bean(name="errorMessageSourceAccessor")
    public MessageSourceAccessor errorMessageSourceAccessor() {
        return new MessageSourceAccessor(errorMessageSource(), Locale.KOREA);
    }
}
