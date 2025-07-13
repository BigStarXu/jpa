<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>订单管理 - JPA学习项目</title>
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
        .status-pending { color: #ffc107; font-weight: bold; }
        .status-confirmed { color: #17a2b8; font-weight: bold; }
        .status-shipped { color: #007bff; font-weight: bold; }
        .status-delivered { color: #28a745; font-weight: bold; }
        .status-cancelled { color: #dc3545; font-weight: bold; }
        .empty-state { text-align: center; padding: 50px; color: #6c757d; }
        .empty-state h3 { margin-bottom: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>订单管理系统</h1>
            <p>JPA学习项目 - 订单管理功能演示</p>
        </div>
        
        <div class="nav">
            <a href="/">首页</a>
            <a href="/users">用户管理</a>
            <a href="/departments">部门管理</a>
            <a href="/orders">订单管理</a>
            <a href="/demo">JPA演示</a>
        </div>

        <h2>订单列表</h2>
        
        <div style="margin-bottom: 20px;">
            <button class="btn btn-success" onclick="showAddOrderForm()">添加订单</button>
            <button class="btn btn-warning" onclick="refreshOrders()">刷新数据</button>
            <button class="btn btn-primary" onclick="runOrderDemo()">运行订单演示</button>
        </div>

        <c:choose>
            <c:when test="${empty orders}">
                <div class="empty-state">
                    <h3>暂无订单数据</h3>
                    <p>点击"运行订单演示"按钮来生成示例订单数据</p>
                    <button class="btn btn-primary" onclick="runOrderDemo()">运行订单演示</button>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>订单号</th>
                            <th>用户</th>
                            <th>总金额</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td>${order.orderNumber}</td>
                                <td>${order.user.username}</td>
                                <td>￥${order.totalAmount}</td>
                                <td>
                                    <span class="status-${order.status.toLowerCase()}">
                                        ${order.status}
                                    </span>
                                </td>
                                <td><fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>
                                    <button class="btn btn-primary" onclick="viewOrderDetails(${order.id})">查看详情</button>
                                    <button class="btn btn-warning" onclick="updateOrderStatus(${order.id})">更新状态</button>
                                    <button class="btn btn-danger" onclick="deleteOrder(${order.id})">删除</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <!-- 添加订单表单 -->
        <div id="addOrderForm" style="display: none; margin-top: 20px; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
            <h3>添加新订单</h3>
            <p>订单功能演示 - 这里可以添加订单创建逻辑</p>
            <button class="btn btn-success" onclick="createSampleOrder()">创建示例订单</button>
            <button class="btn btn-warning" onclick="hideAddOrderForm()">取消</button>
        </div>

        <!-- 订单详情 -->
        <div id="orderDetails" style="display: none; margin-top: 20px; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
            <h3>订单详情</h3>
            <div id="orderDetailsContent"></div>
            <button class="btn btn-warning" onclick="hideOrderDetails()">关闭</button>
        </div>
    </div>

    <script>
        function showAddOrderForm() {
            document.getElementById('addOrderForm').style.display = 'block';
        }
        
        function hideAddOrderForm() {
            document.getElementById('addOrderForm').style.display = 'none';
        }
        
        function refreshOrders() {
            location.reload();
        }
        
        function runOrderDemo() {
            fetch('/api/demo/order-operations', {
                method: 'POST'
            })
            .then(response => response.json())
            .then(data => {
                alert('订单演示运行完成！');
                location.reload();
            })
            .catch(error => {
                alert('运行演示失败：' + error.message);
            });
        }
        
        function createSampleOrder() {
            alert('创建示例订单功能演示');
            hideAddOrderForm();
        }
        
        function viewOrderDetails(orderId) {
            // 模拟获取订单详情
            const details = `
                <h4>订单详情</h4>
                <p><strong>订单ID:</strong> ${orderId}</p>
                <p><strong>订单号:</strong> ORD-${orderId.toString().padStart(6, '0')}</p>
                <p><strong>用户:</strong> 示例用户</p>
                <p><strong>总金额:</strong> ￥299.00</p>
                <p><strong>状态:</strong> 待确认</p>
                <p><strong>创建时间:</strong> ${new Date().toLocaleString()}</p>
                
                <h5>订单项</h5>
                <ul>
                    <li>商品A - 数量: 2 - 单价: ￥99.00</li>
                    <li>商品B - 数量: 1 - 单价: ￥101.00</li>
                </ul>
            `;
            
            document.getElementById('orderDetailsContent').innerHTML = details;
            document.getElementById('orderDetails').style.display = 'block';
        }
        
        function hideOrderDetails() {
            document.getElementById('orderDetails').style.display = 'none';
        }
        
        function updateOrderStatus(orderId) {
            const statuses = ['PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED'];
            const newStatus = statuses[Math.floor(Math.random() * statuses.length)];
            alert(`更新订单 ${orderId} 状态为: ${newStatus}`);
        }
        
        function deleteOrder(orderId) {
            if (confirm('确定要删除这个订单吗？')) {
                alert('删除订单功能演示: ' + orderId);
            }
        }
    </script>
</body>
</html> 