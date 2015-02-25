package de.cpgaertner.edu.inf.games.datacenter.command.debug.force_open_all;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.DoorLocation;

import java.io.IOException;

public class ForceOpenAllCommandHandler implements CommandHandler<ForceOpenAllCommand> {
    @Override
    public void handle(Player player, ForceOpenAllCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;


        Coordinate coordinate = new Coordinate(0, 0);
        Level level = player.getLevel();

        Coordinate bound = level.getBound();

        for (int x = 0; x < bound.getX(); x++) {
            for (int y = 0; y < bound.getY(); y++) {

                coordinate.setX(x);
                coordinate.setY(y);

                Location loc = level.getAt(coordinate);

                tryOpen(loc);
                tryOpen(loc.getNorth());
                tryOpen(loc.getEast());
                tryOpen(loc.getSouth());
                tryOpen(loc.getWest());


            }
        }

    }

    protected void tryOpen(Location loc) {
        if (loc == null) {
            return;
        }

        if (loc instanceof DoorLocation) {
            ((DoorLocation) loc).forceOpen();
        }
    }
}
