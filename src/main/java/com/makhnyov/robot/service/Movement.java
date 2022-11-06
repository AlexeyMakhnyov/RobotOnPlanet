package com.makhnyov.robot.service;

import com.makhnyov.robot.model.Command;
import com.makhnyov.robot.model.Position;
import org.springframework.stereotype.Service;

import com.makhnyov.robot.model.Direction;

import java.security.InvalidParameterException;
import java.util.Objects;

@Service
public class Movement {
    private final Position startPosition;

    public Movement() {
        startPosition = new Position(0L, 0L, Direction.NORTH);
    }

    // изменение координат робота при различных направлениях
    public Position move(Position position) {
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
    public Position turn(Position position, Command command) {
        switch (command) {
            case LEFT:
                switch (position.getDirection()) {
                    case NORTH:
                        position.setDirection(Direction.WEST);
                        return position;
                    case SOUTH:
                        position.setDirection(Direction.EAST);
                        return position;
                    case EAST:
                        position.setDirection(Direction.NORTH);
                        return position;
                    case WEST:
                        position.setDirection(Direction.SOUTH);
                        return position;
                    default:
                        return position;
                }
            case RIGHT:
                switch (position.getDirection()) {
                    case NORTH:
                        position.setDirection(Direction.EAST);
                        return position;
                    case SOUTH:
                        position.setDirection(Direction.WEST);
                        return position;
                    case EAST:
                        position.setDirection(Direction.SOUTH);
                        return position;
                    case WEST:
                        position.setDirection(Direction.NORTH);
                        return position;
                    default:
                        return position;
                }
            default:
                throw new InvalidParameterException("Invalid command! Possible options L (left) or R (right)!");
        }
    }

    // определение замкнутости траектории
    public Boolean isCircular(Position position, Command command) {
        return Objects.equals(position.getX(), startPosition.getX())
                && Objects.equals(position.getY(), startPosition.getY()) || command == Command.LEFT || command == Command.RIGHT;
    }
}
