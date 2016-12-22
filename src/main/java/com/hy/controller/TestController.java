package com.hy.controller;

import com.hy.domain.Girl;
import com.hy.service.GrilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/girl")
public class TestController {

    @Autowired
    private GrilRepository grilRepository;

    @GetMapping(value = {"/test", "txt"})
    public String test() {
        return "Hello Word!!!";
    }

    @GetMapping(value = "/{id}t02")
    public String test2(@PathVariable(required = true, name = "id") Integer id) {
        return "id:" + id;
    }

    @GetMapping(value = "/save/{age}-{name}")
    public Girl save(@PathVariable("age") Integer age, @PathVariable("name") String name) {
        Girl g = new Girl();
        g.setAge(age);
        g.setName(name);
        return grilRepository.save(g);
    }

    @GetMapping("/list")
    public List<Girl> list() {
        return grilRepository.findAll();
    }

}
