package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ToppageController {

    /** Top画面を表示 */
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }
}