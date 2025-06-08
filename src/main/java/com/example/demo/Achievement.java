package com.example.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "achievement", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "id")
       })
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean fresher; // 是否新手成就

    @Column(name = "step_in", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean stepIn;// 入门

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean older; 

    @Column(name = "sign_in", nullable = false)
    @Temporal(TemporalType.DATE) // 只存储日期部分
    private Date signInDate; // 签到日期（原sign_in字段）

    @PositiveOrZero(message = "连续签到天数不能为负数")
    @Column(name = "consecutive_days")
    private Integer consecutiveDays; // 新增：连续签到天数

    public enum AchievementType {
        FRESHER("新手成就"),
        STEP_MASTER("入门"),
        LOYAL_USER("忠实用户"),
        MONTHLY_SIGNER("月度签到");

        private final String description;

        AchievementType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }


    public boolean isBasicCompleted() {
        return Boolean.TRUE.equals(fresher) && 
               Boolean.TRUE.equals(stepIn) && 
               Boolean.TRUE.equals(older);
    }

    public boolean isConsecutiveSignIn(int thresholdDays) {
        return consecutiveDays != null && consecutiveDays >= thresholdDays;
    }

    public String getAchievementStatus() {
        StringBuilder sb = new StringBuilder();
        if (Boolean.TRUE.equals(fresher)) sb.append("已完成新手成就|");
        if (Boolean.TRUE.equals(stepIn)) sb.append("已完成步数目标|");
        if (Boolean.TRUE.equals(older)) sb.append("已获得老用户徽章");
        return sb.length() > 0 ? sb.toString() : "暂无成就";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFresher() {
        return fresher;
    }

    public void setFresher(Boolean fresher) {
        this.fresher = fresher;
    }

    public Boolean getStepIn() {
        return stepIn;
    }

    public void setStepIn(Boolean stepIn) {
        this.stepIn = stepIn;
    }

    public Boolean getOlder() {
        return older;
    }

    public void setOlder(Boolean older) {
        this.older = older;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

    public Integer getConsecutiveDays() {
        return consecutiveDays;
    }

    public void setConsecutiveDays(Integer consecutiveDays) {
        this.consecutiveDays = consecutiveDays;
    }

    public void updateConsecutiveDays(boolean signedToday) {
        if (signedToday) {
            consecutiveDays = (consecutiveDays == null) ? 1 : consecutiveDays + 1;
        } else {
            consecutiveDays = 0;
        }
    }
}