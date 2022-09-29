package com.makhnyov.robot.model;

import java.util.List;

import lombok.Data;

@Data
public class Route {
    
    private final List<Point> route;
    private final Boolean circuldar;

}
