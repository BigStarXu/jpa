-- 插入测试用户数据
-- 执行前请确保已连接到jpa_learning数据库

-- 清空现有数据（可选）
-- DELETE FROM users;

-- 插入测试用户
INSERT INTO users (username, email, age, created_at, updated_at) VALUES
('张三', 'zhangsan@example.com', 25, NOW(), NOW()),
('李四', 'lisi@example.com', 30, NOW(), NOW()),
('王五', 'wangwu@example.com', 28, NOW(), NOW()),
('赵六', 'zhaoliu@example.com', 35, NOW(), NOW()),
('钱七', 'qianqi@example.com', 22, NOW(), NOW()),
('孙八', 'sunba@example.com', 27, NOW(), NOW()),
('周九', 'zhoujiu@example.com', 32, NOW(), NOW()),
('吴十', 'wushi@example.com', 29, NOW(), NOW()),
('郑十一', 'zhengshiyi@example.com', 26, NOW(), NOW()),
('王十二', 'wangshier@example.com', 31, NOW(), NOW());

-- 查看插入的数据
SELECT * FROM users;

-- 查看统计信息
SELECT 
    COUNT(*) as 用户总数,
    AVG(age) as 平均年龄,
    MAX(age) as 最大年龄,
    MIN(age) as 最小年龄
FROM users; 