package de.cpgaertner.edu.inf.api.command.examples.test;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;

import java.io.IOException;

public class TestCommandHandler implements CommandHandler<TestCommand> {
    @Override
    public Routine handle(Player player, TestCommand cmd) throws IOException {
        cmd.respond("passed");
        return null;
    }
}
