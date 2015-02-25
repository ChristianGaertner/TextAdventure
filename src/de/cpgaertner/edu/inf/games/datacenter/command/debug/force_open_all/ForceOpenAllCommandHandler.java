package de.cpgaertner.edu.inf.games.datacenter.command.debug.force_open_all;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.player.Player;

import java.io.IOException;

public class ForceOpenAllCommandHandler implements CommandHandler<ForceOpenAllCommand> {
    @Override
    public void handle(Player player, ForceOpenAllCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;
        cmd.respondf("You are at %s, this is a location of the type '%s'.",
                player.getPosition(),
                player.getLevel().getAt(player.getPosition()).getClass().getSimpleName()
        );

    }
}
