package com.hy.controller;

import com.hy.service.TestEasyUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {

    @Autowired
    TestEasyUIService testEasyUIService;

    @GetMapping
    public String index() {
        return "index.html";
    }

}
