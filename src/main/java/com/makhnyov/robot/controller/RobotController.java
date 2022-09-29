package com.makhnyov.robot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makhnyov.robot.model.Direction;
import com.makhnyov.robot.model.Point;
import com.makhnyov.robot.model.Position;
import com.makhnyov.robot.model.Route;
import com.makhnyov.robot.service.Movement;

@RestController
@RequestMapping("/robot")
public class RobotController {
    
    private Position position;
    private final Movement movement;
    private List<Position> route;

    public RobotController(Movement movement) {
        this.movement = movement;
        route = new ArrayList<>();
        position = new Position(new Point(0L, 0L), Direction.NORTH);
        route.add(position);
    }

    @GetMapping("/position")
    public Position getCurrebtPosition() {
        return position;
    }

    @PostMapping("/reset")
    public Position resetCurrentPosition() {
        position = new Position(new Point(0L, 0L), Direction.NORTH);
        return position;
    }

    @PostMapping("/{command}")
    public Position executeCommand(@PathVariable String command) {
        if (command.equals("G")) {
            position = movement.move(position);
            route.add(position);
        }
        else if (command.equals("L") || command.equals("R"))
            position = movement.turn(position, command);
        return position;
    }

    @GetMapping("/route")
    public Route getRoute() {
        return new Route(route.stream().map(Position::getPoint).collect(Collectors.toList()), movement.isCircular(position));
    }
    
}
