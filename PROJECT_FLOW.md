# JPA学习项目完整流程文档

## 📋 项目概述

这是一个基于Spring Boot 3.2.0、JPA、MySQL和JSP的完整JPA学习项目，包含用户管理、部门管理、订单管理等功能，旨在通过实际项目学习JPA的核心概念和最佳实践。

## 🏗️ 技术架构

### 技术栈
- **后端框架**: Spring Boot 3.2.0
- **持久层**: Spring Data JPA + Hibernate
- **数据库**: MySQL 8.0
- **前端**: JSP + JSTL
- **构建工具**: Maven
- **Java版本**: Java 17+

### 项目结构
```
jpa-learning/
├── src/
│   ├── main/
│   │   ├── java/com/example/jpa/
│   │   │   ├── JpaLearningApplication.java     # 主应用类
│   │   │   ├── config/
│   │   │   │   ├── JpaConfig.java              # JPA配置
│   │   │   │   └── WebConfig.java              # Web配置（JSP支持）
│   │   │   ├── controller/
│   │   │   │   ├── UserController.java         # 用户REST API
│   │   │   │   ├── PageController.java         # 页面控制器
│   │   │   │   └── DemoController.java         # 演示控制器
│   │   │   ├── entity/
│   │   │   │   ├── User.java                   # 用户实体
│   │   │   │   ├── Department.java             # 部门实体
│   │   │   │   ├── Order.java                  # 订单实体
│   │   │   │   ├── OrderItem.java              # 订单项实体
│   │   │   │   ├── Person.java                 # 人员基类
│   │   │   │   ├── Employee.java               # 员工实体
│   │   │   │   └── Customer.java               # 客户实体
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java         # 用户仓库
│   │   │   │   ├── DepartmentRepository.java   # 部门仓库
│   │   │   │   └── OrderRepository.java        # 订单仓库
│   │   │   ├── service/
│   │   │   │   └── UserService.java            # 用户服务
│   │   │   └── demo/
│   │   │       └── JpaDemoService.java         # JPA演示服务
│   │   ├── resources/
│   │   │   ├── application.yml                 # 主配置文件
│   │   │   ├── application-test.yml            # 测试配置文件
│   │   │   └── static/                         # 静态资源
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── views/
│   │               ├── index.jsp               # 主页
│   │               ├── users.jsp               # 用户列表页
│   │               └── user-detail.jsp         # 用户详情页
│   └── test/
│       └── java/com/example/jpa/
│           ├── JpaLearningApplicationTests.java # 基础测试类
│           ├── entity/
│           │   └── UserTest.java               # 用户实体测试
│           └── repository/
│               └── UserRepositoryTest.java     # 用户仓库测试
├── pom.xml                                      # Maven配置
├── README.md                                    # 项目说明
├── PROJECT_FLOW.md                              # 项目流程文档
├── init_database.sql                            # 数据库初始化脚本
├── insert_test_users.sql                        # 测试数据脚本
└── database_init.sql                            # 基础数据库脚本
```

## 🔄 完整项目流程

### 第一阶段：项目初始化

#### 1.1 环境准备
```bash
# 检查Java版本
java -version  # 需要Java 17+

# 检查Maven版本
mvn -version   # 需要Maven 3.6+

# 检查MySQL服务
mysql --version # 需要MySQL 8.0+
```

#### 1.2 创建Spring Boot项目
```bash
# 使用Spring Initializr创建项目
# 选择依赖：Spring Web, Spring Data JPA, MySQL Driver, Spring Boot DevTools
```

#### 1.3 配置Maven依赖
```xml
<!-- pom.xml核心依赖 -->
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- MySQL Driver -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    
    <!-- JSP支持 -->
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
        <scope>provided</scope>
    </dependency>
    
    <!-- JSTL -->
    <dependency>
        <groupId>jakarta.servlet.jsp.jstl</groupId>
        <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
        <version>3.0.0</version>
    </dependency>
    
    <!-- 测试依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- H2测试数据库 -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### 第二阶段：数据库配置

#### 2.1 创建数据库
```sql
-- 在MySQL中执行
CREATE DATABASE IF NOT EXISTS jpa_learning 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

#### 2.2 配置数据库连接
```yaml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/jpa_learning?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8
    username: root
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update  # 开发环境使用update，生产环境使用validate
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
    database-platform: org.hibernate.dialect.MySQL8Dialect

server:
  port: 8080
```

#### 2.3 测试配置
```yaml
# application-test.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    username: sa
    password: 
    driver-class-name: org.h2.Driver
  
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.H2Dialect
        format_sql: true
    database-platform: org.hibernate.dialect.H2Dialect
```

