package com.makhnyov.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.makhnyov.robot.entity.Points;

public interface PointRepository extends JpaRepository<Points, Long>{
    
}
