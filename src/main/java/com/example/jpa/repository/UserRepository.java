package com.example.jpa.repository;

import com.example.jpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户仓库接口
 * 
 * 演示的Spring Data JPA概念：
 * 1. 继承JpaRepository获得基本的CRUD操作
 * 2. 方法名查询 - 根据方法名自动生成查询
 * 3. @Query注解 - 自定义JPQL查询
 * 4. @Param注解 - 参数绑定
 * 5. 分页查询 - Pageable接口
 * 6. 排序查询 - Sort接口
 * 7. 投影查询 - 只查询特定字段
 * 8. 原生SQL查询 - @Query(nativeQuery = true)
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // ========== 方法名查询 ==========
    
    /**
     * 根据用户名查找用户
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 根据年龄查找用户列表
     */
    List<User> findByAge(Integer age);
    
    /**
     * 根据年龄范围查找用户
     */
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);
    
    /**
     * 根据用户名模糊查询
     */
    List<User> findByUsernameContainingIgnoreCase(String username);
    
    /**
     * 根据邮箱后缀查找用户
     */
    List<User> findByEmailEndingWith(String emailSuffix);
    
    /**
     * 根据年龄大于指定值查找用户
     */
    List<User> findByAgeGreaterThan(Integer age);
    
    /**
     * 根据创建时间查找用户
     */
    List<User> findByCreatedAtAfter(LocalDateTime dateTime);
    
    /**
     * 统计指定年龄的用户数量
     */
    long countByAge(Integer age);
    
    /**
     * 根据年龄删除用户
     */
    void deleteByAge(Integer age);
    
    /**
     * 根据用户名和邮箱查找用户
     */
    Optional<User> findByUsernameAndEmail(String username, String email);
    
    /**
     * 根据用户名或邮箱查找用户
     */
    List<User> findByUsernameOrEmail(String username, String email);
    
    // ========== 分页和排序查询 ==========
    
    /**
     * 分页查询所有用户
     */
    Page<User> findAll(Pageable pageable);
    
    /**
     * 根据年龄分页查询用户
     */
    Page<User> findByAge(Integer age, Pageable pageable);
    
    /**
     * 根据用户名模糊查询并分页
     */
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
    
    // ========== @Query注解查询 ==========
    
    /**
     * 使用JPQL查询所有用户
     */
    @Query("SELECT u FROM User u")
    List<User> findAllUsers();
    
    /**
     * 使用JPQL查询指定年龄范围的用户
     */
    @Query("SELECT u FROM User u WHERE u.age BETWEEN :minAge AND :maxAge")
    List<User> findUsersByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    
    /**
     * 使用JPQL查询用户名包含指定字符串的用户
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> findUsersByUsernameKeyword(@Param("keyword") String keyword);
    
    /**
     * 使用JPQL查询用户数量
     */
    @Query("SELECT COUNT(u) FROM User u")
    long countAllUsers();
    
    /**
     * 使用JPQL查询平均年龄
     */
    @Query("SELECT AVG(u.age) FROM User u")
    Double getAverageAge();
    
    /**
     * 使用JPQL查询最大年龄
     */
    @Query("SELECT MAX(u.age) FROM User u")
    Integer getMaxAge();
    
    /**
     * 使用JPQL查询最小年龄
     */
    @Query("SELECT MIN(u.age) FROM User u")
    Integer getMinAge();
    
    /**
     * 使用JPQL查询用户统计信息
     */
    @Query("SELECT COUNT(u), AVG(COALESCE(u.age, 0)), MAX(COALESCE(u.age, 0)), MIN(COALESCE(u.age, 0)) FROM User u")
    Object[] getUserStatistics();
    
    // ========== 原生SQL查询 ==========
    
    /**
     * 使用原生SQL查询所有用户
     */
    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> findAllUsersNative();
    
    /**
     * 使用原生SQL查询指定年龄的用户
     */
    @Query(value = "SELECT * FROM users WHERE age = :age", nativeQuery = true)
    List<User> findUsersByAgeNative(@Param("age") Integer age);
    
    /**
     * 使用原生SQL查询用户数量
     */
    @Query(value = "SELECT COUNT(*) FROM users", nativeQuery = true)
    long countUsersNative();
    
    // ========== 投影查询 ==========
    
    /**
     * 只查询用户名和邮箱
     */
    @Query("SELECT u.username, u.email FROM User u")
    List<Object[]> findUsernameAndEmail();
    
    /**
     * 只查询用户名
     */
    @Query("SELECT u.username FROM User u")
    List<String> findAllUsernames();
    
    /**
     * 只查询邮箱
     */
    @Query("SELECT u.email FROM User u")
    List<String> findAllEmails();
} 