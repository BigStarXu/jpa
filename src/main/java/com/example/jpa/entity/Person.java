package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 人员基类 - 演示JPA继承映射
 * 
 * 演示的JPA概念：
 * 1. @Entity - 标记为JPA实体
 * 2. @Table - 指定数据库表名
 * 3. @Inheritance - 继承映射策略
 * 4. @DiscriminatorColumn - 区分列配置
 * 5. @DiscriminatorType - 区分列类型
 * 6. @Id - 主键标识
 * 7. @GeneratedValue - 主键生成策略
 * 8. @Column - 列映射配置
 * 9. @CreationTimestamp - 创建时间自动设置
 * 10. @UpdateTimestamp - 更新时间自动设置
 */
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // 抽象方法，子类必须实现
    public abstract String getPersonType();
} 