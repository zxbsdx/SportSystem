package com.example.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
@Table(name = "user_info", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "id"),
           
       })
public class User_info {
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user; // 关联user


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "男|女", message = "性别必须是'男'或'女'")
    @Column(nullable = false, length = 1)
    private String gender;

    @Past(message = "生日必须是过去日期")
    @Column(nullable = false)
    private Date birthday;  

    @Positive(message = "身高必须为正数")
    @DecimalMin(value = "50.0", message = "身高不能低于50cm")
    @DecimalMax(value = "250.0", message = "身高不能超过250cm")
    @Column(nullable = false)
    private Float height;

    @Positive(message = "体重必须为正数")
    @DecimalMin(value = "2.5", message = "体重不能低于2.5kg")
    @DecimalMax(value = "300.0", message = "体重不能超过300kg")
    @Column(nullable = false)
    private Float weight;

    @Column(name = "sign_up", nullable = false)
    private String signUp;  

    // Getter和Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getSignUp() {
        return signUp;
    }

    public void setSignUp(String signUp) {
        this.signUp = signUp;
    }

     public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}