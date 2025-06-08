package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;


@RestController // 返回json数据
public class API_Controller {
    @Autowired // 添加自动注入注解
    private UserInfoRepository userInfoRepository;

    HttpSession session;
    
    @PostMapping("/api/userInfo") 
    public ResponseEntity<?> usrinfo() {

        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
        return ResponseEntity.status(401).body("未登录"); // 明确返回 401
    }
        return ResponseEntity.ok(user); 
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

}
