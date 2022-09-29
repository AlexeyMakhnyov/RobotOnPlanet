package com.makhnyov.robot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {
    
    private Point point;
    private Direction direction;

}
