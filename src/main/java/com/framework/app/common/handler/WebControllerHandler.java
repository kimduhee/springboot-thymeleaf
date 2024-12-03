package com.framework.app.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@Slf4j
@ControllerAdvice
public class WebControllerHandler {

    /**
     * Get 요청시 setter가 아닌 Field에 직접 접근 방식으로 처리
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }
}
