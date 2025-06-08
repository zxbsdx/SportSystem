package com.example.demo;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
        
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/index")  
    public String home() {
        return "index"; 
    }

    // 返回登录页面, 不知什么原因找不到文件(已解决)
    @GetMapping("/login-page")  
    public String login() {
        return "login"; 
    }

    @GetMapping("/favicon.ico")  
    public String favicon() {
        return "/favicon.ico"; 
    }
    
    // 处理登录请求
    @PostMapping("/login")
    public String processLogin(        
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        
        User user = userRepository.findByUsername(username);
        // System.out.println(passwordEncoder.matches(password, user.getPassword()));
       
        System.out.println(user.getPassword());
        System.out.println(password);
        //if (user != null && passwordEncoder.matches(password, user.getPassword())) {

        if (user!=null && password.equals(user.getPassword())){
            // 登录成功

            // 注意手动设置JSESSIONID Spring Security 不认
            // session.setAttribute("currentUser", user);

            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(), 
                null, 
                authorities
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
          
        // 存储用户对象到 Session（非必须）
            session.setAttribute("currentUser", user);

            return "index";
        } else {
            // 登录失败
            // model.addAttribute("error", user);
  
            return "login";
        }
    }
    

    
    // 注销
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

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
        //user.setPassword(userDto.getPassword()); // 加个der
        
        // 5. 保存到数据库
        userRepository.save(user);

        // 6. 注册成功后重定向到登录页
        return "login";
    }
}


