package com.techacademy.controller;

import java.time.LocalDateTime;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /** 一覧画面 */
    @GetMapping("/list")
    public String getList(Model model) {
        //全件検索結果をModelに登録
        model.addAttribute("employeelist", service.getEmployeeList());
        model.addAttribute("employeecount", service.getEmployeeCount());
        // employee/list.htmlに画面遷移
        return "employee/list";
    }

    /** 従業員登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee) {
        // 従業員登録画面に遷移
        return "employee/register";
    }

    /** 従業員登録処理　*/
    @PostMapping("/register")
    public String postRegister(@Validated Employee employee, BindingResult res, Model model) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(employee);
        }

        employee.setDeleteFlag(0);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        //employee.getAuthentication().getEmployee()

        try {
            // Employee保存
            service.saveEmployee(employee);
        } catch (DuplicateKeyException e) {
            res.rejectValue("authentication.code", "code.duplicated");
            return getRegister(employee);
        } catch (Exception e) {
            res.rejectValue("authentication.code", "code.dberror");
            return getRegister(employee);
        }
        //一覧処理にリダイレクト
        return "redirect:/employee/list";
    }



    /** Employee詳細画面を表示 */
    @GetMapping("/detail/{id}/")
    public String getEmployee(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("employee", service.getEmployee(id));
        // Employee詳細画面に遷移
        return "employee/detail";
    }


    /** employee更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getEmpForUpd(@PathVariable("id") Integer id, Model model, Employee employee) {
        if(id == null) {
            // Modelに登録
            model.addAttribute("employee", employee);
        } else {
            // Modelに登録
            model.addAttribute("employee", service.getEmployee(id));
        }

        // Employee詳細画面に遷移
        return "employee/update";
    }

    /** Employee更新処理 */
    @PostMapping("/update")
    public String postEmpForUpd(@Validated Employee employee, BindingResult res, @RequestParam("pass") String pass, Model model) {
        if(res.hasErrors()) {
            //エラーあり
            Integer id = null;
            return getEmpForUpd(id, model, employee);
        }
        //employee.setDeleteFlag(0);
        employee.setUpdatedAt(LocalDateTime.now());
        if (!pass.equals("")) {
            employee.getAuthentication().setPassword(pass);
        }

        try {
            // Employee保存
            service.saveEmployee(employee);
        } catch (DuplicateKeyException e) {
            res.rejectValue("code", "code.duplicated");
            Integer id = null;
            return getEmpForUpd(id, model, employee);
        } catch (Exception e) {
            res.rejectValue("code", "code.dberror");
            Integer id = null;
            return getEmpForUpd(id, model, employee);
        }

        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

    /** Employee論理削除処理 */
    @GetMapping("/delete/{id}/")
    public String getEmpForDel(@PathVariable("id") Integer id) {
        Employee employee = service.getEmployee(id);
        //論理削除フラグに１を設定
        employee.setDeleteFlag(1);
        //更新日付設定
        employee.setUpdatedAt(LocalDateTime.now());
        // Employee保存
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

}
