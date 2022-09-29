package com.makhnyov.robot.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.makhnyov.robot.entity.Points;
import com.makhnyov.robot.entity.Position;
import com.makhnyov.robot.entity.Direction;
import com.makhnyov.robot.repository.PointRepository;
import com.makhnyov.robot.repository.PositionRepository;

@Service
public class DataLoad {

    private final PointRepository pointRepository;
    private final PositionRepository positionRepository;

    public DataLoad(PointRepository pointRepository, PositionRepository positionRepository) {
        this.pointRepository = pointRepository;
        this.positionRepository = positionRepository;
    }

    @PostConstruct
    public void loadData() {
        pointRepository.save(new Points(0L, 0L));
        positionRepository.save(new Position(0L, 0L, Direction.NORTH));
    }

}
