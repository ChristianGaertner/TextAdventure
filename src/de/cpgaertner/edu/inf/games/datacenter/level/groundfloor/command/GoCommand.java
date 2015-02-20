package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;
import de.cpgaertner.edu.inf.api.level.Location;
import lombok.Getter;

public class GoCommand extends BasicCommand {

    @Getter protected final String name = "go";

    public GoCommand(String name, String[] args, Adapter adapter) {
        super(name, args, adapter);
    }

    public Location.Direction getDirection() {
        return Location.Direction.valueOf(getArgs()[0]);
    }
}
