package com.makhnyov.robot.service;

import com.makhnyov.robot.dto.PositionDto;
import com.makhnyov.robot.model.Command;
import org.springframework.stereotype.Service;

import com.makhnyov.robot.model.Direction;

import java.security.InvalidParameterException;
import java.util.Objects;

@Service
public class Movement {
    private final PositionDto startPosition;

    public Movement() {
        startPosition = new PositionDto(0L, 0L, Direction.NORTH);
    }

    // изменение координат робота при различных направлениях
    public PositionDto move(PositionDto position) {
        switch (position.getDirection()) {
            case NORTH -> {
                position.setY(position.getY() + 1);
                return position;
            }
            case SOUTH -> {
                position.setY(position.getY() - 1);
                return position;
            }
            case EAST -> {
                position.setX(position.getX() + 1);
                return position;
            }
            case WEST -> {
                position.setX(position.getX() - 1);
                return position;
            }
            default -> throw new InvalidParameterException("Wrong direction!");
        }
    }

    // изменение направления робота в различных ситуациях при повороте влево/вправо
    public PositionDto turn(PositionDto positionDto, Command command) {
        switch (command) {
            case LEFT:
                switch (positionDto.getDirection()) {
                    case NORTH:
                        positionDto.setDirection(Direction.WEST);
                        return positionDto;
                    case SOUTH:
                        positionDto.setDirection(Direction.EAST);
                        return positionDto;
                    case EAST:
                        positionDto.setDirection(Direction.NORTH);
                        return positionDto;
                    case WEST:
                        positionDto.setDirection(Direction.SOUTH);
                        return positionDto;
                    default:
                        return positionDto;
                }
            case RIGHT:
                switch (positionDto.getDirection()) {
                    case NORTH:
                        positionDto.setDirection(Direction.EAST);
                        return positionDto;
                    case SOUTH:
                        positionDto.setDirection(Direction.WEST);
                        return positionDto;
                    case EAST:
                        positionDto.setDirection(Direction.SOUTH);
                        return positionDto;
                    case WEST:
                        positionDto.setDirection(Direction.NORTH);
                        return positionDto;
                    default:
                        return positionDto;
                }
            default:
                throw new InvalidParameterException("Invalid command! Possible options L (left) or R (right)!");
        }
    }

//    // определение замкнутости траектории
    public Boolean isCircular(PositionDto positionDto, Command command) {
        return Objects.equals(positionDto.getX(), startPosition.getX())
                && Objects.equals(positionDto.getY(), startPosition.getY()) || command == Command.LEFT || command == Command.RIGHT;
    }
}
