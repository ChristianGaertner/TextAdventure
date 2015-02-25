package de.cpgaertner.edu.inf.games.datacenter.command.go;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;

import java.io.IOException;

public class GoCommandHandler implements CommandHandler<GoCommand> {
    @Override
    public void handle(Player player, GoCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;


        Location.Direction dir = cmd.getDirection();

        // First check the sides of the location itself
        Location loc = player.getLevel().getAt(player.getPosition()).get(dir);

        if (loc == null || loc.isWalkable()) {

            Coordinate newPos = player.getPosition().get(cmd.getDirection());
            Location newLoc = player.get(dir);
            if (newLoc.isWalkable()) {
                player.setPosition(newPos);
                cmd.respondf("Now you are at %s, this is a location of the type '%s'.",
                    newPos,
                    newLoc.getClass().getSimpleName()
                );
            }

        }

        cmd.respondf("You cannot walk into the location in the %s", cmd.getDirection());

    }
}
