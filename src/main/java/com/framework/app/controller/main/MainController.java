package com.framework.app.controller.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/view/comm")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    @GetMapping(value={"/", "/main"})
    public String main() {
        return "comm/main";
    }
}
