package de.cpgaertner.edu.inf.games.datacenter.command.debug.teleport;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;
import de.cpgaertner.edu.inf.api.level.Coordinate;
import lombok.Getter;

public class TeleportCommand extends BasicCommand {

    public static final String NAME = "teleport";

    @Getter protected Coordinate destination;

    public TeleportCommand(Coordinate destination, Adapter adapter) {
        super(NAME, new String[]{destination.toString()}, adapter);
        this.destination = destination;
    }
}
