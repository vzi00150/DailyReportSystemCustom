package com.techacademy.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Report;
import com.techacademy.service.AuthenticationService;
import com.techacademy.service.ReportService;

@Controller
@RequestMapping("report")
public class ReportController {
    private final ReportService service;
    private final AuthenticationService authservice;

    public ReportController(ReportService service, AuthenticationService authservice) {
        this.service = service;
        this.authservice = authservice;
    }

    /** 日報一覧画面 */
    @GetMapping("/list")
    public String getList(Model model, Principal principal) {
      //入力された社員コードから名前を取得して、usernameにセットする
        Authentication authentication = authservice.getAuthentication(principal.getName());
        model.addAttribute("username", authentication.getEmployee().getName());
        model.addAttribute("role", authentication.getRole().ordinal());

        //全件検索結果をModelに登録
        model.addAttribute("reportlist", service.getReportList());
        model.addAttribute("reportcount", service.getReportCount());
        // report/list.htmlに画面遷移
        return "report/list";
    }


    /** 日報登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(Report report, Model model, Principal principal) {
        //入力された社員コードから名前を取得して、usernameにセットする
        Authentication authentication = authservice.getAuthentication(principal.getName());
        model.addAttribute("username", authentication.getEmployee().getName());
        model.addAttribute("role", authentication.getRole().ordinal());

        //日報登録用オブジェクト
        model.addAttribute("report", report);

        // 従業員登録画面に遷移
        return "report/register";
    }

    /** 日報登録処理　*/
    @PostMapping("/register")
    public String postRegister(@Validated Report report, BindingResult res, Model model, Principal principal) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(report,model,principal);
        }
        //入力された社員コードからEmployeeを取得
        Authentication authentication = authservice.getAuthentication(principal.getName());
        report.setEmployee(authentication.getEmployee());

        //日報の日付がnullだったら本日日付をセットする
        if (report.getReportDate() == null ) {
            report.setReportDate(LocalDate.now());
        }
        report.setCreatedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());

        // 日報の保存
        service.saveReport(report);

        //一覧処理にリダイレクト
        return "redirect:/report/list";
    }

//
//
//    /** Employee詳細画面を表示 */
//    @GetMapping("/detail/{id}/")
//    public String getEmployee(@PathVariable("id") Integer id, Model model, Principal principal) {
//        //入力された社員コードから名前を取得して、usernameにセットする
//        Authentication authentication = authservice.getAuthentication(principal.getName());
//        model.addAttribute("username", authentication.getEmployee().getName());
//        model.addAttribute("role", authentication.getRole().ordinal());
//
//        // Modelに登録
//        model.addAttribute("employee", service.getEmployee(id));
//        // Employee詳細画面に遷移
//        return "employee/detail";
//    }
//
//
//    /** employee更新画面を表示 */
//    @GetMapping("/update/{id}/")
//    public String getEmpForUpd(@PathVariable("id") Integer id, Model model, Employee employee, Principal principal) {
//        //入力された社員コードから名前を取得して、usernameにセットする
//        Authentication authentication = authservice.getAuthentication(principal.getName());
//        model.addAttribute("username", authentication.getEmployee().getName());
//        model.addAttribute("role", authentication.getRole().ordinal());
//
//        if(id == null) {
//            // Modelに登録
//            model.addAttribute("employee", employee);
//        } else {
//            // Modelに登録
//            model.addAttribute("employee", service.getEmployee(id));
//        }
//
//        // Employee詳細画面に遷移
//        return "employee/update";
//    }
//
//    /** Employee更新処理 */
//    @PostMapping("/update")
//    public String postEmpForUpd(@Validated Employee employee, BindingResult res, @RequestParam("pass") String pass, Model model, Principal principal) {
//        if(res.hasErrors()) {
//            //エラーあり
//            Integer id = null;
//            return getEmpForUpd(id, model, employee, principal);
//        }
//        //employee.setDeleteFlag(0);
//        employee.setUpdatedAt(LocalDateTime.now());
//        if (!pass.equals("")) {
//            employee.getAuthentication().setPassword(passwordEncoder.encode(pass));
//        }
//
//        // Employee保存
//        service.saveEmployee(employee);
//
//        // 一覧画面にリダイレクト
//        return "redirect:/employee/list";
//    }
//
//    /** Employee論理削除処理 */
//    @GetMapping("/delete/{id}/")
//    public String getEmpForDel(@PathVariable("id") Integer id) {
//        Employee employee = service.getEmployee(id);
//        //論理削除フラグに１を設定
//        employee.setDeleteFlag(1);
//        //更新日付設定
//        employee.setUpdatedAt(LocalDateTime.now());
//        // Employee保存
//        service.saveEmployee(employee);
//        // 一覧画面にリダイレクト
//        return "redirect:/employee/list";
//    }

}
