package com.example.demo;

import java.sql.Date;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class User_infoDto {
   

    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "男|女", message = "性别必须是'男'或'女'")
    private String gender;
    
    @Past(message = "生日必须是过去日期")
    private Date birthday;
    
    @Positive(message = "身高必须为正数")
    @DecimalMin("50.0") @DecimalMax("250.0")
    private Float height;
    
    @Positive(message = "体重必须为正数")
    @DecimalMin("2.5") @DecimalMax("300.0")
    private Float weight;


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

}
