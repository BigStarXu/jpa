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
 * UserRepository MySQL测试
 * 使用真实的MySQL数据库进行测试
 * 注意：这会操作真实数据库，请确保使用测试数据库
 */
@DataJpaTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:mysql://localhost:3306/jpa_learning_test?useSSL=false&serverTimezone=UTC",
    "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
    "spring.datasource.username=root",
    "spring.datasource.password=root",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=true"
})
@DisplayName("UserRepository MySQL测试")
class UserRepositoryMySqlTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // 清理测试数据
        userRepository.deleteAll();

        // 创建测试用户
        testUser = new User();
        testUser.setUsername("mysql_test_user");
        testUser.setEmail("mysql_test@example.com");
        testUser.setAge(25);
    }

    @Test
    @DisplayName("测试MySQL保存用户")
    void testSaveUser() {
        // 保存用户到MySQL
        User savedUser = userRepository.save(testUser);

        // 验证保存成功
        assertNotNull(savedUser.getId());
        assertEquals("mysql_test_user", savedUser.getUsername());
        assertEquals("mysql_test@example.com", savedUser.getEmail());
        assertEquals(25, savedUser.getAge());
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getUpdatedAt());
    }

    @Test
    @DisplayName("测试MySQL查找用户")
    void testFindUser() {
        // 先保存用户
        User savedUser = userRepository.save(testUser);

        // 根据ID查找
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("mysql_test_user", foundUser.get().getUsername());

        // 根据用户名查找
        Optional<User> foundByUsername = userRepository.findByUsername("mysql_test_user");
        assertTrue(foundByUsername.isPresent());
        assertEquals("mysql_test@example.com", foundByUsername.get().getEmail());
    }

    @Test
    @DisplayName("测试MySQL用户统计")
    void testUserStatistics() {
        // 保存用户
        userRepository.save(testUser);

        // 创建第二个用户
        User testUser2 = new User();
        testUser2.setUsername("mysql_test_user2");
        testUser2.setEmail("mysql_test2@example.com");
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
} 