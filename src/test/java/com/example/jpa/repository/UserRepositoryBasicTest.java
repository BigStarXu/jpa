package com.example.jpa.repository;

import com.example.jpa.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * UserRepository基础测试
 * 最简单的数据库操作测试
 */
@DataJpaTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@DisplayName("UserRepository基础测试")
class UserRepositoryBasicTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("测试保存和查找用户")
    void testSaveAndFindUser() {
        // 创建用户
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setAge(25);

        // 保存用户
        User savedUser = userRepository.save(user);

        // 验证保存成功
        assertNotNull(savedUser.getId());
        assertEquals("testuser", savedUser.getUsername());

        // 查找所有用户
        List<User> allUsers = userRepository.findAll();
        assertEquals(1, allUsers.size());
        assertEquals("testuser", allUsers.get(0).getUsername());
    }

    @Test
    @DisplayName("测试用户统计")
    void testUserStatistics() {
        // 创建两个用户
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setAge(25);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setAge(30);

        // 保存用户
        userRepository.save(user1);
        userRepository.save(user2);

        // 获取统计信息
        Object[] stats = userRepository.getUserStatistics();

        // 验证统计结果
        assertNotNull(stats);
        assertEquals(2L, stats[0]); // 用户总数
        assertEquals(27.5, (Double) stats[1], 0.01); // 平均年龄
    }
} 