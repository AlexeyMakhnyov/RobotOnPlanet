package com.makhnyov.robot.repository;

import com.makhnyov.robot.model.CommandLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends JpaRepository<CommandLog, Long> {
    CommandLog findFirstByOrderByIdDesc();
}
