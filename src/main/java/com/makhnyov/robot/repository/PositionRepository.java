package com.makhnyov.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.makhnyov.robot.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {

}
