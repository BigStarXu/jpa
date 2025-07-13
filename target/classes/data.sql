-- 初始化数据脚本
-- 注意：此脚本依赖于JPA自动创建的表结构
-- 如果手动创建表，请先执行 create_tables.sql

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