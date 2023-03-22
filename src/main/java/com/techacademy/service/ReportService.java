package com.techacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository repository) {
        this.reportRepository = repository;
    }

    /** 全件を検索して返す */
    public List<Report> getReportList() {
        // リポジトリのfindAllメソッドを呼び出す
        return reportRepository.findAll();
    }

    /** レコード件をかえす　*/
    public long getReportCount() {
        //リポジトリのcountメソッドを呼び出す
        return reportRepository.count();
    }

}
