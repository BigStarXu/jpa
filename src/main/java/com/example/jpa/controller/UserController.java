package com.example.jpa.controller;

import com.example.jpa.entity.Department;
import com.example.jpa.entity.User;
import com.example.jpa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 * 
 * 演示的Spring Boot概念：
 * 1. @RestController - REST控制器
 * 2. @RequestMapping - 请求映射
 * 3. @GetMapping - GET请求映射
 * 4. @PostMapping - POST请求映射
 * 5. @PutMapping - PUT请求映射
 * 6. @DeleteMapping - DELETE请求映射
 * 7. @PathVariable - 路径变量
 * 8. @RequestParam - 请求参数
 * 9. @RequestBody - 请求体
 * 10. ResponseEntity - 响应实体
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    
    private final UserService userService;
    
    // ========== 查询接口 ==========
    
    /**
     * 获取所有用户
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("获取所有用户");
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
    
    /**
     * 分页获取用户
     */
    @GetMapping("/page")
    public ResponseEntity<Page<User>> getUsersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        log.info("分页获取用户: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy, sortDir);
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> users = userService.findUsersWithPagination(pageable);
        
        return ResponseEntity.ok(users);
    }
    
    /**
     * 根据ID获取用户
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("根据ID获取用户: {}", id);
        
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 根据用户名获取用户
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        log.info("根据用户名获取用户: {}", username);
        
        return userService.findUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 根据邮箱获取用户
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        log.info("根据邮箱获取用户: {}", email);
        
        return userService.findUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 根据年龄获取用户
     */
    @GetMapping("/age/{age}")
    public ResponseEntity<List<User>> getUsersByAge(@PathVariable Integer age) {
        log.info("根据年龄获取用户: {}", age);
        
        List<User> users = userService.findUsersByAge(age);
        return ResponseEntity.ok(users);
    }
    
    /**
     * 根据年龄范围获取用户
     */
    @GetMapping("/age/range")
    public ResponseEntity<List<User>> getUsersByAgeRange(
            @RequestParam Integer minAge,
            @RequestParam Integer maxAge) {
        
        log.info("根据年龄范围获取用户: {} - {}", minAge, maxAge);
        
        List<User> users = userService.findUsersByAgeRange(minAge, maxAge);
        return ResponseEntity.ok(users);
    }
    
    /**
     * 根据用户名关键词搜索用户
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByKeyword(@RequestParam String keyword) {
        log.info("根据关键词搜索用户: {}", keyword);
        
        List<User> users = userService.findUsersByUsernameKeyword(keyword);
        return ResponseEntity.ok(users);
    }
    
    /**
     * 获取用户统计信息
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getUserStatistics() {
        log.info("获取用户统计信息");
        
        Object[] stats = userService.getUserStatistics();
        Map<String, Object> result = Map.of(
                "totalCount", stats[0],
                "averageAge", stats[1],
                "maxAge", stats[2],
                "minAge", stats[3]
        );
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 获取平均年龄
     */
    @GetMapping("/average-age")
    public ResponseEntity<Map<String, Double>> getAverageAge() {
        log.info("获取平均年龄");
        
        Double averageAge = userService.getAverageAge();
        return ResponseEntity.ok(Map.of("averageAge", averageAge));
    }
    
    // ========== 创建接口 ==========
    
    /**
     * 创建新用户
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("创建新用户: {}", user.getUsername());
        
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            log.error("创建用户失败: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 批量创建用户
     */
    @PostMapping("/batch")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<User> users) {
        log.info("批量创建用户，数量: {}", users.size());
        
        try {
            List<User> createdUsers = userService.createUsers(users);
            return ResponseEntity.ok(createdUsers);
        } catch (RuntimeException e) {
            log.error("批量创建用户失败: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    // ========== 更新接口 ==========
    
    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        log.info("更新用户信息: {}", id);
        
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            log.error("更新用户失败: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 更新用户年龄
     */
    @PutMapping("/{id}/age")
    public ResponseEntity<User> updateUserAge(@PathVariable Long id, @RequestParam Integer age) {
        log.info("更新用户年龄: {} -> {}", id, age);
        
        try {
            User updatedUser = userService.updateUserAge(id, age);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            log.error("更新用户年龄失败: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    // ========== 删除接口 ==========
    
    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("删除用户: {}", id);
        
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("删除用户失败: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 根据年龄删除用户
     */
    @DeleteMapping("/age/{age}")
    public ResponseEntity<Void> deleteUsersByAge(@PathVariable Integer age) {
        log.info("根据年龄删除用户: {}", age);
        
        userService.deleteUsersByAge(age);
        return ResponseEntity.ok().build();
    }
    
    // ========== 部门相关接口 ==========
    
    /**
     * 为用户添加部门
     */
    @PostMapping("/{userId}/departments/{departmentId}")
    public ResponseEntity<User> addDepartmentToUser(
            @PathVariable Long userId,
            @PathVariable Long departmentId) {
        
        log.info("为用户添加部门: 用户ID={}, 部门ID={}", userId, departmentId);
        
        try {
            User updatedUser = userService.addDepartmentToUser(userId, departmentId);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            log.error("为用户添加部门失败: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 从用户移除部门
     */
    @DeleteMapping("/{userId}/departments/{departmentId}")
    public ResponseEntity<User> removeDepartmentFromUser(
            @PathVariable Long userId,
            @PathVariable Long departmentId) {
        
        log.info("从用户移除部门: 用户ID={}, 部门ID={}", userId, departmentId);
        
        try {
            User updatedUser = userService.removeDepartmentFromUser(userId, departmentId);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            log.error("从用户移除部门失败: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 获取用户的部门列表
     */
    @GetMapping("/{userId}/departments")
    public ResponseEntity<List<Department>> getUserDepartments(@PathVariable Long userId) {
        log.info("获取用户的部门列表: {}", userId);
        
        try {
            List<Department> departments = userService.getUserDepartments(userId);
            return ResponseEntity.ok(departments);
        } catch (RuntimeException e) {
            log.error("获取用户部门列表失败: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
} 