### 第三阶段：实体设计

#### 3.1 基础实体类
```java
// User.java - 用户实体
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // 关系映射
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_departments",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments = new HashSet<>();
}
```

#### 3.2 关系映射
```java
// Department.java - 部门实体
@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @ManyToMany(mappedBy = "departments")
    @ToString.Exclude
    private Set<User> users = new HashSet<>();
}
```

#### 3.3 继承映射
```java
// Person.java - 人员基类
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "age")
    private Integer age;
}

// Employee.java - 员工实体
@Entity
@DiscriminatorValue("EMPLOYEE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends Person {
    @Column(name = "employee_id", unique = true)
    private String employeeId;
    
    @Column(name = "salary")
    private BigDecimal salary;
}

// Customer.java - 客户实体
@Entity
@DiscriminatorValue("CUSTOMER")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Person {
    @Column(name = "customer_code", unique = true)
    private String customerCode;
    
    @Column(name = "credit_limit")
    private BigDecimal creditLimit;
}
```

### 第四阶段：Repository层

#### 4.1 基础Repository
```java
// UserRepository.java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 方法名查询
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByAge(Integer age);
    List<User> findByAgeBetween(Integer minAge, Integer maxAge);
    List<User> findByUsernameContainingIgnoreCase(String username);
    
    // @Query注解查询
    @Query("SELECT u FROM User u WHERE u.age BETWEEN :minAge AND :maxAge")
    List<User> findUsersByAgeRange(@Param("minAge") Integer minAge, 
                                   @Param("maxAge") Integer maxAge);
    
    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword%")
    List<User> searchUsers(@Param("keyword") String keyword);
    
    // 统计查询
    @Query("SELECT COUNT(u) FROM User u")
    Long countAllUsers();
    
    @Query("SELECT AVG(u.age) FROM User u")
    Double getAverageAge();
    
    // 分页查询
    Page<User> findByAgeGreaterThan(Integer age, Pageable pageable);
    
    // 排序查询
    List<User> findAllByOrderByAgeAsc();
    List<User> findAllByOrderByCreatedAtDesc();
}
```

#### 4.2 复杂查询
```java
// 原生SQL查询
@Query(value = "SELECT * FROM users WHERE age = :age", nativeQuery = true)
List<User> findUsersByAgeNative(@Param("age") Integer age);

// 投影查询
@Query("SELECT u.username, u.email FROM User u")
List<Object[]> findUserProjections();

// 聚合查询
@Query("SELECT u.age, COUNT(u) FROM User u GROUP BY u.age")
List<Object[]> findUserAgeDistribution();
```

### 第五阶段：Service层

#### 5.1 业务逻辑
```java
// UserService.java
@Service
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // 创建用户
    @Transactional
    public User createUser(User user) {
        log.info("Creating user: {}", user.getUsername());
        
        // 验证用户名唯一性
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("用户名已存在: " + user.getUsername());
        }
        
        // 验证邮箱唯一性
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("邮箱已存在: " + user.getEmail());
        }
        
        return userRepository.save(user);
    }
    
    // 批量创建用户
    @Transactional
    public List<User> createUsers(List<User> users) {
        log.info("Creating {} users", users.size());
        return userRepository.saveAll(users);
    }
    
    // 更新用户
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("用户不存在: " + id));
        
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setAge(userDetails.getAge());
        
        return userRepository.save(user);
    }
    
    // 获取用户统计信息
    public Map<String, Object> getUserStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalUsers", userRepository.countAllUsers());
        statistics.put("averageAge", userRepository.getAverageAge());
        return statistics;
    }
}
```

### 第六阶段：Controller层

#### 6.1 REST API控制器
```java
// UserController.java
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    // 获取所有用户
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    
    // 根据ID获取用户
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("用户不存在: " + id));
        return ResponseEntity.ok(user);
    }
    
    // 创建用户
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    // 更新用户
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
    
    // 删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // 搜索用户
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String keyword) {
        List<User> users = userRepository.searchUsers(keyword);
        return ResponseEntity.ok(users);
    }
    
    // 获取用户统计
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getUserStatistics() {
        Map<String, Object> statistics = userService.getUserStatistics();
        return ResponseEntity.ok(statistics);
    }
}
```

#### 6.2 页面控制器
```java
// PageController.java
@Controller
public class PageController {
    
    @Autowired
    private UserRepository userRepository;
    
    // 主页
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "欢迎使用JPA学习项目");
        return "index";
    }
    
    // 用户列表页
    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }
    
    // 用户详情页
    @GetMapping("/users/{id}")
    public String userDetail(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("用户不存在: " + id));
        model.addAttribute("user", user);
        return "user-detail";
    }
}
```

