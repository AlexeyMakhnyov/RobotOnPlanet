package com.makhnyov.robot.model;

import java.util.List;

import com.makhnyov.robot.entity.Points;

import lombok.Data;

@Data
public class Route {

    private final List<Points> route;
    private final Boolean circuldar;

}
