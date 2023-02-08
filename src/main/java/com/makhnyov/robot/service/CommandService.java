package com.makhnyov.robot.service;

import com.makhnyov.robot.model.Command;
import com.makhnyov.robot.model.CommandLog;
import com.makhnyov.robot.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandService {
    private final CommandRepository commandRepository;

    @Autowired
    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public void save(Command command) {
        CommandLog commandLog = new CommandLog(command);
        commandRepository.save(commandLog);
    }

    public long getCount() {
        return commandRepository.count();
    }

    public Command getLastCommand() {
        return commandRepository.findFirstByOrderByIdDesc().getCommand();
    }

    public List<Command> getCommands() {
        return commandRepository.findAll().stream().map(CommandLog::getCommand).collect(Collectors.toList());
    }

    public void deleteAll() {
        commandRepository.deleteAll();
    }
}
