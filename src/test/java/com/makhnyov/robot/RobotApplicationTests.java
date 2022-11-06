package com.makhnyov.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.makhnyov.robot.model.Command;
import com.makhnyov.robot.model.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.makhnyov.robot.model.Direction;
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


	// проверка изменения координат робота при передвижении в различных направлениях
	@Test
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


	// проверка изменения направления робота в различных ситуациях при повороте
	// влево/вправо
	@Test
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

		northTurnLeft = movement.turn(northTurnLeft, Command.LEFT);
		northTurnRight = movement.turn(northTurnRight, Command.RIGHT);

		southTurnLeft = movement.turn(southTurnLeft, Command.LEFT);
		southTurnRight = movement.turn(southTurnRight, Command.RIGHT);

		westTurnLeft = movement.turn(westTurnLeft, Command.LEFT);
		westTurnRight = movement.turn(westTurnRight, Command.RIGHT);

		eastTurnLeft = movement.turn(eastTurnLeft, Command.LEFT);
		eastTurnRight = movement.turn(eastTurnRight, Command.RIGHT);

		assertEquals(expectedNorthTurnLeft, northTurnLeft);
		assertEquals(expectedNorthTurnRight, northTurnRight);

		assertEquals(expectedSouthTurnLeft, southTurnLeft);
		assertEquals(expectedSouthTurnRight, southTurnRight);

		assertEquals(expectedWestTurnLeft, westTurnLeft);
		assertEquals(expectedWestTurnRight, westTurnRight);

		assertEquals(expectedEastTurnLeft, eastTurnLeft);
		assertEquals(expectedEastTurnRight, eastTurnRight);
	}


	// проверка цикличной траектории при ситуации, когда робот двигается и не
	// двигается
	@Test
	void circularPosition() {
		Position position = new Position(0L, 0L, Direction.NORTH);

		position = movement.move(position);
		position = movement.move(position);
		position = movement.turn(position, Command.LEFT);
		position = movement.turn(position, Command.LEFT);
		position = movement.move(position);
		position = movement.move(position);

		Position turnDirection = movement.turn(position, Command.LEFT);

		assertEquals(true, movement.isCircular(position, Command.GO));

		assertEquals(true, movement.isCircular(turnDirection, Command.LEFT));
	}

	@Test
	// проверка нецикличной траектории
	void nonCircularPosition() {
		Movement movement = new Movement();
		Position position = new Position(0L, 0L, Direction.NORTH);

		position = movement.move(position);
		position = movement.move(position);

		assertEquals(false, movement.isCircular(position, Command.GO));
	}



}
