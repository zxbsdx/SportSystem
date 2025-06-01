package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.ui.Model; 
import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
        
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")  // 处理根路径请求
    public String home() {
        return "redirect:/index"; 
    }
      
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "hello world!";
    }

    
    
    // 显示登录页面
    @GetMapping("/login")
    public String showLoginForm() {
        return "redirect:/login.html";
    }
    
    // 处理登录请求
    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        
        User user = userRepository.findByUsername(username);
        
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // 登录成功
            session.setAttribute("currentUser", user);
            return "redirect:/index";
        } else {
            // 登录失败
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }
    
    // 欢迎页面
    @GetMapping("/welcome")
    public String showWelcomePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "welcome";
    }
    
    // 注销
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}


