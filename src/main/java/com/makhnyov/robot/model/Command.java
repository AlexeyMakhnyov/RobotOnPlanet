package com.makhnyov.robot.model;

import lombok.Getter;

@Getter
public enum Command {
    GO("G"), LEFT("L"), RIGHT("R");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command fromString(String command) {
        for (Command c : Command.values()) {
            if (c.command.equalsIgnoreCase(command)) {
                return c;
            }
        }
        return null;
    }


}
