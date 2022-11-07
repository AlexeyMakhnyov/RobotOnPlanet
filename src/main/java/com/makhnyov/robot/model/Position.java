package com.makhnyov.robot.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@Table(name = "position")
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long x;
    private long y;
    @Enumerated(EnumType.STRING)
    private Direction direction;

    public Position(long x, long y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y && direction == position.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }
}
