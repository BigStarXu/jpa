package com.example.jpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * 主页控制器
 * 提供根路径的映射和API文档
 */
@Controller
@Slf4j
public class HomeController {
    /**
     * 主页 - 返回JSP视图
     */
    @GetMapping("/")
    public String home() {
        log.info("访问主页");
        return "index"; // 对应 /WEB-INF/jsp/index.jsp
    }

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    @ResponseBody
    public Map<String, String> health() {
        log.info("健康检查");
        return Map.of("status", "UP", "message", "应用运行正常");
    }

    /**
     * API文档接口
     */
    @GetMapping("/api-docs")
    @ResponseBody
    public Map<String, Object> apiDocs() {
        log.info("获取API文档");
        Map<String, Object> response = new HashMap<>();
        Map<String, String> projectInfo = new HashMap<>();
        projectInfo.put("名称", "JPA学习项目");
        projectInfo.put("版本", "1.0.0");
        projectInfo.put("描述", "Spring Boot + JPA + MySQL 学习项目");
        response.put("项目信息", projectInfo);
        Map<String, String> techStack = new HashMap<>();
        techStack.put("后端框架", "Spring Boot 3.2.0");
        techStack.put("持久层", "Spring Data JPA");
        techStack.put("数据库", "MySQL 8.0");
        techStack.put("Java版本", "Java 17");
        response.put("技术栈", techStack);
        Map<String, String> features = new HashMap<>();
        features.put("实体映射", "演示基本的实体类映射");
        features.put("关系映射", "演示一对一、一对多、多对多关系");
        features.put("继承映射", "演示单表继承策略");
        features.put("查询方法", "演示方法名查询和@Query注解");
        features.put("事务管理", "演示事务操作和异常处理");
        response.put("核心功能", features);
        return response;
    }
} 