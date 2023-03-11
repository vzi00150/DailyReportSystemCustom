package com.techacademy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authentication1")
public class Authentication {

    /** 権限用の列挙型 */
    public static enum Role {
        一般, 管理者
    }
    /** 社員番号 */
    @Column(length = 20)
    @Id
    private String code;

    /** パスワード */
    @Column(length = 255)
    private String password;

    /** 権限 列挙型(文字列)*/
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;

    /** EmployeeテーブルのID */
    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;

}
