package de.cpgaertner.edu.inf.games.datacenter.command.debug.debugger;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;
import de.cpgaertner.edu.inf.api.level.Location;
import lombok.Getter;

public class DebuggerCommand extends BasicCommand {

    public static final String NAME = "debugger";

    @Getter protected Location.Direction direction;

    public DebuggerCommand(Location.Direction direction, Adapter adapter) {
        super(NAME, new String[]{direction.toString()}, adapter);
        this.direction = direction;
    }
}
