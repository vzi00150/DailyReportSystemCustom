package com.techacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.repository.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository repository) {
        this.employeeRepository = repository;
    }

    /** 全件を検索してかえす　*/
    public List<Employee> getEmployeeList() {
        //リポジトリのfindAllメソッドを呼び出す
        return employeeRepository.findAll();
    }
    /** 従業員の登録を行う　*/
    @Transactional
    public Employee saveEmployee(Employee employee) {
        Authentication authentication = employee.getAuthentication(); // authenticationの取り出し
        authentication.setEmployee(employee); // ここでempoloyeeのインスタンスを設定

        return employeeRepository.save(employee);
    }
}
