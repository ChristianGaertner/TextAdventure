package de.cpgaertner.edu.inf.games.datacenter.command.go;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;
import de.cpgaertner.edu.inf.api.level.Location;
import lombok.Getter;

public class GoCommand extends BasicCommand {

    public static final String NAME = "go";

    @Getter protected Location.Direction direction;

    public GoCommand(Location.Direction direction, Adapter adapter) {
        super(NAME, new String[]{direction.toString()}, adapter);
        this.direction = direction;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
