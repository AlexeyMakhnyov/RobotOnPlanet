package com.makhnyov.robot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.makhnyov.robot.entity.Command;

public interface CommandRepository extends JpaRepository<Command, Long> {
    Command findFirstByOrderByIdDesc();
}
