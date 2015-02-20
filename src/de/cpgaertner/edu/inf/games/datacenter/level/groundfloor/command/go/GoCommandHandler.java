package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command.go;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;

import java.io.IOException;

public class GoCommandHandler implements CommandHandler<GoCommand> {
    @Override
    public void handle(Player player, GoCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;


        Location newLoc = player.getLocation().get(cmd.getDirection());

        if (newLoc.isWalkable()) {
            player.setLocation(newLoc);
        } else {
            cmd.respondf("You cannot walk into the location in the %s", cmd.getDirection());
        }

    }
}
