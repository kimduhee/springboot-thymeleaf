package com.framework.app.common.config;

import com.framework.app.common.interceptor.GlobalInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    //업로드 이미지 저장경로
    @Value("${spring.servlet.multipart.location}")
    private String uploadImagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///"+uploadImagePath)    // 웹에서 이미지 호출시 'file:///' 설정됨
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalInterceptor())
                .excludePathPatterns("/images/**", "/css/**", "/js/**");
    }
}
