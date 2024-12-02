package com.framework.app.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/view/comm")
@RequiredArgsConstructor
@Slf4j
public class CommController {

    /**
     * 500 에러 화면처리
     * @param model
     * @return
     */
    @GetMapping("/500")
    public String error500(Model model) {
        return "comm/500";
    }

    /**
     * 404 에러 화면처리
     * @param model
     * @return
     */
    @GetMapping("/404")
    public String error404(Model model) {
        return "comm/404";
    }
}
