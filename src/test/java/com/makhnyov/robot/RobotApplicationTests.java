package com.makhnyov.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.makhnyov.robot.entity.Direction;
import com.makhnyov.robot.entity.Position;
import com.makhnyov.robot.service.Movement;

@SpringBootTest
class RobotApplicationTests {

	private final Movement movement;

	@Test
	void contextLoads() {
	}

	@Autowired
	public RobotApplicationTests(Movement movement) {
		this.movement = movement;
	}

	@Test
	// проверка изменения координат робота при передвижении в различных направлениях
	void move() {
		Position positionN = new Position(0L, 0L, Direction.NORTH);
		Position expectedN = new Position(0L, 1L, Direction.NORTH);
		Position positionS = new Position(0L, 0L, Direction.SOUTH);
		Position expectedS = new Position(0L, -1L, Direction.SOUTH);
		Position positionW = new Position(0L, 0L, Direction.WEST);
		Position expectedW = new Position(-1L, 0L, Direction.WEST);
		Position positionE = new Position(0L, 0L, Direction.EAST);
		Position expectedE = new Position(1L, 0L, Direction.EAST);

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
	// проверка изменения направления робота в различных ситуациях при повороте
	// влево/вправо
	void turn() {

		Position northTurnLeft = new Position(0L, 0L, Direction.NORTH);
		Position expectedNorthTurnLeft = new Position(0L, 0L, Direction.WEST);
		Position northTurnRight = new Position(0L, 0L, Direction.NORTH);
		Position expectedNorthTurnRight = new Position(0L, 0L, Direction.EAST);

		Position southTurnLeft = new Position(0L, 0L, Direction.SOUTH);
		Position expectedSouthTurnLeft = new Position(0L, 0L, Direction.EAST);
		Position southTurnRight = new Position(0L, 0L, Direction.SOUTH);
		Position expectedSouthTurnRight = new Position(0L, 0L, Direction.WEST);

		Position westTurnLeft = new Position(0L, 0L, Direction.WEST);
		Position expectedWestTurnLeft = new Position(0L, 0L, Direction.SOUTH);
		Position westTurnRight = new Position(0L, 0L, Direction.WEST);
		Position expectedWestTurnRight = new Position(0L, 0L, Direction.NORTH);

		Position eastTurnLeft = new Position(0L, 0L, Direction.EAST);
		Position expectedEastTurnLeft = new Position(0L, 0L, Direction.NORTH);
		Position eastTurnRight = new Position(0L, 0L, Direction.EAST);
		Position expectedEastTurnRight = new Position(0L, 0L, Direction.SOUTH);

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
	// проверка цикличной траектории при ситуации, когда робот двигается и не
	// двигается
	void circularPosition() {
		Position position = new Position(0L, 0L, Direction.NORTH);
		Position turnDirection = new Position(0L, 0L, Direction.NORTH);

		position = movement.move(position);
		position = movement.move(position);
		position = movement.turn(position, "L");
		position = movement.turn(position, "L");
		position = movement.move(position);
		position = movement.move(position);

		turnDirection = movement.turn(position, "L");

		assertEquals(true, movement.isCircular(position, "G"));

		assertEquals(true, movement.isCircular(turnDirection, "L"));
	}

	@Test
	// проверка нецикличной траектории
	void nonCircularPosition() {
		Movement movement = new Movement();
		Position position = new Position(0L, 0L, Direction.NORTH);

		position = movement.move(position);
		position = movement.move(position);

		assertEquals(false, movement.isCircular(position, "G"));
	}

}
