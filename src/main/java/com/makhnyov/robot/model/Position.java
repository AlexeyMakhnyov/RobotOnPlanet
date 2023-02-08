package com.makhnyov.robot.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "position")
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long x;
    private long y;
    @Enumerated(EnumType.STRING)
    private Direction direction;

    public Position(long x, long y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
