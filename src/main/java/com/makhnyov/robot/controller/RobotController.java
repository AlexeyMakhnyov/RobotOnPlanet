package com.makhnyov.robot.controller;

import com.makhnyov.robot.dto.PositionDto;
import com.makhnyov.robot.model.*;
import com.makhnyov.robot.service.CommandService;
import com.makhnyov.robot.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/robot")
public class RobotController {
    private final PositionService positionService;
    private final CommandService commandService;

    @Autowired
    public RobotController(PositionService positionService, CommandService commandService) {
        this.positionService = positionService;
        this.commandService = commandService;
    }

    @GetMapping("/position")
    public PositionDto getCurrentPosition() {
        return positionService.getCurrentPosition();
    }

    @GetMapping("/reset")
    public void reset() {
        positionService.reset();
    }

    @PostMapping("/{command}")
    public ResponseEntity<String> executeCommand(@PathVariable String command) {
        Command cmd = Command.fromString(command);
        switch (cmd) {
            case GO, LEFT, RIGHT ->
                    positionService.executeCommand(cmd);
            default -> {
                return new ResponseEntity<>("Invalid command! Possible options L (left) or R (right) or G (go)!", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("The robot executed the command " + cmd.getCommand() + ".", HttpStatus.OK);
    }

    @GetMapping("/route")
    public Route getRoute() {
       return positionService.getRoute();
    }

    @GetMapping("/commands")
    public List<Command> getCommands() {
        return commandService.getCommands();
    }

}
