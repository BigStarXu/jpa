package com.example.jpa.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * User实体简单测试
 * 不涉及数据库，只测试实体类本身
 */
@DisplayName("User实体简单测试")
class UserSimpleTest {

    @Test
    @DisplayName("测试用户基本信息")
    void testUserBasicInfo() {
        // 创建用户
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setAge(25);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 验证基本信息
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(25, user.getAge());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    @DisplayName("测试用户年龄设置")
    void testUserAge() {
        User user = new User();
        
        // 测试正常年龄
        user.setAge(30);
        assertEquals(30, user.getAge());

        // 测试null年龄
        user.setAge(null);
        assertNull(user.getAge());

        // 测试0年龄
        user.setAge(0);
        assertEquals(0, user.getAge());
    }

    @Test
    @DisplayName("测试用户部门关系")
    void testUserDepartmentRelationship() {
        User user = new User();
        Department department = new Department();
        department.setId(1L);
        department.setName("技术部");

        // 初始状态应该没有部门
        assertTrue(user.getDepartments().isEmpty());

        // 添加部门
        user.addDepartment(department);
        assertEquals(1, user.getDepartments().size());
        assertTrue(user.getDepartments().contains(department));
        assertTrue(department.getUsers().contains(user));

        // 移除部门
        user.removeDepartment(department);
        assertEquals(0, user.getDepartments().size());
        assertFalse(user.getDepartments().contains(department));
        assertFalse(department.getUsers().contains(user));
    }

    @Test
    @DisplayName("测试用户订单关系")
    void testUserOrderRelationship() {
        User user = new User();
        
        // 初始状态应该没有订单
        assertTrue(user.getOrders().isEmpty());

        // 创建订单
        Order order = new Order();
        order.setId(1L);
        order.setOrderNumber("ORD-001");
        order.setUser(user);

        // 添加订单
        user.getOrders().add(order);
        assertEquals(1, user.getOrders().size());
        assertTrue(user.getOrders().contains(order));
        assertEquals(user, order.getUser());
    }

    @Test
    @DisplayName("测试用户toString方法")
    void testUserToString() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setAge(25);

        String userString = user.toString();
        
        // toString应该包含用户信息
        assertTrue(userString.contains("testuser"));
        assertTrue(userString.contains("test@example.com"));
        assertTrue(userString.contains("25"));
    }

    @Test
    @DisplayName("测试用户equals和hashCode")
    void testUserEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("user1");
        user2.setEmail("user1@example.com");

        User user3 = new User();
        user3.setId(2L);
        user3.setUsername("user2");
        user3.setEmail("user2@example.com");

        // 相同ID的用户应该相等
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        // 不同ID的用户不应该相等
        assertNotEquals(user1, user3);
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }
} 