package com.example.jpa.service;

import com.example.jpa.entity.Department;
import com.example.jpa.entity.User;
import com.example.jpa.repository.DepartmentRepository;
import com.example.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务类
 * 
 * 演示的JPA概念：
 * 1. @Service - 标记为服务类
 * 2. @Transactional - 事务管理
 * 3. @RequiredArgsConstructor - 自动注入依赖
 * 4. 业务逻辑处理
 * 5. 异常处理
 * 6. 数据验证
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    
    // ========== 查询方法 ==========
    
    /**
     * 查找所有用户
     */
    public List<User> findAllUsers() {
        log.info("查询所有用户");
        return userRepository.findAll();
    }
    
    /**
     * 根据ID查找用户
     */
    public Optional<User> findUserById(Long id) {
        log.info("根据ID查询用户: {}", id);
        return userRepository.findById(id);
    }
    
    /**
     * 根据用户名查找用户
     */
    public Optional<User> findUserByUsername(String username) {
        log.info("根据用户名查询用户: {}", username);
        return userRepository.findByUsername(username);
    }
    
    /**
     * 根据邮箱查找用户
     */
    public Optional<User> findUserByEmail(String email) {
        log.info("根据邮箱查询用户: {}", email);
        return userRepository.findByEmail(email);
    }
    
    /**
     * 根据年龄查找用户
     */
    public List<User> findUsersByAge(Integer age) {
        log.info("根据年龄查询用户: {}", age);
        return userRepository.findByAge(age);
    }
    
    /**
     * 根据年龄范围查找用户
     */
    public List<User> findUsersByAgeRange(Integer minAge, Integer maxAge) {
        log.info("根据年龄范围查询用户: {} - {}", minAge, maxAge);
        return userRepository.findByAgeBetween(minAge, maxAge);
    }
    
    /**
     * 根据用户名模糊查询
     */
    public List<User> findUsersByUsernameKeyword(String keyword) {
        log.info("根据用户名关键词查询用户: {}", keyword);
        return userRepository.findByUsernameContainingIgnoreCase(keyword);
    }
    
    /**
     * 分页查询所有用户
     */
    public Page<User> findUsersWithPagination(Pageable pageable) {
        log.info("分页查询用户: {}", pageable);
        return userRepository.findAll(pageable);
    }
    
    /**
     * 获取用户统计信息
     */
    public Object[] getUserStatistics() {
        log.info("获取用户统计信息");
        return userRepository.getUserStatistics();
    }
    
    /**
     * 获取平均年龄
     */
    public Double getAverageAge() {
        log.info("获取用户平均年龄");
        return userRepository.getAverageAge();
    }
    
    // ========== 创建方法 ==========
    
    /**
     * 创建新用户
     */
    @Transactional
    public User createUser(User user) {
        log.info("创建新用户: {}", user.getUsername());
        
        // 验证用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("用户名已存在: " + user.getUsername());
        }
        
        // 验证邮箱是否已存在
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("邮箱已存在: " + user.getEmail());
        }
        
        // 验证年龄
        if (user.getAge() != null && (user.getAge() < 0 || user.getAge() > 150)) {
            throw new RuntimeException("年龄无效: " + user.getAge());
        }
        
        return userRepository.save(user);
    }
    
    /**
     * 批量创建用户
     */
    @Transactional
    public List<User> createUsers(List<User> users) {
        log.info("批量创建用户，数量: {}", users.size());
        
        for (User user : users) {
            // 验证用户名是否已存在
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new RuntimeException("用户名已存在: " + user.getUsername());
            }
            
            // 验证邮箱是否已存在
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("邮箱已存在: " + user.getEmail());
            }
        }
        
        return userRepository.saveAll(users);
    }
    
    // ========== 更新方法 ==========
    
    /**
     * 更新用户信息
     */
    @Transactional
    public User updateUser(Long id, User userDetails) {
        log.info("更新用户信息: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + id));
        
        // 检查用户名是否被其他用户使用
        if (!user.getUsername().equals(userDetails.getUsername())) {
            if (userRepository.findByUsername(userDetails.getUsername()).isPresent()) {
                throw new RuntimeException("用户名已存在: " + userDetails.getUsername());
            }
        }
        
        // 检查邮箱是否被其他用户使用
        if (!user.getEmail().equals(userDetails.getEmail())) {
            if (userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                throw new RuntimeException("邮箱已存在: " + userDetails.getEmail());
            }
        }
        
        // 更新用户信息
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setAge(userDetails.getAge());
        
        return userRepository.save(user);
    }
    
    /**
     * 更新用户年龄
     */
    @Transactional
    public User updateUserAge(Long id, Integer age) {
        log.info("更新用户年龄: {} -> {}", id, age);
        
        if (age < 0 || age > 150) {
            throw new RuntimeException("年龄无效: " + age);
        }
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + id));
        
        user.setAge(age);
        return userRepository.save(user);
    }
    
    // ========== 删除方法 ==========
    
    /**
     * 删除用户
     */
    @Transactional
    public void deleteUser(Long id) {
        log.info("删除用户: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("用户不存在: " + id);
        }
        
        userRepository.deleteById(id);
    }
    
    /**
     * 根据年龄删除用户
     */
    @Transactional
    public void deleteUsersByAge(Integer age) {
        log.info("根据年龄删除用户: {}", age);
        userRepository.deleteByAge(age);
    }
    
    // ========== 部门相关方法 ==========
    
    /**
     * 为用户添加部门
     */
    @Transactional
    public User addDepartmentToUser(Long userId, Long departmentId) {
        log.info("为用户添加部门: 用户ID={}, 部门ID={}", userId, departmentId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + userId));
        
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("部门不存在: " + departmentId));
        
        user.addDepartment(department);
        return userRepository.save(user);
    }
    
    /**
     * 从用户移除部门
     */
    @Transactional
    public User removeDepartmentFromUser(Long userId, Long departmentId) {
        log.info("从用户移除部门: 用户ID={}, 部门ID={}", userId, departmentId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + userId));
        
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("部门不存在: " + departmentId));
        
        user.removeDepartment(department);
        return userRepository.save(user);
    }
    
    /**
     * 获取用户的部门列表
     */
    public List<Department> getUserDepartments(Long userId) {
        log.info("获取用户的部门列表: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在: " + userId));
        
        return user.getDepartments().stream().toList();
    }
} 