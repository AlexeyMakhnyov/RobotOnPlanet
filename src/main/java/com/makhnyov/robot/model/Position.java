package com.makhnyov.robot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {
    
    private Long x;
    private Long y;
    private Direction direction;

}
