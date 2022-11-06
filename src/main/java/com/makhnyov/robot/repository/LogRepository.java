package com.makhnyov.robot.repository;

import com.makhnyov.robot.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    Log findFirstByOrderByIdDesc();
}
