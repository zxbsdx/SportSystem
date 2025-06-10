package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController // 返回json数据
public class API_Controller {
    @Autowired // 添加自动注入注解
    private UserInfoRepository userInfoRepository;

    HttpSession session;
    
    @PostMapping("/api/user") 
    public String usrinfo(HttpServletRequest request) {

        HttpSession session = request.getSession(); // 等价于 request.getSession(true)，会创建新会话
        
        // 检查会话是否有效
        if (session != null) {
            // 获取会话属性
            Object user = session.getAttribute("user");
            if (user != null) {
                return "用户信息: " + user.toString();
            } else {
                return "用户未登录";
            }
        } else {
            return "会话无效";
        }
    }
    


    @PostMapping("/api/set_userInfo") 
    public ResponseEntity<?> set_info(
        @RequestBody User_infoDto userInfoRequest,  // 接收前端传来的用户信息
        HttpSession session) {
    
    // 1. 检查用户是否登录
    User user = (User) session.getAttribute("currentUser");
    if (user == null) {
        return ResponseEntity.status(401).body("未登录");
    }
    
  
    User_info userInfo = userInfoRepository.findByUserId(user.getId())
            .orElse(new User_info());
    

    userInfo.setGender(userInfoRequest.getGender());
    userInfo.setBirthday(userInfoRequest.getBirthday());
    userInfo.setHeight(userInfoRequest.getHeight());
    userInfo.setWeight(userInfoRequest.getWeight());
    
    
    userInfoRepository.save(userInfo);
    
    return ResponseEntity.ok("用户信息更新成功");
}
    // 获取用户信息
    @PostMapping("/api/user_info") 
    public User_info user_info() {
        User_info info = null;
        return info; 
    }
    // 获取运动信息
    @PostMapping("/api/get_sport") 
    public Sports get_sport() {
        Sports info = null;
        return info; 
    }

    // 获取成就信息
    @PostMapping("/api/get_achievement") 
    public Achievement get_achievement() {
        Achievement info = null;
        return info; 
    }   

}
