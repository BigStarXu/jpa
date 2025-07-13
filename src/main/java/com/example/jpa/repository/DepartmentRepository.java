package com.example.jpa.repository;

import com.example.jpa.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 部门仓库接口
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    /**
     * 根据部门名称查找部门
     */
    Optional<Department> findByName(String name);
    
    /**
     * 根据部门名称模糊查询
     */
    List<Department> findByNameContainingIgnoreCase(String name);
    
    /**
     * 根据描述模糊查询
     */
    List<Department> findByDescriptionContainingIgnoreCase(String description);
    
    /**
     * 使用JPQL查询部门及其用户数量
     */
    @Query("SELECT d, COUNT(u) FROM Department d LEFT JOIN d.users u GROUP BY d")
    List<Object[]> findDepartmentsWithUserCount();
    
    /**
     * 使用JPQL查询指定用户的部门
     */
    @Query("SELECT d FROM Department d JOIN d.users u WHERE u.id = :userId")
    List<Department> findDepartmentsByUserId(@Param("userId") Long userId);
} 