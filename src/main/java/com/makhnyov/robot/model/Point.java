package com.makhnyov.robot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "route")
@NoArgsConstructor
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long x;
    private Long y;

    public Point(Long x, Long y) {
        this.x = x;
        this.y = y;
    }
}
