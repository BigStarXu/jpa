package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单实体类
 * 
 * 演示的JPA概念：
 * 1. @Entity - 标记为JPA实体
 * 2. @Table - 指定数据库表名
 * 3. @Id - 主键标识
 * 4. @GeneratedValue - 主键生成策略
 * 5. @Column - 列映射配置
 * 6. @ManyToOne - 多对一关系映射
 * 7. @JoinColumn - 外键列配置
 * 8. @OneToMany - 一对多关系映射
 * 9. @Enumerated - 枚举映射
 * 10. @CreationTimestamp - 创建时间自动设置
 * 11. @UpdateTimestamp - 更新时间自动设置
 */
@Entity
@Table(name = "orders")
@Data
@ToString(exclude = {"user", "orderItems"})
@EqualsAndHashCode(exclude = {"user", "orderItems"})
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;
    
    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status = OrderStatus.PENDING;
    
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // 多对一关系：多个订单属于一个用户
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // 一对多关系：一个订单包含多个订单项
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    // 便利方法：添加订单项
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    
    // 便利方法：移除订单项
    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }
    
    // 计算总金额
    public void calculateTotalAmount() {
        this.totalAmount = orderItems.stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // 订单状态枚举
    public enum OrderStatus {
        PENDING("待处理"),
        CONFIRMED("已确认"),
        SHIPPED("已发货"),
        DELIVERED("已送达"),
        CANCELLED("已取消");
        
        private final String description;
        
        OrderStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
} 