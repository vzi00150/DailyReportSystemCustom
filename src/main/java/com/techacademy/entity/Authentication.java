package com.techacademy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authentication")
public class Authentication {

    /** 権限用の列挙型 */
    public static enum Role {
        一般, 管理者
    }
    /** 社員番号 */
    @Column(length = 20)
    @Id
    @NotEmpty
    private String code;

    /** パスワード */
    @Column(length = 255)
    @NotEmpty
    private String password;

    /** 権限 列挙型(文字列)*/
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    /** EmployeeテーブルのID */
    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;

}
