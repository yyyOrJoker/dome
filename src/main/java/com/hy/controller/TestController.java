package com.hy.controller;

import com.hy.model.domain.CmTimesheet;
import com.hy.model.easyui.EasyUIDatagrid;
import com.hy.service.TestEasyUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller
public class TestController {

    public final static int USERID = 1;
    public final static String USERNAME = "孤星";
    public final static String BMNAME = "产品二部本部";


    @Autowired
    TestEasyUIService testEasyUIService;

    @GetMapping("/")
    public String index() {
        System.out.println("访问首页...");
        return "redirect:/work.html";
    }

    @GetMapping("/info")
    @ResponseBody
    public String info() {
        return "试试能否正常访问!";
    }

    @GetMapping("/add")
    @ResponseBody
    public List<CmTimesheet> addWorkSheet(Model model) throws ParseException {
        return testEasyUIService.addWorkSheet(1, 1, 6, "shanghai", 1, 1, "2016", "52");
    }

    @RequestMapping("/table")
    @ResponseBody
    public EasyUIDatagrid getWorkSheetEdit(ModelMap map) {
        System.out.println(map.size());
        System.out.println(map);
        return testEasyUIService.loadEasyuiDataGrid();
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("dao");
        return "index";
    }
}
