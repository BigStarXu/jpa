<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>JPA演示 - JPA学习项目</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 1200px; margin: 0 auto; }
        .header { background: #f8f9fa; padding: 20px; border-radius: 5px; margin-bottom: 20px; }
        .nav { margin-bottom: 20px; }
        .nav a { margin-right: 15px; text-decoration: none; color: #007bff; }
        .nav a:hover { text-decoration: underline; }
        .demo-section { margin-bottom: 30px; padding: 20px; border: 1px solid #ddd; border-radius: 5px; }
        .demo-section h3 { margin-top: 0; color: #007bff; }
        .btn { padding: 10px 20px; margin: 5px; border: none; border-radius: 4px; cursor: pointer; }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-success { background-color: #28a745; color: white; }
        .btn-warning { background-color: #ffc107; color: black; }
        .btn-info { background-color: #17a2b8; color: white; }
        .result-area { margin-top: 15px; padding: 15px; background: #f8f9fa; border-radius: 4px; min-height: 100px; }
        .loading { color: #007bff; }
        .success { color: #28a745; }
        .error { color: #dc3545; }
        .code-block { background: #f4f4f4; padding: 10px; border-radius: 4px; font-family: monospace; margin: 10px 0; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>JPA功能演示</h1>
            <p>JPA学习项目 - 各种JPA功能演示和测试</p>
        </div>
        
        <div class="nav">
            <a href="/">首页</a>
            <a href="/users">用户管理</a>
            <a href="/departments">部门管理</a>
            <a href="/orders">订单管理</a>
            <a href="/demo">JPA演示</a>
        </div>

        <!-- 基本CRUD演示 -->
        <div class="demo-section">
            <h3>基本CRUD操作演示</h3>
            <p>演示基本的增删改查操作</p>
            <button class="btn btn-primary" onclick="runDemo('basic-crud')">运行基本CRUD演示</button>
            <div id="basic-crud-result" class="result-area">
                <p>点击按钮运行演示...</p>
            </div>
        </div>

        <!-- 关系映射演示 -->
        <div class="demo-section">
            <h3>关系映射演示</h3>
            <p>演示一对一、一对多、多对多关系映射</p>
            <button class="btn btn-success" onclick="runDemo('relationships')">运行关系映射演示</button>
            <div id="relationships-result" class="result-area">
                <p>点击按钮运行演示...</p>
            </div>
        </div>

        <!-- 继承映射演示 -->
        <div class="demo-section">
            <h3>继承映射演示</h3>
            <p>演示单表继承策略</p>
            <button class="btn btn-warning" onclick="runDemo('inheritance')">运行继承映射演示</button>
            <div id="inheritance-result" class="result-area">
                <p>点击按钮运行演示...</p>
            </div>
        </div>

        <!-- 复杂查询演示 -->
        <div class="demo-section">
            <h3>复杂查询演示</h3>
            <p>演示各种复杂查询方法</p>
            <button class="btn btn-info" onclick="runDemo('complex-queries')">运行复杂查询演示</button>
            <div id="complex-queries-result" class="result-area">
                <p>点击按钮运行演示...</p>
            </div>
        </div>

        <!-- 事务操作演示 -->
        <div class="demo-section">
            <h3>事务操作演示</h3>
            <p>演示事务管理和异常处理</p>
            <button class="btn btn-primary" onclick="runDemo('transaction-operations')">运行事务演示</button>
            <div id="transaction-operations-result" class="result-area">
                <p>点击按钮运行演示...</p>
            </div>
        </div>

        <!-- 批量操作演示 -->
        <div class="demo-section">
            <h3>批量操作演示</h3>
            <p>演示批量插入、更新、删除操作</p>
            <button class="btn btn-success" onclick="runDemo('batch-operations')">运行批量操作演示</button>
            <div id="batch-operations-result" class="result-area">
                <p>点击按钮运行演示...</p>
            </div>
        </div>

        <!-- 运行所有演示 -->
        <div class="demo-section">
            <h3>运行所有演示</h3>
            <p>一次性运行所有JPA功能演示</p>
            <button class="btn btn-warning" onclick="runAllDemos()">运行所有演示</button>
            <div id="all-demos-result" class="result-area">
                <p>点击按钮运行所有演示...</p>
            </div>
        </div>

        <!-- JPA学习要点 -->
        <div class="demo-section">
            <h3>JPA学习要点</h3>
            <div class="code-block">
                <strong>1. 实体映射注解：</strong><br>
                @Entity, @Table, @Id, @GeneratedValue, @Column<br><br>
                
                <strong>2. 关系映射注解：</strong><br>
                @OneToOne, @OneToMany, @ManyToOne, @ManyToMany<br><br>
                
                <strong>3. 继承映射注解：</strong><br>
                @Inheritance, @DiscriminatorColumn, @DiscriminatorValue<br><br>
                
                <strong>4. 查询方法：</strong><br>
                方法名查询, @Query注解, 原生SQL查询<br><br>
                
                <strong>5. 事务管理：</strong><br>
                @Transactional, 事务传播, 事务隔离级别
            </div>
        </div>
    </div>

    <script>
        function runDemo(demoType) {
            const resultArea = document.getElementById(demoType + '-result');
            resultArea.innerHTML = '<p class="loading">正在运行演示...</p>';
            
            fetch('/api/demo/' + demoType, {
                method: 'POST'
            })
            .then(response => response.json())
            .then(data => {
                resultArea.innerHTML = '<p class="success">演示完成！</p><pre>' + JSON.stringify(data, null, 2) + '</pre>';
            })
            .catch(error => {
                resultArea.innerHTML = '<p class="error">演示失败：' + error.message + '</p>';
            });
        }
        
        function runAllDemos() {
            const resultArea = document.getElementById('all-demos-result');
            resultArea.innerHTML = '<p class="loading">正在运行所有演示...</p>';
            
            fetch('/api/demo/run-all', {
                method: 'POST'
            })
            .then(response => response.json())
            .then(data => {
                resultArea.innerHTML = '<p class="success">所有演示完成！</p><pre>' + JSON.stringify(data, null, 2) + '</pre>';
            })
            .catch(error => {
                resultArea.innerHTML = '<p class="error">演示失败：' + error.message + '</p>';
            });
        }
    </script>
</body>
</html> 