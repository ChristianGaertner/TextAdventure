package de.cpgaertner.edu.inf.games.datacenter.command.look;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;

import java.io.IOException;

public class LookCommandHandler implements CommandHandler<LookCommand> {
    @Override
    public void handle(Player player, LookCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;


        Location.Direction dir = cmd.getDirection();

        cmd.respondf("In the %s there is a %s.", dir, player.getLocation().get(dir).getClass().getSimpleName());

    }
}
