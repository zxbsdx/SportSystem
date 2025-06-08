package com.example.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "sports", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "id")
       })
public class Sports {

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // 存储枚举的字符串值
    @Column(nullable = false, length = 20)
    private SportType type;

    @PositiveOrZero(message = "天数不能为负数")
    @Column(nullable = false)
    private Long day;

    @Temporal(TemporalType.TIME) // 只存储时间部分
    @Column(nullable = false)
    private Date time;

    @Positive(message = "持续时间必须为正数")
    @Max(value = 1440, message = "持续时间不能超过1440分钟")
    @Column(nullable = false)
    private Long duration; // 单位：分钟

    @PositiveOrZero(message = "消耗能量不能为负数")
    @Column(nullable = false)
    private Float energy; // 单位：千卡

    @PositiveOrZero(message = "步数不能为负数")
    @Column(nullable = false)
    private Long step;

    @Pattern(regexp = "^\\d+\\.?\\d*$", message = "距离必须为数字")
    @Column(name = "distance", nullable = false) // 修正字段名
    private String kilo; // 单位：公里

    // 计算每分钟消耗能量（业务方法）
    public Float getEnergyPerMinute() {
        return duration > 0 ? energy / duration : 0f;
    }

    // 检查是否是高强度运动（业务方法）
    public boolean isHighIntensity() {
        return type == SportType.RUNNING || 
               type == SportType.SWIMMING || 
               type == SportType.GYM;
    }

    // Getter和Setter
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Float getEnergy() {
        return energy;
    }

    public void setEnergy(Float energy) {
        this.energy = energy;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public String getKilo() {
        return kilo;
    }

    public void setKilo(String kilo) {
        this.kilo = kilo;
    }

    // 实用方法：获取运动类型显示名称
    public String getTypeDisplayName() {
        return type.getDisplayName();
    }
}