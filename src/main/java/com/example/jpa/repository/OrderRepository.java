package com.example.jpa.repository;

import com.example.jpa.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单仓库接口
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * 根据订单号查找订单
     */
    Order findByOrderNumber(String orderNumber);
    
    /**
     * 根据用户ID查找订单
     */
    List<Order> findByUserId(Long userId);
    
    /**
     * 根据订单状态查找订单
     */
    List<Order> findByStatus(Order.OrderStatus status);
    
    /**
     * 根据总金额范围查找订单
     */
    List<Order> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
    
    /**
     * 根据创建时间范围查找订单
     */
    List<Order> findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询用户订单
     */
    Page<Order> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 分页查询指定状态的订单
     */
    Page<Order> findByStatus(Order.OrderStatus status, Pageable pageable);
    
    /**
     * 使用JPQL查询订单统计信息
     */
    @Query("SELECT COUNT(o), SUM(o.totalAmount), AVG(o.totalAmount) FROM Order o")
    Object[] getOrderStatistics();
    
    /**
     * 使用JPQL查询指定用户的订单统计
     */
    @Query("SELECT COUNT(o), SUM(o.totalAmount), AVG(o.totalAmount) FROM Order o WHERE o.user.id = :userId")
    Object[] getOrderStatisticsByUserId(@Param("userId") Long userId);
    
    /**
     * 使用JPQL查询指定时间段的订单
     */
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startTime AND :endTime")
    List<Order> findOrdersByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 使用原生SQL查询订单数量
     */
    @Query(value = "SELECT COUNT(*) FROM orders", nativeQuery = true)
    long countOrdersNative();
} 