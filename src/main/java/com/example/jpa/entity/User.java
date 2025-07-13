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
 * 用户实体类
 * 
 * 演示的JPA概念：
 * 1. @Entity - 标记为JPA实体
 * 2. @Table - 指定数据库表名
 * 3. @Id - 主键标识
 * 4. @GeneratedValue - 主键生成策略
 * 5. @Column - 列映射配置
 * 6. @ManyToMany - 多对多关系映射
 * 7. @JoinTable - 关联表配置
 * 8. @CreationTimestamp - 创建时间自动设置
 * 9. @UpdateTimestamp - 更新时间自动设置
 */
@Entity
@Table(name = "users")
@Data
@ToString(exclude = {"departments"}) // 避免循环引用
@EqualsAndHashCode(exclude = {"departments"})
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // 多对多关系：用户可以有多个部门，部门可以有多个用户
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_departments",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments = new HashSet<>();
    
    // 一对多关系：用户可以有多个订单
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();
    
    // 便利方法：添加部门
    public void addDepartment(Department department) {
        this.departments.add(department);
        department.getUsers().add(this);
    }
    
    // 便利方法：移除部门
    public void removeDepartment(Department department) {
        this.departments.remove(department);
        department.getUsers().remove(this);
    }
} 