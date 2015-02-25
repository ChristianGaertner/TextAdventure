package de.cpgaertner.edu.inf.games.datacenter.command.interact;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;
import de.cpgaertner.edu.inf.api.level.Location;
import lombok.Getter;

public class InteractCommand extends BasicCommand {

    public static final String NAME = "interact";

    @Getter protected Location.Direction direction;

    public InteractCommand(Location.Direction direction, Adapter adapter) {
        super(NAME, new String[]{direction.toString()}, adapter);
        this.direction = direction;
    }
}
