package com.example.jpa.controller;

import com.example.jpa.entity.Department;
import com.example.jpa.entity.User;
import com.example.jpa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 页面控制器
 * 处理JSP页面的请求
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {
    
    private final UserService userService;
    
    /**
     * 用户管理页面
     */
    @GetMapping("/users")
    public String usersPage(Model model) {
        log.info("访问用户管理页面");
        
        try {
            List<User> users = userService.findAllUsers();
            Object[] stats = userService.getUserStatistics();
            
            model.addAttribute("users", users);
            model.addAttribute("userCount", stats != null && stats.length > 0 ? stats[0] : 0);
            model.addAttribute("avgAge", stats != null && stats.length > 1 && stats[1] != null ? stats[1] : 0);
            model.addAttribute("maxAge", stats != null && stats.length > 2 && stats[2] != null ? stats[2] : 0);
            
            return "users";
        } catch (Exception e) {
            log.error("获取用户数据失败", e);
            model.addAttribute("error", "获取用户数据失败: " + e.getMessage());
            return "users";
        }
    }
    
    /**
     * 部门管理页面
     */
    @GetMapping("/departments")
    public String departmentsPage(Model model) {
        log.info("访问部门管理页面");
        
        try {
            // 这里可以添加部门服务来获取部门数据
            // 暂时使用模拟数据
            model.addAttribute("departments", List.of());
            return "departments";
        } catch (Exception e) {
            log.error("获取部门数据失败", e);
            model.addAttribute("error", "获取部门数据失败: " + e.getMessage());
            return "departments";
        }
    }
    
    /**
     * 订单管理页面
     */
    @GetMapping("/orders")
    public String ordersPage(Model model) {
        log.info("访问订单管理页面");
        
        try {
            // 这里可以添加订单服务来获取订单数据
            model.addAttribute("orders", List.of());
            return "orders";
        } catch (Exception e) {
            log.error("获取订单数据失败", e);
            model.addAttribute("error", "获取订单数据失败: " + e.getMessage());
            return "orders";
        }
    }
    
    /**
     * JPA演示页面
     */
    @GetMapping("/demo")
    public String demoPage(Model model) {
        log.info("访问JPA演示页面");
        return "demo";
    }
    
    /**
     * 添加用户（表单提交）
     */
    @PostMapping("/users/add")
    public String addUser(@RequestParam String username, 
                         @RequestParam String email, 
                         @RequestParam Integer age,
                         Model model) {
        log.info("添加用户: username={}, email={}, age={}", username, email, age);
        
        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setAge(age);
            
            userService.createUser(user);
            model.addAttribute("message", "用户添加成功！");
            
        } catch (Exception e) {
            log.error("添加用户失败", e);
            model.addAttribute("error", "添加用户失败: " + e.getMessage());
        }
        
        // 重新获取用户列表
        List<User> users = userService.findAllUsers();
        Object[] stats = userService.getUserStatistics();
        
        model.addAttribute("users", users);
        model.addAttribute("userCount", stats != null && stats.length > 0 ? stats[0] : 0);
        model.addAttribute("avgAge", stats != null && stats.length > 1 && stats[1] != null ? stats[1] : 0);
        model.addAttribute("maxAge", stats != null && stats.length > 2 && stats[2] != null ? stats[2] : 0);
        
        return "users";
    }
    
    /**
     * 添加部门（表单提交）
     */
    @PostMapping("/departments/add")
    public String addDepartment(@RequestParam String name, 
                               @RequestParam String description,
                               Model model) {
        log.info("添加部门: name={}, description={}", name, description);
        
        try {
            // 这里可以添加部门服务来创建部门
            model.addAttribute("message", "部门添加成功！");
        } catch (Exception e) {
            log.error("添加部门失败", e);
            model.addAttribute("error", "添加部门失败: " + e.getMessage());
        }
        
        return "departments";
    }
} 