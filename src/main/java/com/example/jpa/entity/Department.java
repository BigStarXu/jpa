package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 部门实体类
 * 
 * 演示的JPA概念：
 * 1. @Entity - 标记为JPA实体
 * 2. @Table - 指定数据库表名
 * 3. @Id - 主键标识
 * 4. @GeneratedValue - 主键生成策略
 * 5. @Column - 列映射配置
 * 6. @ManyToMany - 多对多关系映射
 * 7. @CreationTimestamp - 创建时间自动设置
 * 8. @UpdateTimestamp - 更新时间自动设置
 */
@Entity
@Table(name = "departments")
@Data
@ToString(exclude = {"users"}) // 避免循环引用
@EqualsAndHashCode(exclude = {"users"})
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // 多对多关系：部门可以有多个用户，用户可以有多个部门
    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
} 