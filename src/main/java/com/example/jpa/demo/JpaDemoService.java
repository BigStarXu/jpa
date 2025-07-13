package com.example.jpa.demo;

import com.example.jpa.entity.*;
import com.example.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * JPA演示服务类
 * 
 * 这个类演示了JPA的各种功能和用法：
 * 1. 基本的CRUD操作
 * 2. 关系映射操作
 * 3. 继承映射操作
 * 4. 复杂查询操作
 * 5. 事务管理
 * 6. 批量操作
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JpaDemoService {
    
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final OrderRepository orderRepository;
    
    /**
     * 演示基本的CRUD操作
     */
    public void demonstrateBasicCrud() {
        log.info("=== 演示基本的CRUD操作 ===");
        
        // 创建用户
        User user = new User();
        user.setUsername("演示用户");
        user.setEmail("demo@example.com");
        user.setAge(25);
        
        User savedUser = userRepository.save(user);
        log.info("创建用户: {}", savedUser);
        
        // 查询用户
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        log.info("查询用户: {}", foundUser);
        
        // 更新用户
        foundUser.setAge(26);
        User updatedUser = userRepository.save(foundUser);
        log.info("更新用户: {}", updatedUser);
        
        // 删除用户
        userRepository.deleteById(updatedUser.getId());
        log.info("删除用户完成");
    }
    
    /**
     * 演示关系映射操作
     */
    public void demonstrateRelationships() {
        log.info("=== 演示关系映射操作 ===");
        
        // 创建部门
        Department techDept = new Department();
        techDept.setName("技术部");
        techDept.setDescription("负责产品开发");
        departmentRepository.save(techDept);
        
        Department marketDept = new Department();
        marketDept.setName("市场部");
        marketDept.setDescription("负责市场推广");
        departmentRepository.save(marketDept);
        
        // 创建用户并关联部门
        User user1 = new User();
        user1.setUsername("技术用户");
        user1.setEmail("tech@example.com");
        user1.setAge(28);
        user1.addDepartment(techDept);
        userRepository.save(user1);
        
        User user2 = new User();
        user2.setUsername("市场用户");
        user2.setEmail("market@example.com");
        user2.setAge(30);
        user2.addDepartment(marketDept);
        user2.addDepartment(techDept); // 一个用户可以属于多个部门
        userRepository.save(user2);
        
        log.info("用户1的部门: {}", user1.getDepartments());
        log.info("用户2的部门: {}", user2.getDepartments());
    }
    
    /**
     * 演示继承映射操作
     */
    public void demonstrateInheritance() {
        log.info("=== 演示继承映射操作 ===");
        
        // 创建员工
        Employee employee = new Employee();
        employee.setName("张三");
        employee.setEmail("zhangsan@company.com");
        employee.setPhone("13800138000");
        employee.setEmployeeId("EMP001");
        employee.setSalary(new BigDecimal("8000"));
        employee.setPosition(Employee.Position.JUNIOR_DEVELOPER);
        employee.setHireDate(java.time.LocalDate.now());
        employee.setDepartment("技术部");
        
        // 创建客户
        Customer customer = new Customer();
        customer.setName("李四");
        customer.setEmail("lisi@customer.com");
        customer.setPhone("13900139000");
        customer.setCustomerId("CUST001");
        customer.setTotalSpent(new BigDecimal("5000"));
        customer.setCustomerType(Customer.CustomerType.VIP);
        customer.setRegistrationDate(java.time.LocalDate.now());
        customer.setAddress("北京市朝阳区");
        
        log.info("员工信息: {}", employee);
        log.info("客户信息: {}", customer);
    }
    
    /**
     * 演示复杂查询操作
     */
    public void demonstrateComplexQueries() {
        log.info("=== 演示复杂查询操作 ===");
        
        // 方法名查询
        List<User> usersByAge = userRepository.findByAge(25);
        log.info("25岁用户数量: {}", usersByAge.size());
        
        // 范围查询
        List<User> usersByAgeRange = userRepository.findByAgeBetween(20, 30);
        log.info("20-30岁用户数量: {}", usersByAgeRange.size());
        
        // 模糊查询
        List<User> usersByKeyword = userRepository.findByUsernameContainingIgnoreCase("用户");
        log.info("包含'用户'的用户数量: {}", usersByKeyword.size());
        
        // JPQL查询
        List<User> usersByJpql = userRepository.findUsersByAgeRange(20, 30);
        log.info("JPQL查询20-30岁用户数量: {}", usersByJpql.size());
        
        // 统计查询
        Object[] stats = userRepository.getUserStatistics();
        log.info("用户统计: 总数={}, 平均年龄={}, 最大年龄={}, 最小年龄={}", 
                stats[0], stats[1], stats[2], stats[3]);
        
        // 原生SQL查询
        long count = userRepository.countUsersNative();
        log.info("原生SQL查询用户总数: {}", count);
    }
    
    /**
     * 演示订单和订单项操作
     */
    public void demonstrateOrderOperations() {
        log.info("=== 演示订单和订单项操作 ===");
        
        // 创建用户
        User user = new User();
        user.setUsername("订单用户");
        user.setEmail("order@example.com");
        user.setAge(25);
        userRepository.save(user);
        
        // 创建订单
        Order order = new Order();
        order.setOrderNumber("ORD" + System.currentTimeMillis());
        order.setUser(user);
        order.setStatus(Order.OrderStatus.PENDING);
        
        // 创建订单项
        OrderItem item1 = new OrderItem();
        item1.setProductName("笔记本电脑");
        item1.setQuantity(1);
        item1.setPrice(new BigDecimal("5999"));
        
        OrderItem item2 = new OrderItem();
        item2.setProductName("鼠标");
        item2.setQuantity(2);
        item2.setPrice(new BigDecimal("99"));
        
        // 添加订单项到订单
        order.addOrderItem(item1);
        order.addOrderItem(item2);
        
        // 计算总金额
        order.calculateTotalAmount();
        
        // 保存订单
        Order savedOrder = orderRepository.save(order);
        log.info("创建订单: {}", savedOrder);
        log.info("订单总金额: {}", savedOrder.getTotalAmount());
        log.info("订单项数量: {}", savedOrder.getOrderItems().size());
    }
    
    /**
     * 演示批量操作
     */
    public void demonstrateBatchOperations() {
        log.info("=== 演示批量操作 ===");
        
        // 批量创建用户
        List<User> users = Arrays.asList(
                createUser("批量用户1", "batch1@example.com", 25),
                createUser("批量用户2", "batch2@example.com", 26),
                createUser("批量用户3", "batch3@example.com", 27)
        );
        
        List<User> savedUsers = userRepository.saveAll(users);
        log.info("批量创建用户数量: {}", savedUsers.size());
        
        // 批量查询
        List<User> allUsers = userRepository.findAll();
        log.info("所有用户数量: {}", allUsers.size());
        
        // 批量删除
        userRepository.deleteAll(savedUsers);
        log.info("批量删除用户完成");
    }
    
    /**
     * 演示事务操作
     */
    public void demonstrateTransactionOperations() {
        log.info("=== 演示事务操作 ===");
        
        try {
            // 这个操作会在一个事务中执行
            User user = createUser("事务用户", "transaction@example.com", 25);
            userRepository.save(user);
            
            // 模拟一个异常
            if (true) {
                throw new RuntimeException("模拟事务回滚");
            }
            
        } catch (Exception e) {
            log.error("事务操作失败: {}", e.getMessage());
            // 由于使用了@Transactional，所有操作都会回滚
        }
        
        // 验证用户是否被回滚（应该不存在）
        long count = userRepository.count();
        log.info("当前用户数量: {}", count);
    }
    
    /**
     * 演示投影查询
     */
    public void demonstrateProjectionQueries() {
        log.info("=== 演示投影查询 ===");
        
        // 只查询用户名和邮箱
        List<Object[]> usernameAndEmails = userRepository.findUsernameAndEmail();
        log.info("用户名和邮箱投影查询结果:");
        for (Object[] result : usernameAndEmails) {
            log.info("用户名: {}, 邮箱: {}", result[0], result[1]);
        }
        
        // 只查询用户名
        List<String> usernames = userRepository.findAllUsernames();
        log.info("用户名列表: {}", usernames);
        
        // 只查询邮箱
        List<String> emails = userRepository.findAllEmails();
        log.info("邮箱列表: {}", emails);
    }
    
    /**
     * 辅助方法：创建用户
     */
    private User createUser(String username, String email, Integer age) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setAge(age);
        return user;
    }
    
    /**
     * 运行所有演示
     */
    public void runAllDemos() {
        log.info("开始运行所有JPA演示...");
        
        try {
            demonstrateBasicCrud();
            demonstrateRelationships();
            demonstrateInheritance();
            demonstrateComplexQueries();
            demonstrateOrderOperations();
            demonstrateBatchOperations();
            demonstrateTransactionOperations();
            demonstrateProjectionQueries();
            
            log.info("所有JPA演示完成！");
        } catch (Exception e) {
            log.error("演示过程中发生错误: {}", e.getMessage(), e);
        }
    }
} 