package com.hy.controller;

import com.hy.model.domain.Timesheet;
import com.hy.service.jpa.AuditInfoRepostory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yyy1867 on 2017/1/5.
 */
@RequestMapping("/audit")
@Controller
public class AuditController {

    public final static int AUDITID = 1001;
    public final static String AUDITNAME = "审核人";

    AuditInfoRepostory auditInfoRepostory;
    Timesheet timesheet;

    @GetMapping
    public String index() {
        System.out.println("访问首页...");
        return "redirect:/audit.html";
    }
}
