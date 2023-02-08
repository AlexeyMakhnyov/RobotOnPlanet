package com.makhnyov.robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.makhnyov.robot.controller.RobotController;
import com.makhnyov.robot.dto.PositionDto;
import com.makhnyov.robot.model.Command;
import com.makhnyov.robot.model.Route;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.makhnyov.robot.model.Direction;
import com.makhnyov.robot.service.Movement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class RobotApplicationTests {

	private final Movement movement;
	private final RobotController robotController;

	@Test
	void contextLoads() {
	}

	@Autowired
	public RobotApplicationTests(Movement movement, RobotController robotController) {
		this.movement = movement;
		this.robotController = robotController;
	}

	// проверка изменения координат робота при передвижении в различных направлениях
	@Test
	void move() {
		com.makhnyov.robot.dto.PositionDto positionN = new com.makhnyov.robot.dto.PositionDto(0L, 0L, Direction.NORTH);
		com.makhnyov.robot.dto.PositionDto expectedN = new com.makhnyov.robot.dto.PositionDto(0L, 1L, Direction.NORTH);
		com.makhnyov.robot.dto.PositionDto positionS = new com.makhnyov.robot.dto.PositionDto(0L, 0L, Direction.SOUTH);
		com.makhnyov.robot.dto.PositionDto expectedS = new com.makhnyov.robot.dto.PositionDto(0L, -1L, Direction.SOUTH);
		com.makhnyov.robot.dto.PositionDto positionW = new com.makhnyov.robot.dto.PositionDto(0L, 0L, Direction.WEST);
		com.makhnyov.robot.dto.PositionDto expectedW = new com.makhnyov.robot.dto.PositionDto(-1L, 0L, Direction.WEST);
		com.makhnyov.robot.dto.PositionDto positionE = new com.makhnyov.robot.dto.PositionDto(0L, 0L, Direction.EAST);
		com.makhnyov.robot.dto.PositionDto expectedE = new com.makhnyov.robot.dto.PositionDto(1L, 0L, Direction.EAST);

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
		PositionDto northTurnLeft = new PositionDto(0L, 0L, Direction.NORTH);
		PositionDto expectedNorthTurnLeft = new PositionDto(0L, 0L, Direction.WEST);
		PositionDto northTurnRight = new PositionDto(0L, 0L, Direction.NORTH);
		PositionDto expectedNorthTurnRight = new PositionDto(0L, 0L, Direction.EAST);

		PositionDto southTurnLeft = new PositionDto(0L, 0L, Direction.SOUTH);
		PositionDto expectedSouthTurnLeft = new PositionDto(0L, 0L, Direction.EAST);
		PositionDto southTurnRight = new PositionDto(0L, 0L, Direction.SOUTH);
		PositionDto expectedSouthTurnRight = new PositionDto(0L, 0L, Direction.WEST);

		PositionDto westTurnLeft = new PositionDto(0L, 0L, Direction.WEST);
		PositionDto expectedWestTurnLeft = new PositionDto(0L, 0L, Direction.SOUTH);
		PositionDto westTurnRight = new PositionDto(0L, 0L, Direction.WEST);
		PositionDto expectedWestTurnRight = new PositionDto(0L, 0L, Direction.NORTH);

		PositionDto eastTurnLeft = new PositionDto(0L, 0L, Direction.EAST);
		PositionDto expectedEastTurnLeft = new PositionDto(0L, 0L, Direction.NORTH);
		PositionDto eastTurnRight = new PositionDto(0L, 0L, Direction.EAST);
		PositionDto expectedEastTurnRight = new PositionDto(0L, 0L, Direction.SOUTH);

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
		PositionDto position = new PositionDto(0L, 0L, Direction.NORTH);

		position = movement.move(position);
		position = movement.move(position);
		position = movement.turn(position, Command.LEFT);
		position = movement.turn(position, Command.LEFT);
		position = movement.move(position);
		position = movement.move(position);

		PositionDto turnDirection = movement.turn(position, Command.LEFT);

		assertEquals(true, movement.isCircular(position, Command.GO));

		assertEquals(true, movement.isCircular(turnDirection, Command.LEFT));
	}

	// проверка нецикличной траектории
	@Test
	void nonCircularPosition() {
		Movement movement = new Movement();
		PositionDto position = new PositionDto(0L, 0L, Direction.NORTH);

		position = movement.move(position);
		position = movement.move(position);

		assertEquals(false, movement.isCircular(position, Command.GO));
	}

	// проверка rest api
	@Test
	void restApiTest() {
		PositionDto endPosition = new PositionDto(1L, 2L, Direction.EAST);
		ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>("Invalid command! Possible options L (left) or R (right) or G (go)!", HttpStatus.BAD_REQUEST);

		robotController.executeCommand("G");
		robotController.executeCommand("G");
		robotController.executeCommand("R");
		robotController.executeCommand("G");

		PositionDto currentPosition = robotController.getCurrentPosition();
		Route route = robotController.getRoute();

		ResponseEntity<String> actualResponseEntity = robotController.executeCommand("T");

		assertEquals(endPosition, currentPosition);
		assertEquals(false, route.circular());
		assertEquals(expectedResponseEntity, actualResponseEntity);
	}

}
