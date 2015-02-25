package de.cpgaertner.edu.inf.games.datacenter.command.interact;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;

import java.io.IOException;

public class InteractCommandHandler implements CommandHandler<InteractCommand> {
    @Override
    public void handle(Player player, InteractCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;


        Location.Direction dir = cmd.getDirection();

        // First check the sides of the location itself
        Location target = player.getLevel().getAt(player.getPosition()).get(dir);

        if (target == null) {
            target = player.get(dir);
            if (target == null) {
                cmd.respondf("No location to interact in the %s.", dir);
                return;
            }
        }

        Routine interaction = target.getRoutine();

        if (interaction == null) {
            cmd.respondf("You cannot interact with the location in the %s", dir);
            return;
        }


        interaction.handle(player, null, cmd.getAdapter());

    }
}
