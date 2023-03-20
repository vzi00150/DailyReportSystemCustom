package com.techacademy.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.entity.Authentication;
import com.techacademy.service.AuthenticationService;

@Controller
public class ToppageController {
    private final AuthenticationService service;

    public ToppageController(AuthenticationService service) {
        this.service = service;
    }


    /** Top画面を表示 */
    @GetMapping("/")
    public String getIndex(Model model, Principal principal) {
        //入力された社員コードから名前を取得して、usernameにセットする
        Authentication authentication = service.getAuthentication(principal.getName());
        model.addAttribute("username", authentication.getEmployee().getName());
        
        model.addAttribute("role", authentication.getRole().ordinal());
        return "index";
    }
}