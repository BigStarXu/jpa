# JPA学习项目

这是一个完整的JPA（Java Persistence API）学习项目，使用Spring Boot + MySQL数据库构建，包含了JPA的所有核心概念和实际应用示例。

## 🎯 项目目标

通过这个项目，你将学习到：

1. **JPA核心概念**：实体映射、关系映射、继承映射
2. **Spring Data JPA**：Repository接口、查询方法、事务管理
3. **实际应用**：完整的CRUD操作、复杂查询、性能优化
4. **最佳实践**：代码组织、异常处理、数据验证

## 🏗️ 项目结构

```
src/main/java/com/example/jpa/
├── JpaLearningApplication.java          # 主应用类
├── config/
│   └── JpaConfig.java                   # JPA配置类
├── controller/
│   ├── UserController.java              # 用户REST API
│   └── DemoController.java              # 演示控制器
├── entity/
│   ├── User.java                        # 用户实体
│   ├── Department.java                  # 部门实体
│   ├── Order.java                       # 订单实体
│   ├── OrderItem.java                   # 订单项实体
│   ├── Person.java                      # 人员基类（继承）
│   ├── Employee.java                    # 员工实体（继承）
│   └── Customer.java                    # 客户实体（继承）
├── repository/
│   ├── UserRepository.java              # 用户仓库
│   ├── DepartmentRepository.java        # 部门仓库
│   └── OrderRepository.java             # 订单仓库
├── service/
│   └── UserService.java                 # 用户服务
└── demo/
    └── JpaDemoService.java              # JPA演示服务
```

## 🚀 快速开始

### 1. 环境要求

- Java 17+
- Maven 3.6+
- MySQL 8.0+
- IDE（推荐IntelliJ IDEA或Eclipse）

### 2. 数据库配置

#### 创建数据库
```sql
-- 在MySQL中执行以下SQL创建数据库
CREATE DATABASE IF NOT EXISTS jpa_learning 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

#### 配置数据库连接
项目已配置为使用MySQL数据库：
- 主机: localhost
- 端口: 3306
- 数据库名: jpa_learning
- 用户名: root
- 密码: (空密码)

如需修改配置，请编辑 `src/main/resources/application.yml` 文件。

### 3. 运行项目

```bash
# 克隆项目
git clone <repository-url>
cd jpa-learning

# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

### 4. 访问应用

- **应用主页**: http://localhost:8080
- **API文档**: http://localhost:8080/api/users

## 📚 JPA核心概念学习

### 1. 实体映射（Entity Mapping）

#### 基本注解
- `@Entity`: 标记为JPA实体
- `@Table`: 指定数据库表名
- `@Id`: 主键标识
- `@GeneratedValue`: 主键生成策略
- `@Column`: 列映射配置

#### 示例代码
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
}
```

### 2. 关系映射（Relationship Mapping）

#### 一对一关系
```java
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "profile_id")
private UserProfile profile;
```

#### 一对多关系
```java
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<Order> orders;
```

#### 多对一关系
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
private User user;
```

#### 多对多关系
```java
@ManyToMany
@JoinTable(
    name = "user_departments",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "department_id")
)
private Set<Department> departments;
```

### 3. 继承映射（Inheritance Mapping）

#### 单表继承策略
```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type")
public abstract class Person {
    // 公共属性
}

@Entity
@DiscriminatorValue("EMPLOYEE")
public class Employee extends Person {
    // 员工特有属性
}
```

### 4. 查询方法（Query Methods）

#### 方法名查询
```java
// 根据用户名查找
Optional<User> findByUsername(String username);

// 根据年龄范围查找
List<User> findByAgeBetween(Integer minAge, Integer maxAge);

// 模糊查询
List<User> findByUsernameContainingIgnoreCase(String username);
```

#### @Query注解
```java
@Query("SELECT u FROM User u WHERE u.age BETWEEN :minAge AND :maxAge")
List<User> findUsersByAgeRange(@Param("minAge") Integer minAge, 
                               @Param("maxAge") Integer maxAge);
```

#### 原生SQL查询
```java
@Query(value = "SELECT * FROM users WHERE age = :age", nativeQuery = true)
List<User> findUsersByAgeNative(@Param("age") Integer age);
```

### 5. 事务管理（Transaction Management）

```java
@Service
@Transactional(readOnly = true)
public class UserService {
    
    @Transactional
    public User createUser(User user) {
        // 创建用户的业务逻辑
        return userRepository.save(user);
    }
}
```

## 🔧 API接口

### 用户管理接口

