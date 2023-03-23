package com.techacademy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.entity.Authentication;
import com.techacademy.service.AuthenticationService;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
public class ToppageController {
    private final AuthenticationService service;
    private final ReportService repservice;


    public ToppageController(AuthenticationService service, ReportService repservice) {
        this.service = service;
        this.repservice = repservice;

    }


    /** Top画面を表示 */
    @GetMapping("/")
//    public String getIndex(Model model, Principal principal) {
//        //入力された社員コードから名前を取得して、usernameにセットする
//        Authentication authentication = service.getAuthentication(principal.getName());
//        model.addAttribute("username", authentication.getEmployee().getName());

    public String getIndex(Model model, @AuthenticationPrincipal UserDetail user) {
        //UserDetailのフィールドにEmployeeがあるのでそれを使って名前を取得
        model.addAttribute("username", user.getEmployee().getName());

        Authentication authentication = service.getAuthentication(user.getUsername());
        model.addAttribute("role", authentication.getRole().ordinal());

        //ログインした社員の全件検索結果をModelに登録
        model.addAttribute("reportlist", repservice.getReportListByEmp(user.getEmployee()));
        model.addAttribute("reportcount", repservice.getReportCountByEmp(user.getEmployee()));

        return "index";
    }
}