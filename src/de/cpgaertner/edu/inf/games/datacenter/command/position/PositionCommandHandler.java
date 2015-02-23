package de.cpgaertner.edu.inf.games.datacenter.command.position;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.player.Player;

import java.io.IOException;

public class PositionCommandHandler implements CommandHandler<PositionCommand> {
    @Override
    public void handle(Player player, PositionCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;
        cmd.respondf("You are at %s, this is a location of the type '%s'.",
                player.getPosition(),
                player.getLevel().getAt(player.getPosition()).getClass().getSimpleName()
        );

    }
}
