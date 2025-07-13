package com.example.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * JPA学习项目主应用类
 * 
 * 这个项目演示了JPA的核心概念：
 * 1. 实体映射 (@Entity, @Table, @Column等)
 * 2. 关系映射 (@OneToMany, @ManyToOne, @ManyToMany等)
 * 3. 继承映射 (@Inheritance, @DiscriminatorColumn等)
 * 4. 查询方法 (方法名查询, @Query注解等)
 * 5. 事务管理 (@Transactional)
 */
@SpringBootApplication
public class JpaLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaLearningApplication.class, args);
        System.out.println("=== JPA学习项目启动成功 ===");
        System.out.println("访问 http://localhost:8080 查看API文档");
        System.out.println("数据库连接信息：");
        System.out.println("  MySQL数据库: jpa_learning");
        System.out.println("  用户名: root");
        System.out.println("  密码: (空密码)");
    }
} 