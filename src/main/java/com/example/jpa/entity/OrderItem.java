package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单项实体类
 * 
 * 演示的JPA概念：
 * 1. @Entity - 标记为JPA实体
 * 2. @Table - 指定数据库表名
 * 3. @Id - 主键标识
 * 4. @GeneratedValue - 主键生成策略
 * 5. @Column - 列映射配置
 * 6. @ManyToOne - 多对一关系映射
 * 7. @JoinColumn - 外键列配置
 * 8. @CreationTimestamp - 创建时间自动设置
 * 9. @UpdateTimestamp - 更新时间自动设置
 */
@Entity
@Table(name = "order_items")
@Data
@ToString(exclude = {"order"})
@EqualsAndHashCode(exclude = {"order"})
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_name", nullable = false)
    private String productName;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;
    
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // 多对一关系：多个订单项属于一个订单
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    // 计算小计
    public BigDecimal getSubtotal() {
        return price.multiply(new BigDecimal(quantity));
    }
} 