### 第七阶段：JSP前端集成

#### 7.1 JSP配置
```java
// WebConfig.java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }
}
```

#### 7.2 JSP页面
```jsp
<!-- index.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JPA学习项目</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .container { max-width: 800px; margin: 0 auto; }
        .header { background: #f0f0f0; padding: 20px; border-radius: 5px; }
        .nav { margin: 20px 0; }
        .nav a { margin-right: 20px; text-decoration: none; color: #007bff; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>JPA学习项目</h1>
            <p>${message}</p>
        </div>
        
        <div class="nav">
            <a href="/users">用户管理</a>
            <a href="/api/users">API接口</a>
        </div>
        
        <div class="content">
            <h2>项目功能</h2>
            <ul>
                <li>用户管理（CRUD操作）</li>
                <li>部门管理</li>
                <li>订单管理</li>
                <li>JPA关系映射演示</li>
                <li>继承映射演示</li>
            </ul>
        </div>
    </div>
</body>
</html>
```

```jsp
<!-- users.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户列表</title>
    <style>
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .action { margin-right: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>用户列表</h1>
        <a href="/" class="action">返回首页</a>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>用户名</th>
                    <th>邮箱</th>
                    <th>年龄</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.age}</td>
                        <td>${user.createdAt}</td>
                        <td>
                            <a href="/users/${user.id}" class="action">查看</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
```

### 第八阶段：测试开发

#### 8.1 单元测试
```java
// UserTest.java
@ExtendWith(MockitoExtension.class)
class UserTest {
    
    @Test
    void testUserCreation() {
        User user = User.builder()
            .username("testuser")
            .email("test@example.com")
            .age(25)
            .build();
        
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(25, user.getAge());
    }
    
    @Test
    void testUserValidation() {
        User user = new User();
        
        // 测试空用户名
        assertThrows(Exception.class, () -> {
            user.setUsername(null);
        });
    }
}
```

#### 8.2 集成测试
```java
// UserRepositoryTest.java
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void testSaveAndFindUser() {
        // 创建用户
        User user = User.builder()
            .username("testuser")
            .email("test@example.com")
            .age(25)
            .build();
        
        // 保存用户
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        
        // 查找用户
        Optional<User> foundUser = userRepository.findByUsername("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }
    
    @Test
    void testFindByAge() {
        // 创建测试用户
        User user1 = User.builder().username("user1").email("user1@test.com").age(25).build();
        User user2 = User.builder().username("user2").email("user2@test.com").age(30).build();
        User user3 = User.builder().username("user3").email("user3@test.com").age(25).build();
        
        userRepository.saveAll(Arrays.asList(user1, user2, user3));
        
        // 测试按年龄查找
        List<User> users25 = userRepository.findByAge(25);
        assertEquals(2, users25.size());
        
        List<User> users30 = userRepository.findByAge(30);
        assertEquals(1, users30.size());
    }
}
```

### 第九阶段：数据库初始化

#### 9.1 数据库脚本
```sql
-- init_database.sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS jpa_learning 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE jpa_learning;

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    age INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建部门表
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

-- 创建用户部门关联表
CREATE TABLE IF NOT EXISTS user_departments (
    user_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, department_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE
);

-- 创建订单表
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT,
    total_amount DECIMAL(10,2),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- 创建订单项表
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- 创建人员表（继承映射）
CREATE TABLE IF NOT EXISTS persons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    person_type VARCHAR(20) NOT NULL,
    employee_id VARCHAR(50) UNIQUE,
    salary DECIMAL(10,2),
    customer_code VARCHAR(50) UNIQUE,
    credit_limit DECIMAL(10,2)
);
```

#### 9.2 测试数据
```sql
-- insert_test_users.sql
INSERT INTO users (username, email, age) VALUES
('张三', 'zhangsan@example.com', 25),
('李四', 'lisi@example.com', 30),
('王五', 'wangwu@example.com', 28),
('赵六', 'zhaoliu@example.com', 35),
('钱七', 'qianqi@example.com', 22);

INSERT INTO departments (name, description) VALUES
('技术部', '负责技术开发和维护'),
('市场部', '负责市场推广和销售'),
('人事部', '负责人力资源管理'),
('财务部', '负责财务管理');

INSERT INTO user_departments (user_id, department_id) VALUES
(1, 1), (1, 2),  -- 张三属于技术部和市场部
(2, 1),          -- 李四属于技术部
(3, 2),          -- 王五属于市场部
(4, 3),          -- 赵六属于人事部
(5, 4);          -- 钱七属于财务部
```

### 第十阶段：项目启动和部署

