package com.example.jpa.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 测试配置类
 * 排除可能影响测试的配置
 */
@TestConfiguration
@ComponentScan(
    basePackages = "com.example.jpa",
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = {com.example.jpa.config.JspConfig.class}
        )
    }
)
public class TestConfig {
    // 测试专用配置
} 