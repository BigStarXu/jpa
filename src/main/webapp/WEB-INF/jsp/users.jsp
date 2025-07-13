<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>用户管理 - JPA学习项目</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 1200px; margin: 0 auto; }
        .header { background: #f8f9fa; padding: 20px; border-radius: 5px; margin-bottom: 20px; }
        .nav { margin-bottom: 20px; }
        .nav a { margin-right: 15px; text-decoration: none; color: #007bff; }
        .nav a:hover { text-decoration: underline; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f2f2f2; font-weight: bold; }
        .btn { padding: 8px 16px; margin: 2px; border: none; border-radius: 4px; cursor: pointer; }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-success { background-color: #28a745; color: white; }
        .btn-danger { background-color: #dc3545; color: white; }
        .btn-warning { background-color: #ffc107; color: black; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group input { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        .stats { display: flex; gap: 20px; margin-bottom: 20px; }
        .stat-card { background: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); flex: 1; }
        .stat-number { font-size: 2em; font-weight: bold; color: #007bff; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>用户管理系统</h1>
            <p>JPA学习项目 - 用户管理功能演示</p>
        </div>
        
        <div class="nav">
            <a href="/">首页</a>
            <a href="/users">用户管理</a>
            <a href="/departments">部门管理</a>
            <a href="/orders">订单管理</a>
            <a href="/demo">JPA演示</a>
        </div>

        <div class="stats">
            <div class="stat-card">
                <div class="stat-number">${userCount}</div>
                <div>总用户数</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${avgAge}</div>
                <div>平均年龄</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">${maxAge}</div>
                <div>最大年龄</div>
            </div>
        </div>

        <h2>用户列表</h2>
        
        <div style="margin-bottom: 20px;">
            <button class="btn btn-success" onclick="showAddUserForm()">添加用户</button>
            <button class="btn btn-warning" onclick="refreshUsers()">刷新数据</button>
        </div>

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
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.email}</td>
                        <td>${user.age}</td>
                        <td><fmt:formatDate value="${user.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <button class="btn btn-primary" onclick="editUser(${user.id})">编辑</button>
                            <button class="btn btn-danger" onclick="deleteUser(${user.id})">删除</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- 添加用户表单 -->
        <div id="addUserForm" style="display: none; margin-top: 20px; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
            <h3>添加新用户</h3>
            <form action="/users/add" method="post">
                <div class="form-group">
                    <label for="username">用户名:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="email">邮箱:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="age">年龄:</label>
                    <input type="number" id="age" name="age" min="1" max="120" required>
                </div>
                <button type="submit" class="btn btn-success">保存</button>
                <button type="button" class="btn btn-warning" onclick="hideAddUserForm()">取消</button>
            </form>
        </div>
    </div>

    <script>
        function showAddUserForm() {
            document.getElementById('addUserForm').style.display = 'block';
        }
        
        function hideAddUserForm() {
            document.getElementById('addUserForm').style.display = 'none';
        }
        
        function refreshUsers() {
            location.reload();
        }
        
        function editUser(userId) {
            alert('编辑用户功能: ' + userId);
            // 这里可以添加编辑用户的逻辑
        }
        
        function deleteUser(userId) {
            if (confirm('确定要删除这个用户吗？')) {
                fetch('/api/users/' + userId, {
                    method: 'DELETE'
                }).then(response => {
                    if (response.ok) {
                        alert('用户删除成功！');
                        location.reload();
                    } else {
                        alert('删除失败！');
                    }
                });
            }
        }
    </script>
</body>
</html> 