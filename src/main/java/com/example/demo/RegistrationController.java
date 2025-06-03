package com.example.demo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.ui.Model;
import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 显示注册页面
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    // 处理注册请求
    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {

        // 1. 验证输入
        if (result.hasErrors()) {
            return "register";
        }

        // 2. 检查用户名是否已存在
        if (userRepository.existsByUsername(userDto.getUsername())) {
            model.addAttribute("usernameError", "用户名已被使用");
            return "register";
        }


        // 4. 创建新用户
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // 密码加密

        
        // 5. 保存到数据库
        userRepository.save(user);

        // 6. 注册成功后重定向到登录页
        return "redirect:/login?registered";
    }
}