package com.makhnyov.robot.service;

import com.makhnyov.robot.model.Direction;
import com.makhnyov.robot.model.Position;
import com.makhnyov.robot.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DataLoad {
    private final PositionRepository positionRepository;

    @Autowired
    public DataLoad(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @PostConstruct
    public void loadData() {
        positionRepository.save(new Position(0L,0L, Direction.NORTH));
    }

}
