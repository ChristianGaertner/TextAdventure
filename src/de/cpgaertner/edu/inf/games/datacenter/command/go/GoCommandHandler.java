package de.cpgaertner.edu.inf.games.datacenter.command.go;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.player.Player;

import java.io.IOException;

public class GoCommandHandler implements CommandHandler<GoCommand> {
    @Override
    public void handle(Player player, GoCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;


        Coordinate newPos = player.getPosition().get(cmd.getDirection());

        if (player.getLevel().getAt(newPos).isWalkable()) {
            player.setPosition(newPos);
        } else {
            cmd.respondf("You cannot walk into the location in the %s", cmd.getDirection());
        }

    }
}
