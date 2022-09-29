package com.makhnyov.robot.service;

import org.springframework.stereotype.Service;

import com.makhnyov.robot.model.Direction;
import com.makhnyov.robot.model.Position;

@Service
public class Movement {

    private static final String LEFT = "L";
    private static final String RIGHT = "R";
    private final Position startPosition;

    //инициализация начальной точки для дальнейшего сравнения
    public Movement() {
        startPosition = new Position(0L, 0L, Direction.NORTH);
    }

    //изменение координат робота при различных направлениях
    public Position move(Position position) {
        switch (position.getDirection()) {
            case NORTH:
                position.setY(position.getY() + 1);
                return position;
            case SOUTH:
                position.setY(position.getY() - 1);
                return position;
            case EAST:
                position.setX(position.getX() + 1);
                return position;
            case WEST:
                position.setX(position.getX() - 1);
                return position;
            default:
                return startPosition;
        }
    }

    //изменение направления робота в различных ситуациях при повороте влево/вправо
    public Position turn(Position position, String side) {
        switch (side) {
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
                        return startPosition;
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
                        return startPosition;
                }
            default:
                return startPosition;
        }
    }

    //определение замкнутости траектории
    public Boolean isCircular(Position position) {
        if (position.getX() == startPosition.getX() && position.getY() == startPosition.getY())
            return true;
        else
            return false;
    }
}
