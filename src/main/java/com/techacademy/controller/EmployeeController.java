package com.techacademy.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String postRegister(Employee employee) {
        employee.setDeleteFlag(0);
        employee.setCreatedAt(new Date());
        employee.setUpdatedAt(new Date());
        //employee.getAuthentication().getEmployee().(employee.getId());
        //従業員登録
        service.saveEmployee(employee);
        //一覧処理にリダイレクト
        return "redirect:/employee/list";
    }
}
