package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    @GetMapping("/")  // 处理根路径请求
    public String home() {
        return "redirect:/index.html"; 
    }
      
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "hello world!";
    }
}

