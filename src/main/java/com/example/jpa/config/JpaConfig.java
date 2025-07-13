package com.example.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

/**
 * JPA配置类
 * 
 * 演示的JPA配置概念：
 * 1. @EnableJpaAuditing - 启用JPA审计功能
 * 2. @EnableJpaRepositories - 启用JPA仓库
 * 3. @EnableTransactionManagement - 启用事务管理
 * 4. AuditorAware - 审计信息提供者
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = "com.example.jpa.repository")
@EnableTransactionManagement
public class JpaConfig {
    
    /**
     * 审计信息提供者
     * 用于自动设置 @CreatedBy 和 @LastModifiedBy 字段
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("system"); // 这里可以根据实际需求返回当前用户
    }
} 