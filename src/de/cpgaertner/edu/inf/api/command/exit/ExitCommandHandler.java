package de.cpgaertner.edu.inf.api.command.exit;

import de.cpgaertner.edu.inf.api.ExitRequestedException;
import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.player.Player;

import java.io.IOException;

public class ExitCommandHandler implements CommandHandler<ExitCommand> {
    @Override
    public void handle(Player player, ExitCommand cmd) throws IOException {
        cmd.respond("Exiting...");
        throw new ExitRequestedException();
    }
}
