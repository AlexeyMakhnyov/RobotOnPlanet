package com.makhnyov.robot.service;

import com.makhnyov.robot.model.Direction;
import com.makhnyov.robot.model.Point;
import com.makhnyov.robot.model.Position;
import com.makhnyov.robot.repository.PositionRepository;
import com.makhnyov.robot.repository.RouteRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DataLoad {

    private final RouteRepository routeRepository;
    private final PositionRepository positionRepository;

    public DataLoad(RouteRepository routeRepository, PositionRepository positionRepository) {
        this.routeRepository = routeRepository;
        this.positionRepository = positionRepository;
    }

    @PostConstruct
    public void loadData() {
        routeRepository.save(new Point(0L, 0L));
        positionRepository.save(new Position(0L, 0L, Direction.NORTH));
    }

}
