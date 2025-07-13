<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>部门管理 - JPA学习项目</title>
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
        .form-group input, .form-group textarea { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        .form-group textarea { height: 100px; resize: vertical; }
        .user-list { margin-top: 10px; }
        .user-tag { display: inline-block; background: #e9ecef; padding: 4px 8px; margin: 2px; border-radius: 3px; font-size: 0.9em; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>部门管理系统</h1>
            <p>JPA学习项目 - 部门管理功能演示</p>
        </div>
        
        <div class="nav">
            <a href="/">首页</a>
            <a href="/users">用户管理</a>
            <a href="/departments">部门管理</a>
            <a href="/orders">订单管理</a>
            <a href="/demo">JPA演示</a>
        </div>

        <h2>部门列表</h2>
        
        <div style="margin-bottom: 20px;">
            <button class="btn btn-success" onclick="showAddDepartmentForm()">添加部门</button>
            <button class="btn btn-warning" onclick="refreshDepartments()">刷新数据</button>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>部门名称</th>
                    <th>描述</th>
                    <th>用户数量</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="dept" items="${departments}">
                    <tr>
                        <td>${dept.id}</td>
                        <td>${dept.name}</td>
                        <td>${dept.description}</td>
                        <td>${dept.userCount}</td>
                        <td><fmt:formatDate value="${dept.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <button class="btn btn-primary" onclick="viewUsers(${dept.id})">查看用户</button>
                            <button class="btn btn-warning" onclick="editDepartment(${dept.id})">编辑</button>
                            <button class="btn btn-danger" onclick="deleteDepartment(${dept.id})">删除</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- 添加部门表单 -->
        <div id="addDepartmentForm" style="display: none; margin-top: 20px; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
            <h3>添加新部门</h3>
            <form action="/departments/add" method="post">
                <div class="form-group">
                    <label for="name">部门名称:</label>
                    <input type="text" id="name" name="name" required>
                </div>
                <div class="form-group">
                    <label for="description">部门描述:</label>
                    <textarea id="description" name="description" rows="3"></textarea>
                </div>
                <button type="submit" class="btn btn-success">保存</button>
                <button type="button" class="btn btn-warning" onclick="hideAddDepartmentForm()">取消</button>
            </form>
        </div>

        <!-- 部门用户列表 -->
        <div id="departmentUsers" style="display: none; margin-top: 20px; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
            <h3>部门用户列表</h3>
            <div id="userList"></div>
            <button class="btn btn-warning" onclick="hideDepartmentUsers()">关闭</button>
        </div>
    </div>

    <script>
        function showAddDepartmentForm() {
            document.getElementById('addDepartmentForm').style.display = 'block';
        }
        
        function hideAddDepartmentForm() {
            document.getElementById('addDepartmentForm').style.display = 'none';
        }
        
        function refreshDepartments() {
            location.reload();
        }
        
        function editDepartment(deptId) {
            alert('编辑部门功能: ' + deptId);
        }
        
        function deleteDepartment(deptId) {
            if (confirm('确定要删除这个部门吗？')) {
                alert('删除部门功能: ' + deptId);
            }
        }
        
        function viewUsers(deptId) {
            // 模拟获取部门用户数据
            const users = [
                { id: 1, username: '张三', email: 'zhangsan@example.com' },
                { id: 2, username: '李四', email: 'lisi@example.com' }
            ];
            
            let userHtml = '<div class="user-list">';
            users.forEach(user => {
                userHtml += `<span class="user-tag">${user.username} (${user.email})</span>`;
            });
            userHtml += '</div>';
            
            document.getElementById('userList').innerHTML = userHtml;
            document.getElementById('departmentUsers').style.display = 'block';
        }
        
        function hideDepartmentUsers() {
            document.getElementById('departmentUsers').style.display = 'none';
        }
    </script>
</body>
</html> 