package com.hy.controller;

import com.hy.model.WorkFrom;
import com.hy.model.domain.Timesheet;
import com.hy.service.TestEasyUIService;
import com.hy.service.jpa.ProjectRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
public class WorkController {

    public final static int USERID = 1;
    public final static String USERNAME = "孤星";
    public final static String BMNAME = "产品二部本部";


    @Autowired
    TestEasyUIService testEasyUIServiceImpl;
    @Autowired
    ProjectRepostory projectRepostory;

    @GetMapping("/")
    public String index() {
        System.out.println("访问首页...");
        return "redirect:/work.html";
    }

    @GetMapping("/add")
    @ResponseBody
    public Map addWorkSheet(WorkFrom frm) throws ParseException {
        return testEasyUIServiceImpl.addWorkSheet(USERID, frm);
    }

    @RequestMapping("/table")
    @ResponseBody
    public List getWorkSheetEdit(@RequestParam("year") Integer year, @RequestParam("day") Integer e) {
        return testEasyUIServiceImpl.loadAllWorkSheet(USERID, year, e);
    }

    @RequestMapping("/findAllprojects")
    @ResponseBody
    public List findAllprojects() {
        List<Map<String, Object>> list = testEasyUIServiceImpl.loadAllprojects("");
        return list;
    }

    @RequestMapping("/findAllcatalogs")
    @ResponseBody
    public List findAllcatalogs() {
        List<Map<String, Object>> list = testEasyUIServiceImpl.loadAllCatalogs(0, "");
        return list;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Integer saveWorktime(@RequestBody List<List<Timesheet>> list) {
        int count = 0;
        for (List<Timesheet> cms : list) {
            if (testEasyUIServiceImpl.saveDays(cms)) count++;
        }
        return count;
    }

    @RequestMapping("/del")
    @ResponseBody
    public Integer delWorkTime(@RequestBody List<List<Integer>> list) {
        int count = 0;
        for (List<Integer> ids : list) {
            if (testEasyUIServiceImpl.delDays(ids)) count++;
        }
        return count;
    }

    @RequestMapping("/submit")
    @ResponseBody
    public Integer submitDays(@RequestBody List<List<Timesheet>> list) {
        int count = 0;
        for (List<Timesheet> cms : list) {
            if (testEasyUIServiceImpl.submitDays(cms)) count++;
        }
        return count;
    }
}
