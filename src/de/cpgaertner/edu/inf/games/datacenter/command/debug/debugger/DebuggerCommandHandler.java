package de.cpgaertner.edu.inf.games.datacenter.command.debug.debugger;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;

import java.io.IOException;

public class DebuggerCommandHandler implements CommandHandler<DebuggerCommand> {
    @Override
    public void handle(Player player, DebuggerCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;


        Level level = player.getLevel();
        Coordinate position = player.getPosition();

        Location loc = level.getAt(position);

        Location inTile = loc.get(cmd.getDirection());
        Location nextTile = level.getAt(position.get(cmd.getDirection()));

        cmd.getAdapter().sendf("inTile[%s]=%s", inTile.getClass().getSimpleName(), inTile);
        cmd.getAdapter().sendf("nextTile[%s]=%s", nextTile.getClass().getSimpleName(), nextTile);

    }
}
