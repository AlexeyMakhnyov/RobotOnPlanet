package com.makhnyov.robot.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "current_position")
@Data
@NoArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long x;
    private Long y;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    public Position(Long x, Long y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

}
