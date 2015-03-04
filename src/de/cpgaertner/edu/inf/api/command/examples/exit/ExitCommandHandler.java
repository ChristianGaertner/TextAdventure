package de.cpgaertner.edu.inf.api.command.examples.exit;

import de.cpgaertner.edu.inf.api.ExitRequestedException;
import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;

import java.io.IOException;

public class ExitCommandHandler implements CommandHandler<ExitCommand> {
    @Override
    public Routine handle(Player player, ExitCommand cmd) throws IOException {
        cmd.respond("Exiting...");
        throw new ExitRequestedException();
    }
}
