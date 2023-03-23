package com.techacademy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "report")
public class Report {

    /** 主キー。自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    /** 日報の日付 */
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    /** タイトル */
    @Column(nullable = false, length = 255)
    @NotBlank
    private String title;

    /** content */
    @Column(nullable = false)
    @NotBlank
    @Type(type="text")
    private String content;

    /** EmployeeテーブルのID */
    @ManyToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;

    /** 登録日時 */
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    /** 更新日時 */
    @Column(nullable = false)
    private LocalDateTime updatedAt;


}