#### 10.1 启动项目
```bash
# 1. 编译项目
mvn clean compile

# 2. 运行测试
mvn test

# 3. 启动应用
mvn spring-boot:run

# 或者使用IDE直接运行JpaLearningApplication类
```

#### 10.2 验证启动
```bash
# 检查应用是否正常启动
curl http://localhost:8080

# 检查API接口
curl http://localhost:8080/api/users

# 检查数据库连接
mysql -u root -p jpa_learning -e "SELECT COUNT(*) FROM users;"
```

#### 10.3 端口管理
```bash
# 检查端口占用
netstat -ano | findstr :8080

# 释放端口（Windows）
taskkill /PID <进程ID> /F

# 释放端口（Linux/Mac）
kill -9 <进程ID>
```

### 第十一阶段：版本控制和部署

#### 11.1 Git版本控制
```bash
# 初始化Git仓库
git init

# 添加文件
git add .

# 提交代码
git commit -m "Initial commit: JPA学习项目"

# 创建GitHub仓库并推送
git remote add origin https://github.com/username/jpa-learning.git
git branch -M main
git push -u origin main
```

#### 11.2 生产环境配置
```yaml
# application-prod.yml
spring:
  datasource:
    url: jdbc:mysql://production-db:3306/jpa_learning
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  
  jpa:
    hibernate:
      ddl-auto: validate  # 生产环境使用validate
    show-sql: false       # 生产环境关闭SQL日志

server:
  port: 8080

logging:
  level:
    com.example.jpa: INFO
    org.hibernate.SQL: WARN
```

## 🔄 数据流程

### 1. 请求流程
```
客户端请求 → Controller → Service → Repository → 数据库
数据库响应 → Repository → Service → Controller → 客户端响应
```

### 2. 数据持久化流程
```
实体对象 → JPA/Hibernate → SQL语句 → MySQL数据库
数据库记录 → JPA/Hibernate → 实体对象 → 业务逻辑
```

### 3. 事务管理流程
```
@Transactional开始 → 业务操作 → 提交/回滚 → 事务结束
```

## 🎯 核心功能流程

### 1. 用户管理流程
```
创建用户 → 验证数据 → 保存到数据库 → 返回用户信息
查询用户 → 从数据库获取 → 返回用户列表
更新用户 → 验证存在性 → 更新数据 → 保存到数据库
删除用户 → 验证存在性 → 删除记录 → 返回成功状态
```

### 2. 关系映射流程
```
用户创建 → 关联部门 → 级联保存 → 维护关联关系
部门查询 → 加载用户 → 延迟加载 → 返回完整数据
```

### 3. 继承映射流程
```
人员创建 → 选择类型 → 保存到单表 → 使用鉴别器区分
人员查询 → 根据类型 → 返回对应实体 → 多态处理
```

## 🧪 测试流程

### 1. 单元测试流程
```
编写测试用例 → Mock依赖 → 执行测试 → 验证结果
```

### 2. 集成测试流程
```
配置测试环境 → 准备测试数据 → 执行测试 → 清理数据
```

### 3. 端到端测试流程
```
启动应用 → 发送HTTP请求 → 验证响应 → 检查数据库状态
```

## 🚀 部署流程

### 1. 开发环境
```
本地开发 → 单元测试 → 集成测试 → 本地验证
```

### 2. 测试环境
```
代码提交 → 自动构建 → 部署测试环境 → 功能测试
```

### 3. 生产环境
```
代码审查 → 生产构建 → 部署生产环境 → 监控验证
```

## 📊 监控和维护

### 1. 应用监控
- 应用性能监控（APM）
- 数据库连接池监控
- JVM内存和GC监控
- 请求响应时间监控

### 2. 日志管理
- 应用日志收集
- 错误日志告警
- 访问日志分析
- 性能日志分析

### 3. 数据库维护
- 定期备份
- 索引优化
- 查询性能优化
- 数据清理

## 🔧 故障排查

### 1. 常见问题
- 端口占用：使用`netstat`和`taskkill`命令
- 数据库连接失败：检查配置和网络
- JPA映射错误：检查实体注解和数据库表结构
- 事务问题：检查事务边界和传播行为

### 2. 性能优化
- 使用分页查询避免大数据量
- 配置合适的连接池大小
- 优化JPA查询减少N+1问题
- 使用缓存减少数据库访问

### 3. 安全考虑
- 输入验证和SQL注入防护
- 敏感数据加密
- 访问权限控制
- 日志安全处理

这个完整的项目流程文档涵盖了从项目初始化到部署维护的全过程，为JPA学习项目提供了详细的操作指南和最佳实践。 