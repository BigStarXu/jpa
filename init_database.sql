-- 完整的数据库初始化脚本
-- 包含表结构创建和初始数据插入

-- 创建数据库
CREATE DATABASE IF NOT EXISTS jpa_learning 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE jpa_learning;

-- ==================== 创建表结构 ====================

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    age INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建部门表
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建用户部门关联表（多对多关系）
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
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- 创建订单项表
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    order_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- 创建人员表（继承映射）
CREATE TABLE IF NOT EXISTS persons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    person_type VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    -- 员工特有字段
    employee_id VARCHAR(50),
    salary DECIMAL(10,2),
    hire_date DATE,
    -- 客户特有字段
    customer_level VARCHAR(20),
    total_purchases DECIMAL(10,2) DEFAULT 0.00
);

-- ==================== 插入初始数据 ====================

-- 初始化用户数据
INSERT INTO users (id, username, email, age, created_at, updated_at) VALUES
(1, '张三', 'zhangsan@example.com', 25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '李四', 'lisi@example.com', 30, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '王五', 'wangwu@example.com', 28, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 初始化部门数据
INSERT INTO departments (id, name, description, created_at, updated_at) VALUES
(1, '技术部', '负责产品开发和技术创新', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '市场部', '负责市场推广和品牌建设', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '人事部', '负责人力资源管理', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 初始化用户部门关联数据
INSERT INTO user_departments (user_id, department_id) VALUES
(1, 1), -- 张三在技术部
(2, 2), -- 李四在市场部
(3, 1), -- 王五在技术部
(3, 2); -- 王五也在市场部（多对多关系）

-- ==================== 验证数据 ====================

-- 显示所有表
SHOW TABLES;

-- 查询用户数据
SELECT '用户数据' as info;
SELECT * FROM users;

-- 查询部门数据
SELECT '部门数据' as info;
SELECT * FROM departments;

-- 查询用户部门关联
SELECT '用户部门关联' as info;
SELECT u.username, d.name as department_name 
FROM users u 
JOIN user_departments ud ON u.id = ud.user_id 
JOIN departments d ON ud.department_id = d.id; 