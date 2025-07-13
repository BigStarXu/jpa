package com.example.jpa.repository;

import com.example.jpa.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository简化测试
 * 使用H2内存数据库进行测试
 */
@DataJpaTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=true"
})
@DisplayName("UserRepository简化测试")
class UserRepositorySimpleTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // 清理测试数据
        userRepository.deleteAll();

        // 创建测试用户
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setAge(25);
    }

    @Test
    @DisplayName("测试保存用户")
    void testSaveUser() {
        // 保存用户
        User savedUser = userRepository.save(testUser);

        // 验证保存成功
        assertNotNull(savedUser.getId());
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals(25, savedUser.getAge());
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
    }

    @Test
    @DisplayName("测试根据ID查找用户")
    void testFindById() {
        // 先保存用户
        User savedUser = userRepository.save(testUser);

        // 根据ID查找
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // 验证查找结果
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    @DisplayName("测试查找所有用户")
    void testFindAll() {
        // 保存用户
        userRepository.save(testUser);

        // 创建第二个用户
        User testUser2 = new User();
        testUser2.setUsername("testuser2");
        testUser2.setEmail("test2@example.com");
        testUser2.setAge(30);
        userRepository.save(testUser2);

        // 查找所有用户
        List<User> allUsers = userRepository.findAll();

        // 验证结果
        assertEquals(2, allUsers.size());
        assertTrue(allUsers.stream().anyMatch(u -> "testuser".equals(u.getUsername())));
        assertTrue(allUsers.stream().anyMatch(u -> "testuser2".equals(u.getUsername())));
    }

    @Test
    @DisplayName("测试根据用户名查找用户")
    void testFindByUsername() {
        // 保存用户
        userRepository.save(testUser);

        // 根据用户名查找
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        // 验证结果
        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    @DisplayName("测试根据邮箱查找用户")
    void testFindByEmail() {
        // 保存用户
        userRepository.save(testUser);

        // 根据邮箱查找
        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        // 验证结果
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    @DisplayName("测试用户统计信息")
    void testGetUserStatistics() {
        // 保存用户
        userRepository.save(testUser);

        // 创建第二个用户
        User testUser2 = new User();
        testUser2.setUsername("testuser2");
        testUser2.setEmail("test2@example.com");
        testUser2.setAge(30);
        userRepository.save(testUser2);

        // 获取统计信息
        Object[] stats = userRepository.getUserStatistics();

        // 验证统计结果
        assertNotNull(stats);
        assertEquals(2L, stats[0]); // 用户总数
        assertEquals(27.5, (Double) stats[1], 0.01); // 平均年龄
        assertEquals(30, stats[2]); // 最大年龄
        assertEquals(25, stats[3]); // 最小年龄
    }

    @Test
    @DisplayName("测试删除用户")
    void testDeleteUser() {
        // 保存用户
        User savedUser = userRepository.save(testUser);

        // 删除用户
        userRepository.delete(savedUser);

        // 验证用户已被删除
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertFalse(foundUser.isPresent());
    }
} 