#### 查询接口
- `GET /api/users` - 获取所有用户
- `GET /api/users/{id}` - 根据ID获取用户
- `GET /api/users/username/{username}` - 根据用户名获取用户
- `GET /api/users/age/{age}` - 根据年龄获取用户
- `GET /api/users/search?keyword=xxx` - 根据关键词搜索用户
- `GET /api/users/statistics` - 获取用户统计信息

#### 创建接口
- `POST /api/users` - 创建新用户
- `POST /api/users/batch` - 批量创建用户

#### 更新接口
- `PUT /api/users/{id}` - 更新用户信息
- `PUT /api/users/{id}/age?age=25` - 更新用户年龄

#### 删除接口
- `DELETE /api/users/{id}` - 删除用户
- `DELETE /api/users/age/{age}` - 根据年龄删除用户

### 演示接口

- `POST /api/demo/run-all` - 运行所有JPA演示
- `POST /api/demo/basic-crud` - 运行基本CRUD演示
- `POST /api/demo/relationships` - 运行关系映射演示
- `POST /api/demo/inheritance` - 运行继承映射演示
- `POST /api/demo/complex-queries` - 运行复杂查询演示

## 🧪 测试示例

### 1. 创建用户
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "测试用户",
    "email": "test@example.com",
    "age": 25
  }'
```

### 2. 查询用户
```bash
curl http://localhost:8080/api/users
```

### 3. 运行演示
```bash
curl -X POST http://localhost:8080/api/demo/run-all
```

## 📖 学习路径

### 初学者路径
1. 查看 `User.java` 实体类，理解基本映射
2. 运行基本CRUD演示：`POST /api/demo/basic-crud`
3. 查看 `UserRepository.java`，理解查询方法
4. 测试用户API接口

### 进阶路径
1. 学习关系映射：查看 `User.java` 和 `Department.java`
2. 运行关系映射演示：`POST /api/demo/relationships`
3. 学习继承映射：查看 `Person.java`、`Employee.java`、`Customer.java`
4. 运行继承映射演示：`POST /api/demo/inheritance`

### 高级路径
1. 学习复杂查询：查看 `UserRepository.java` 中的@Query方法
2. 运行复杂查询演示：`POST /api/demo/complex-queries`
3. 学习事务管理：查看 `UserService.java`
4. 学习性能优化：查看分页查询和投影查询

## 🔍 数据库查看

启动应用后，使用MySQL客户端工具（如MySQL Workbench、Navicat等）连接数据库查看：

- **数据库名**: `jpa_learning`
- **用户表**: `users`
- **部门表**: `departments`
- **用户部门关联表**: `user_departments`
- **订单表**: `orders`
- **订单项表**: `order_items`
- **人员表**: `persons`（包含员工和客户）

## 📝 重要概念

### 1. 实体生命周期
- **Transient**: 新建状态，未与EntityManager关联
- **Managed**: 托管状态，与EntityManager关联
- **Detached**: 游离状态，与EntityManager断开关联
- **Removed**: 删除状态，标记为删除

### 2. 级联操作
- `CascadeType.PERSIST`: 级联保存
- `CascadeType.MERGE`: 级联更新
- `CascadeType.REMOVE`: 级联删除
- `CascadeType.ALL`: 所有级联操作

### 3. 加载策略
- `FetchType.EAGER`: 立即加载
- `FetchType.LAZY`: 延迟加载

### 4. 事务传播
- `REQUIRED`: 默认传播行为，如果存在事务则加入，否则创建新事务
- `REQUIRES_NEW`: 总是创建新事务
- `SUPPORTS`: 如果存在事务则加入，否则以非事务方式执行
- `NOT_SUPPORTED`: 以非事务方式执行

## 🚨 常见问题

### 1. 循环引用问题
使用 `@ToString(exclude = "departments")` 避免循环引用导致的栈溢出。

### 2. N+1查询问题
使用 `@EntityGraph` 或 `JOIN FETCH` 优化关联查询。

### 3. 事务边界
合理设置事务边界，避免长事务影响性能。

### 4. 内存泄漏
及时清理不需要的实体对象，避免内存泄漏。

## 📚 扩展学习

1. **JPA规范文档**: https://jakarta.ee/specifications/persistence/
2. **Spring Data JPA文档**: https://spring.io/projects/spring-data-jpa
3. **Hibernate文档**: https://hibernate.org/orm/documentation/

## 🤝 贡献

欢迎提交Issue和Pull Request来改进这个学习项目！

## �� 许可证

MIT License 