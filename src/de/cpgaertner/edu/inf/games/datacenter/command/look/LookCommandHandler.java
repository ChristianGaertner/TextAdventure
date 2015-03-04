package de.cpgaertner.edu.inf.games.datacenter.command.look;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;

import java.io.IOException;

public class LookCommandHandler implements CommandHandler<LookCommand> {
    @Override
    public Routine handle(Player player, LookCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;


        boolean inLocation = true;
        Location.Direction dir = cmd.getDirection();

        // First check the sides of the location itself
        Location loc = player.getLevel().getAt(player.getPosition()).get(dir);

        if (loc == null) {
            // if there is now, get the next tile
            loc = player.get(dir);
            inLocation = false;
        }

        cmd.respondf("In the %s there is a %s.", dir, loc.getClass().getSimpleName());
        if (inLocation) {
            cmd.respond("This is part of your current location.");
        } else {
            cmd.respondf("This would be the Location at %s.", player.getPosition().get(dir));
        }

        return null;
    }
}
