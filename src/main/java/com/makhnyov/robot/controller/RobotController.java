package com.makhnyov.robot.controller;

import java.util.Objects;

import com.makhnyov.robot.model.*;
import com.makhnyov.robot.repository.LogRepository;
import com.makhnyov.robot.repository.PositionRepository;
import com.makhnyov.robot.repository.RouteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makhnyov.robot.service.Movement;

@RestController
@RequestMapping("/robot")
public class RobotController {

    private final Movement movement;
    private final PositionRepository positionRepository;
    private final RouteRepository routeRepository;
    private final LogRepository logRepository;

    public RobotController(Movement movement, PositionRepository positionRepository, RouteRepository routeRepository, LogRepository logRepository) {
        this.movement = movement;
        this.positionRepository = positionRepository;
        this.routeRepository = routeRepository;
        this.logRepository = logRepository;
    }


    @GetMapping("/position")
    public Position getCurrentPosition() {
        return positionRepository.findFirstByOrderByIdDesc();
    }

    @PostMapping("/reset")
    public void resetCurrentPosition() {
        routeRepository.deleteAll();
        routeRepository.save(new Point(0L, 0L));
        Position position = positionRepository.findFirstByOrderByIdDesc();
        position.setX(0L);
        position.setY(0L);
        position.setDirection(Direction.NORTH);
        positionRepository.save(position);
        logRepository.deleteAll();
    }

    @PostMapping("/{command}")
    public ResponseEntity<String> executeCommand(@PathVariable String command) {
        Position position = positionRepository.findFirstByOrderByIdDesc();
        Command cmd = Command.fromString(command);
        switch (cmd) {
            case GO, LEFT, RIGHT ->
                    executeCommand(cmd, position);
            default -> {
                return new ResponseEntity<>("Invalid command! Possible options L (left) or R (right) or G (go)!", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("The robot executed the command " + cmd.getCommand() + ".", HttpStatus.OK);
    }

    private void executeCommand(Command command, Position position) {
        if (command == Command.GO) {
            position = movement.move(position);
            positionRepository.save(position);
            routeRepository.save(new Point(position.getX(), position.getY()));
        } else {
            position = movement.turn(position, command);
        }
        positionRepository.save(position);
        logRepository.save(new Log(command));
    }

    @GetMapping("/route")
    public Route getRoute() {
        long count = logRepository.count();
        if (count == 0)
            return null;
        else
            return new Route(routeRepository.findAll(),
                    movement.isCircular(positionRepository.findFirstByOrderByIdDesc(),
                           logRepository.findFirstByOrderByIdDesc().getCommand()));
    }

    @GetMapping("/commands")
    public Iterable<Log> getCommands() {
        return logRepository.findAll();
    }

}
