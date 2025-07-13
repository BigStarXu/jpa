<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JPA学习项目主页</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 1200px; margin: 0 auto; }
        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 40px; border-radius: 10px; margin-bottom: 30px; text-align: center; }
        .nav { background: #f8f9fa; padding: 20px; border-radius: 10px; margin-bottom: 30px; }
        .nav a { display: inline-block; margin: 10px; padding: 12px 24px; text-decoration: none; color: #007bff; background: white; border-radius: 5px; border: 1px solid #dee2e6; transition: all 0.3s; }
        .nav a:hover { background: #007bff; color: white; transform: translateY(-2px); box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        .content { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; }
        .card { background: white; padding: 25px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); border: 1px solid #dee2e6; }
        .card h3 { color: #007bff; margin-top: 0; }
        .feature-list { list-style: none; padding: 0; }
        .feature-list li { padding: 8px 0; border-bottom: 1px solid #eee; }
        .feature-list li:before { content: "✓ "; color: #28a745; font-weight: bold; }
        .api-section { background: #f8f9fa; padding: 20px; border-radius: 10px; margin-top: 30px; }
        .api-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; margin-top: 20px; }
        .api-card { background: white; padding: 20px; border-radius: 8px; border-left: 4px solid #007bff; }
        .btn { display: inline-block; padding: 10px 20px; margin: 5px; text-decoration: none; border-radius: 5px; transition: all 0.3s; }
        .btn-primary { background: #007bff; color: white; }
        .btn-success { background: #28a745; color: white; }
        .btn-warning { background: #ffc107; color: black; }
        .btn:hover { transform: translateY(-2px); box-shadow: 0 4px 8px rgba(0,0,0,0.2); }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🎓 JPA学习项目</h1>
            <p>Spring Boot + JPA + MySQL + JSP 完整学习平台</p>
            <p>版本 1.0.0 | 基于 Java 17 + Spring Boot 3.2.0</p>
        </div>
        
        <div class="nav">
            <a href="/">🏠 首页</a>
            <a href="/users">👥 用户管理</a>
            <a href="/departments">🏢 部门管理</a>
            <a href="/orders">📦 订单管理</a>
            <a href="/demo">🧪 JPA演示</a>
            <a href="/api/users">📊 API接口</a>
            <a href="/health">💚 健康检查</a>
        </div>

        <div class="content">
            <div class="card">
                <h3>🚀 项目特色</h3>
                <ul class="feature-list">
                    <li>完整的JPA实体映射演示</li>
                    <li>关系映射（一对一、一对多、多对多）</li>
                    <li>继承映射（单表继承策略）</li>
                    <li>复杂查询和事务管理</li>
                    <li>REST API + JSP页面双重展示</li>
                    <li>MySQL数据库集成</li>
                    <li>完整的CRUD操作演示</li>
                </ul>
            </div>
            
            <div class="card">
                <h3>📚 学习内容</h3>
                <ul class="feature-list">
                    <li>JPA核心概念和注解</li>
                    <li>Spring Data JPA使用</li>
                    <li>实体生命周期管理</li>
                    <li>查询方法和@Query注解</li>
                    <li>事务管理和异常处理</li>
                    <li>性能优化技巧</li>
                    <li>最佳实践和设计模式</li>
                </ul>
            </div>
        </div>

        <div class="api-section">
            <h3>🔗 快速访问</h3>
            <div class="api-grid">
                <div class="api-card">
                    <h4>用户管理</h4>
                    <p>完整的用户CRUD操作演示</p>
                    <a href="/users" class="btn btn-primary">进入用户管理</a>
                    <a href="/api/users" class="btn btn-success">查看API</a>
                </div>
                
                <div class="api-card">
                    <h4>部门管理</h4>
                    <p>部门信息和用户关联管理</p>
                    <a href="/departments" class="btn btn-primary">进入部门管理</a>
                    <a href="/api/departments" class="btn btn-success">查看API</a>
                </div>
                
                <div class="api-card">
                    <h4>JPA演示</h4>
                    <p>各种JPA功能演示和测试</p>
                    <a href="/demo" class="btn btn-warning">运行演示</a>
                    <a href="/api/demo/run-all" class="btn btn-success">API演示</a>
                </div>
                
                <div class="api-card">
                    <h4>系统状态</h4>
                    <p>应用健康检查和系统信息</p>
                    <a href="/health" class="btn btn-primary">健康检查</a>
                    <a href="/api-docs" class="btn btn-success">API文档</a>
                </div>
            </div>
        </div>

        <div class="card" style="margin-top: 30px;">
            <h3>💡 使用说明</h3>
            <p><strong>1. 数据库配置：</strong>项目使用MySQL数据库，数据库名：jpa_learning</p>
            <p><strong>2. 页面访问：</strong>点击上方导航菜单访问不同功能页面</p>
            <p><strong>3. API测试：</strong>使用Postman或浏览器访问REST API接口</p>
            <p><strong>4. JPA学习：</strong>访问JPA演示页面运行各种功能演示</p>
            <p><strong>5. 源码学习：</strong>查看项目源码了解JPA最佳实践</p>
        </div>
    </div>
</body>
</html> 