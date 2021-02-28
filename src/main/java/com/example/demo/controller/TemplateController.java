package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @RequestMapping("login")
    public String getLoginView(){
        return "login";
    }

    @RequestMapping("courses")
    public String getCourseView(){
        return "courses";
    }
}
