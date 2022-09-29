package com.makhnyov.robot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makhnyov.robot.entity.Command;
import com.makhnyov.robot.entity.Direction;
import com.makhnyov.robot.entity.Points;
import com.makhnyov.robot.entity.Position;
import com.makhnyov.robot.model.Route;
import com.makhnyov.robot.repository.CommandRepository;
import com.makhnyov.robot.repository.PointRepository;
import com.makhnyov.robot.repository.PositionRepository;
import com.makhnyov.robot.service.Movement;


@RestController
@RequestMapping("/robot")
public class RobotController {

    private final Movement movement;
    PositionRepository positionRepository;
    PointRepository pointRepository;
    CommandRepository commandRepository;

    public RobotController(Movement movement, PositionRepository positionRepository,
            PointRepository pointRepository, CommandRepository commandRepository) {
        this.movement = movement;
        this.positionRepository = positionRepository;
        this.pointRepository = pointRepository;
        this.commandRepository = commandRepository;
    }

    @GetMapping("/position")
    public Position getCurrebtPosition() {
        return positionRepository.findById(1L).get();
    }

    @PostMapping("/reset")
    public void resetCurrentPosition() {
        pointRepository.deleteAll();
        pointRepository.save(new Points(0L, 0L));
        Position position = positionRepository.findById(1L).get();
        position.setX(0L);
        position.setY(0L);
        position.setDirection(Direction.NORTH);;
        positionRepository.save(position);
        commandRepository.deleteAll();
    }

    @PostMapping("/{command}")
    public void executeCommand(@PathVariable String command) {
        if (command.equals("G")) {
            Position position = positionRepository.findById(1L).get();
            position = movement.move(position);
            pointRepository.save(new Points(position.getX(), position.getY()));
            positionRepository.save(position);
            commandRepository.save(new Command(command));
        } else if (command.equals("L") || command.equals("R")) {
            Position position = positionRepository.getReferenceById(1L);
            position = movement.turn(position, command);
            positionRepository.save(position);
            commandRepository.save(new Command(command));
        }
    }

    @GetMapping("/route")
    public Route getRoute() {
        long count = commandRepository.count();
        if (count == 0)
            return null;
        else
            return new Route(pointRepository.findAll(),
                movement.isCircular(positionRepository.findById(1L).get(),
                        commandRepository.findFirstByOrderByIdDesc().getCommand()));
    }

    @GetMapping("/commands")
    public Iterable<Command> getCommands() {
        return commandRepository.findAll();
    }

}
