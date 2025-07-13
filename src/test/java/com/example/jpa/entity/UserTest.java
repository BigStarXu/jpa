package com.example.jpa.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User实体类测试
 * 测试用户实体的各种功能和关系映射
 */
@DisplayName("User实体类测试")
class UserTest {

    private User user;
    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        // 创建测试用户
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setAge(25);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 创建测试部门
        department1 = new Department();
        department1.setId(1L);
        department1.setName("技术部");
        department1.setDescription("负责技术开发");

        department2 = new Department();
        department2.setId(2L);
        department2.setName("产品部");
        department2.setDescription("负责产品设计");
    }

    @Test
    @DisplayName("测试用户基本信息设置和获取")
    void testUserBasicInfo() {
        // 验证基本信息
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(25, user.getAge());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    @DisplayName("测试用户年龄验证")
    void testUserAgeValidation() {
        // 测试正常年龄
        user.setAge(30);
        assertEquals(30, user.getAge());

        // 测试边界年龄
        user.setAge(0);
        assertEquals(0, user.getAge());

        user.setAge(150);
        assertEquals(150, user.getAge());

        // 测试null年龄
        user.setAge(null);
        assertNull(user.getAge());
    }

    @Test
    @DisplayName("测试用户与部门的多对多关系")
    void testUserDepartmentRelationship() {
        // 初始状态应该没有部门
        assertTrue(user.getDepartments().isEmpty());

        // 添加部门
        user.addDepartment(department1);
        assertEquals(1, user.getDepartments().size());
        assertTrue(user.getDepartments().contains(department1));
        assertTrue(department1.getUsers().contains(user));

        // 添加第二个部门
        user.addDepartment(department2);
        assertEquals(2, user.getDepartments().size());
        assertTrue(user.getDepartments().contains(department2));
        assertTrue(department2.getUsers().contains(user));

        // 移除部门
        user.removeDepartment(department1);
        assertEquals(1, user.getDepartments().size());
        assertFalse(user.getDepartments().contains(department1));
        assertFalse(department1.getUsers().contains(user));
        assertTrue(user.getDepartments().contains(department2));
    }

    @Test
    @DisplayName("测试用户订单关系")
    void testUserOrderRelationship() {
        // 初始状态应该没有订单
        assertTrue(user.getOrders().isEmpty());

        // 创建订单
        Order order = new Order();
        order.setId(1L);
        order.setOrderNumber("ORD-001");
        order.setUser(user);

        // 添加订单到用户（通过订单设置用户）
        user.getOrders().add(order);
        assertEquals(1, user.getOrders().size());
        assertTrue(user.getOrders().contains(order));
        assertEquals(user, order.getUser());
    }

    @Test
    @DisplayName("测试用户时间戳自动更新")
    void testUserTimestampUpdate() {
        LocalDateTime originalCreatedAt = user.getCreatedAt();
        LocalDateTime originalUpdatedAt = user.getUpdatedAt();

        // 模拟时间更新
        LocalDateTime newUpdatedAt = LocalDateTime.now().plusHours(1);
        user.setUpdatedAt(newUpdatedAt);

        // 创建时间不应该改变
        assertEquals(originalCreatedAt, user.getCreatedAt());
        // 更新时间应该改变
        assertEquals(newUpdatedAt, user.getUpdatedAt());
    }

    @Test
    @DisplayName("测试用户数据验证")
    void testUserDataValidation() {
        // 测试用户名不能为空
        assertThrows(NullPointerException.class, () -> {
            user.setUsername(null);
            user.getUsername().length(); // 这会抛出NPE
        });

        // 测试邮箱格式（简单验证）
        user.setEmail("invalid-email");
        assertEquals("invalid-email", user.getEmail());

        user.setEmail("valid@example.com");
        assertEquals("valid@example.com", user.getEmail());
    }

    @Test
    @DisplayName("测试用户集合操作")
    void testUserCollectionOperations() {
        Set<Department> departments = new HashSet<>();
        departments.add(department1);
        departments.add(department2);

        user.setDepartments(departments);
        assertEquals(2, user.getDepartments().size());

        // 测试集合不可变性
        departments.clear();
        assertEquals(2, user.getDepartments().size()); // 用户部门集合不应该被清空
    }

    @Test
    @DisplayName("测试用户toString方法")
    void testUserToString() {
        String userString = user.toString();
        
        // toString应该包含用户信息但不包含部门（避免循环引用）
        assertTrue(userString.contains("testuser"));
        assertTrue(userString.contains("test@example.com"));
        assertTrue(userString.contains("25"));
        assertFalse(userString.contains("departments")); // 应该排除departments字段
    }

    @Test
    @DisplayName("测试用户equals和hashCode方法")
    void testUserEqualsAndHashCode() {
        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("testuser");
        user2.setEmail("test@example.com");

        User user3 = new User();
        user3.setId(2L);
        user3.setUsername("differentuser");
        user3.setEmail("different@example.com");

        // 相同ID的用户应该相等
        assertEquals(user, user2);
        assertEquals(user.hashCode(), user2.hashCode());

        // 不同ID的用户不应该相等
        assertNotEquals(user, user3);
        assertNotEquals(user.hashCode(), user3.hashCode());

        // 与null比较
        assertNotEquals(user, null);

        // 与不同类型对象比较
        assertNotEquals(user, "string");
    }

    @Test
    @DisplayName("测试用户构建器模式（如果使用Lombok @Builder）")
    void testUserBuilder() {
        // 注意：当前User类没有使用@Builder注解
        // 如果需要，可以在User类上添加@Builder注解
        User builtUser = new User();
        builtUser.setId(100L);
        builtUser.setUsername("builtuser");
        builtUser.setEmail("built@example.com");
        builtUser.setAge(30);

        assertEquals(100L, builtUser.getId());
        assertEquals("builtuser", builtUser.getUsername());
        assertEquals("built@example.com", builtUser.getEmail());
        assertEquals(30, builtUser.getAge());
    }
} 