package com.example.jpa.controller;

import com.example.jpa.demo.JpaDemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 演示控制器
 * 提供运行JPA演示的接口
 */
@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
@Slf4j
public class DemoController {
    
    private final JpaDemoService jpaDemoService;
    
    /**
     * 运行所有JPA演示
     */
    @PostMapping("/run-all")
    public ResponseEntity<Map<String, String>> runAllDemos() {
        log.info("开始运行所有JPA演示");
        
        try {
            jpaDemoService.runAllDemos();
            return ResponseEntity.ok(Map.of("message", "所有JPA演示运行完成，请查看控制台日志"));
        } catch (Exception e) {
            log.error("运行JPA演示失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", "运行演示失败: " + e.getMessage()));
        }
    }
    
    /**
     * 运行基本CRUD演示
     */
    @PostMapping("/basic-crud")
    public ResponseEntity<Map<String, String>> runBasicCrudDemo() {
        log.info("运行基本CRUD演示");
        
        try {
            jpaDemoService.demonstrateBasicCrud();
            return ResponseEntity.ok(Map.of("message", "基本CRUD演示运行完成"));
        } catch (Exception e) {
            log.error("运行基本CRUD演示失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", "运行演示失败: " + e.getMessage()));
        }
    }
    
    /**
     * 运行关系映射演示
     */
    @PostMapping("/relationships")
    public ResponseEntity<Map<String, String>> runRelationshipsDemo() {
        log.info("运行关系映射演示");
        
        try {
            jpaDemoService.demonstrateRelationships();
            return ResponseEntity.ok(Map.of("message", "关系映射演示运行完成"));
        } catch (Exception e) {
            log.error("运行关系映射演示失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", "运行演示失败: " + e.getMessage()));
        }
    }
    
    /**
     * 运行继承映射演示
     */
    @PostMapping("/inheritance")
    public ResponseEntity<Map<String, String>> runInheritanceDemo() {
        log.info("运行继承映射演示");
        
        try {
            jpaDemoService.demonstrateInheritance();
            return ResponseEntity.ok(Map.of("message", "继承映射演示运行完成"));
        } catch (Exception e) {
            log.error("运行继承映射演示失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", "运行演示失败: " + e.getMessage()));
        }
    }
    
    /**
     * 运行复杂查询演示
     */
    @PostMapping("/complex-queries")
    public ResponseEntity<Map<String, String>> runComplexQueriesDemo() {
        log.info("运行复杂查询演示");
        
        try {
            jpaDemoService.demonstrateComplexQueries();
            return ResponseEntity.ok(Map.of("message", "复杂查询演示运行完成"));
        } catch (Exception e) {
            log.error("运行复杂查询演示失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", "运行演示失败: " + e.getMessage()));
        }
    }
    
    /**
     * 运行订单操作演示
     */
    @PostMapping("/order-operations")
    public ResponseEntity<Map<String, String>> runOrderOperationsDemo() {
        log.info("运行订单操作演示");
        
        try {
            jpaDemoService.demonstrateOrderOperations();
            return ResponseEntity.ok(Map.of("message", "订单操作演示运行完成"));
        } catch (Exception e) {
            log.error("运行订单操作演示失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", "运行演示失败: " + e.getMessage()));
        }
    }
    
    /**
     * 运行批量操作演示
     */
    @PostMapping("/batch-operations")
    public ResponseEntity<Map<String, String>> runBatchOperationsDemo() {
        log.info("运行批量操作演示");
        
        try {
            jpaDemoService.demonstrateBatchOperations();
            return ResponseEntity.ok(Map.of("message", "批量操作演示运行完成"));
        } catch (Exception e) {
            log.error("运行批量操作演示失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", "运行演示失败: " + e.getMessage()));
        }
    }
    
    /**
     * 运行事务操作演示
     */
    @PostMapping("/transaction-operations")
    public ResponseEntity<Map<String, String>> runTransactionOperationsDemo() {
        log.info("运行事务操作演示");
        
        try {
            jpaDemoService.demonstrateTransactionOperations();
            return ResponseEntity.ok(Map.of("message", "事务操作演示运行完成"));
        } catch (Exception e) {
            log.error("运行事务操作演示失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", "运行演示失败: " + e.getMessage()));
        }
    }
    
    /**
     * 运行投影查询演示
     */
    @PostMapping("/projection-queries")
    public ResponseEntity<Map<String, String>> runProjectionQueriesDemo() {
        log.info("运行投影查询演示");
        
        try {
            jpaDemoService.demonstrateProjectionQueries();
            return ResponseEntity.ok(Map.of("message", "投影查询演示运行完成"));
        } catch (Exception e) {
            log.error("运行投影查询演示失败: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(Map.of("error", "运行演示失败: " + e.getMessage()));
        }
    }
} 