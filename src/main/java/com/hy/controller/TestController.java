package com.hy.controller;

import com.hy.model.FromList;
import com.hy.model.WorkFrom;
import com.hy.model.domain.CmTimesheet;
import com.hy.model.easyui.EasyUIDatagrid;
import com.hy.service.TestEasyUIService;
import com.hy.service.TestEasyUIServiceImpl;
import com.hy.service.jpa.CmProjectRepostory;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    public final static int USERID = 1;
    public final static String USERNAME = "孤星";
    public final static String BMNAME = "产品二部本部";


    @Autowired
    TestEasyUIService testEasyUIServiceImpl;
    @Autowired
    CmProjectRepostory cmProjectRepostory;

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
    public Integer saveWorktime(@RequestBody List<List<CmTimesheet>> list) {
        int count = 0;
        for (List<CmTimesheet> cms : list) {
            if (testEasyUIServiceImpl.saveDays(cms)) count++;
        }
        return count;
    }

    @RequestMapping("/del")
    @ResponseBody
    public Integer delWorkTime(@RequestBody List<Integer> list) {
        int count = 0;

        return count;
    }
}
