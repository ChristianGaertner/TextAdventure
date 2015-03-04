package de.cpgaertner.edu.inf.api.command.handler;

import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;

import java.io.IOException;

public interface CommandHandler<T extends Command> {

    public Routine handle(Player player, T cmd) throws IOException;

}
