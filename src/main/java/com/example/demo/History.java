package com.example.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "history", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "id")
       })
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     // 运动类型枚举
    public enum SportType {
        WALKING("步行"),
        RUNNING("跑步"),
        SWIMMING("游泳"),
        CYCLING("骑行"),
        YOGA("瑜伽"),
        GYM("健身房"),
        OTHER("其他");

        private final String displayName;

        SportType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }


    @Enumerated(EnumType.STRING) // 存储枚举的字符串值
    @Column(nullable = false, length = 20)
    private SportType type;

    @PositiveOrZero(message = "天数不能为负数")
    @Column(nullable = false)
    private Long day;

    @Temporal(TemporalType.TIME) // 只存储时间部分
    @Column(nullable = false)
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportType getType() {
        return type;
    }

    public void setType(SportType type) {
        this.type = type;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}