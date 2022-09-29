package com.makhnyov.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.makhnyov.robot.model.Direction;
import com.makhnyov.robot.model.Point;
import com.makhnyov.robot.model.Position;
import com.makhnyov.robot.service.Movement;

@SpringBootTest
class RobotApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	//проверка изменения координат робота при передвижении в различных направлениях
	void move() {
		Movement movement = new Movement();
		Position positionN = new Position(new Point(0L, 0L), Direction.NORTH);
		Position expectedN = new Position(new Point(0L, 1L), Direction.NORTH);
		Position positionS = new Position(new Point(0L, 0L), Direction.SOUTH);
		Position expectedS = new Position(new Point(0L, -1L), Direction.SOUTH);
		Position positionW = new Position(new Point(0L, 0L), Direction.WEST);
		Position expectedW = new Position(new Point(-1L, 0L), Direction.WEST);
		Position positionE = new Position(new Point(0L, 0L), Direction.EAST);
		Position expectedE = new Position(new Point(1L, 0L), Direction.EAST);

		positionN = movement.move(positionN);
		positionS = movement.move(positionS);
		positionW = movement.move(positionW);
		positionE = movement.move(positionE);

		assertEquals(expectedN, positionN);
		assertEquals(expectedS, positionS);
		assertEquals(expectedW, positionW);
		assertEquals(expectedE, positionE);
	}

	@Test
	//проверка изменения направления робота в различных ситуациях при повороте влево/вправо
	void turn() {
		Movement movement = new Movement();

		Position northTurnLeft = new Position(new Point(0L, 0L), Direction.NORTH);
		Position expectedNorthTurnLeft = new Position(new Point(0L, 0L), Direction.WEST);
		Position northTurnRight = new Position(new Point(0L, 0L), Direction.NORTH);
		Position expectedNorthTurnRight = new Position(new Point(0L, 0L), Direction.EAST);

		Position southTurnLeft = new Position(new Point(0L, 0L), Direction.SOUTH);
		Position expectedSouthTurnLeft = new Position(new Point(0L, 0L), Direction.EAST);
		Position southTurnRight = new Position(new Point(0L, 0L), Direction.SOUTH);
		Position expectedSouthTurnRight = new Position(new Point(0L, 0L), Direction.WEST);

		Position westTurnLeft = new Position(new Point(0L, 0L), Direction.WEST);
		Position expectedWestTurnLeft = new Position(new Point(0L, 0L), Direction.SOUTH);
		Position westTurnRight = new Position(new Point(0L, 0L), Direction.WEST);
		Position expectedWestTurnRight = new Position(new Point(0L, 0L), Direction.NORTH);

		Position eastTurnLeft = new Position(new Point(0L, 0L), Direction.EAST);
		Position expectedEastTurnLeft = new Position(new Point(0L, 0L), Direction.NORTH);
		Position eastTurnRight = new Position(new Point(0L, 0L), Direction.EAST);
		Position expectedEastTurnRight = new Position(new Point(0L, 0L), Direction.SOUTH);

		northTurnLeft = movement.turn(northTurnLeft, "L");
		northTurnRight = movement.turn(northTurnRight, "R");

		southTurnLeft = movement.turn(southTurnLeft, "L");
		southTurnRight = movement.turn(southTurnRight, "R");

		westTurnLeft = movement.turn(westTurnLeft, "L");
		westTurnRight = movement.turn(westTurnRight, "R");

		eastTurnLeft = movement.turn(eastTurnLeft, "L");
		eastTurnRight = movement.turn(eastTurnRight, "R");

		assertEquals(expectedNorthTurnLeft, northTurnLeft);
		assertEquals(expectedNorthTurnRight, northTurnRight);

		assertEquals(expectedSouthTurnLeft, southTurnLeft);
		assertEquals(expectedSouthTurnRight, southTurnRight);

		assertEquals(expectedWestTurnLeft, westTurnLeft);
		assertEquals(expectedWestTurnRight, westTurnRight);

		assertEquals(expectedEastTurnLeft, eastTurnLeft);
		assertEquals(expectedEastTurnRight, eastTurnRight);
	}

	@Test
	//проверка цикличной траектории при ситуации, когда робот двигается и не двигается
	void circularPosition() {
		Movement movement = new Movement();
		Position position = new Position(new Point(0L, 0L), Direction.NORTH);
		Position turnDirection = new Position(new Point(0L, 0L), Direction.NORTH);

		position = movement.move(position);
		position = movement.move(position);
		position = movement.turn(position, "L");
		position = movement.turn(position, "L");
		position = movement.move(position);
		position = movement.move(position);

		turnDirection = movement.turn(position, "L");

		assertEquals(true, movement.isCircular(position));

		assertEquals(true, movement.isCircular(turnDirection));
	}

	@Test
	//проверка нецикличной траектории
	void nonCircularPosition() {
		Movement movement = new Movement();
		Position position = new Position(new Point(0L, 0L), Direction.NORTH);

		position = movement.move(position);
		position = movement.move(position);

		assertEquals(false, movement.isCircular(position));
	}

}
