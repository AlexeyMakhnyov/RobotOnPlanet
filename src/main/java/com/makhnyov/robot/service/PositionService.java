package com.makhnyov.robot.service;

import com.makhnyov.robot.model.Command;
import com.makhnyov.robot.model.Direction;
import com.makhnyov.robot.model.Position;
import com.makhnyov.robot.model.Route;
import com.makhnyov.robot.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService {
    private final PositionRepository positionRepository;
    private final Movement movement;
    private final CommandService commandService;

    @Autowired
    public PositionService(PositionRepository positionRepository, Movement movement, CommandService commandService) {
        this.positionRepository = positionRepository;
        this.movement = movement;
        this.commandService = commandService;
    }

    public com.makhnyov.robot.dto.PositionDto getCurrentPosition() {
        com.makhnyov.robot.dto.PositionDto positionDto = new com.makhnyov.robot.dto.PositionDto();
        Position position = positionRepository.findFirstByOrderByIdDesc();
        positionDto.setX(position.getX());
        positionDto.setY(position.getY());
        positionDto.setDirection(position.getDirection());
        return positionDto;
    }

    public void executeCommand(Command command) {
        com.makhnyov.robot.dto.PositionDto positionDto = getCurrentPosition();
        if (command == Command.GO) {
            positionDto = movement.move(positionDto);
        } else {
            positionDto = movement.turn(positionDto, command);
        }
        save(positionDto);
        commandService.save(command);
    }

    public void save(com.makhnyov.robot.dto.PositionDto positionDto) {
        Position position = new Position();
        position.setX(positionDto.getX());
        position.setY(positionDto.getY());
        position.setDirection(positionDto.getDirection());
        positionRepository.save(position);
    }

    public Route getRoute() {
        long count = commandService.getCount();
        if (count == 0)
            return null;
        else {
            com.makhnyov.robot.dto.PositionDto currentPosition = getCurrentPosition();
            Command command = commandService.getLastCommand();
            List<com.makhnyov.robot.dto.PositionDto> route = getAllPosition();
            boolean isCircular = movement.isCircular(currentPosition, command);
            return new Route(route, isCircular);
        }
    }

    public List<com.makhnyov.robot.dto.PositionDto> getAllPosition() {
        return positionRepository.findAll().stream().map(position -> {
            com.makhnyov.robot.dto.PositionDto positionDto = new com.makhnyov.robot.dto.PositionDto();
            positionDto.setX(position.getX());
            positionDto.setY(position.getY());
            positionDto.setDirection(position.getDirection());
            return positionDto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void reset() {
        positionRepository.deleteAll();
        positionRepository.save(new Position(0L, 0L, Direction.NORTH));
        commandService.deleteAll();
    }
}
