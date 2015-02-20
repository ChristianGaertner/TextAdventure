package de.cpgaertner.edu.inf.api.command.handler;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;

import java.io.IOException;

public interface CommandHandler<T extends Command> {

    public boolean handle(Player player, Location location, T cmd, Adapter adapter) throws IOException;

}
