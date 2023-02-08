package com.makhnyov.robot.model;

import com.makhnyov.robot.dto.PositionDto;

import java.util.List;

public record Route(List<PositionDto> route, Boolean circular) {

}