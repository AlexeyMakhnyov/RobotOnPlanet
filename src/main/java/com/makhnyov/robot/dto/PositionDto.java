package com.makhnyov.robot.dto;

import com.makhnyov.robot.model.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
    private long x;
    private long y;
    private Direction direction;
}
