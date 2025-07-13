package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 客户实体类 - 继承自Person
 * 
 * 演示的JPA概念：
 * 1. @Entity - 标记为JPA实体
 * 2. @DiscriminatorValue - 区分值配置
 * 3. @Column - 列映射配置
 * 4. @Enumerated - 枚举映射
 */
@Entity
@DiscriminatorValue("CUSTOMER")
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends Person {
    
    @Column(name = "customer_id", unique = true)
    private String customerId;
    
    @Column(name = "total_spent", precision = 10, scale = 2)
    private BigDecimal totalSpent = BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type")
    private CustomerType customerType = CustomerType.REGULAR;
    
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    
    @Column(name = "address")
    private String address;
    
    @Override
    public String getPersonType() {
        return "CUSTOMER";
    }
    
    // 客户类型枚举
    public enum CustomerType {
        REGULAR("普通客户"),
        VIP("VIP客户"),
        PREMIUM("高级客户");
        
        private final String description;
        
        CustomerType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
} 