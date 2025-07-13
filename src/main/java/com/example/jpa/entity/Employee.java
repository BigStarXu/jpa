package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 员工实体类 - 继承自Person
 * 
 * 演示的JPA概念：
 * 1. @Entity - 标记为JPA实体
 * 2. @DiscriminatorValue - 区分值配置
 * 3. @Column - 列映射配置
 * 4. @Enumerated - 枚举映射
 * 5. @Temporal - 时间类型映射
 */
@Entity
@DiscriminatorValue("EMPLOYEE")
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person {
    
    @Column(name = "employee_id", unique = true)
    private String employeeId;
    
    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;
    
    @Column(name = "hire_date")
    private LocalDate hireDate;
    
    @Column(name = "department")
    private String department;
    
    @Override
    public String getPersonType() {
        return "EMPLOYEE";
    }
    
    // 职位枚举
    public enum Position {
        JUNIOR_DEVELOPER("初级开发"),
        SENIOR_DEVELOPER("高级开发"),
        TEAM_LEAD("团队领导"),
        MANAGER("经理"),
        DIRECTOR("总监");
        
        private final String description;
        
        Position(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
} 