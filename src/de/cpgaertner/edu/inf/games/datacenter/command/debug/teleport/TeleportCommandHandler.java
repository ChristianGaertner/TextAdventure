package de.cpgaertner.edu.inf.games.datacenter.command.debug.teleport;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;

import java.io.IOException;

public class TeleportCommandHandler implements CommandHandler<TeleportCommand> {
    @Override
    public Routine handle(Player player, TeleportCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;

        if (player.getLevel().getBound().getX() < cmd.getDestination().getX()) {
            cmd.getAdapter().send("X out ouf bounds");
        }

        if (player.getLevel().getBound().getY() < cmd.getDestination().getY()) {
            cmd.getAdapter().send("Y out ouf bounds");
        }

        player.setPosition(cmd.getDestination());

        cmd.getAdapter().sendf("You are now at %s.", cmd.getDestination());

        return null;

    }
}
