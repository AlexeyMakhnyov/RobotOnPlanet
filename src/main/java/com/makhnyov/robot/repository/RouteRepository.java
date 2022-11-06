package com.makhnyov.robot.repository;

import com.makhnyov.robot.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Point, Long> {
}
