package de.cpgaertner.edu.inf.games.datacenter.command.position;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;

public class PositionCommand extends BasicCommand {

    public static final String NAME = "where";

    public PositionCommand(Adapter adapter) {
        super(NAME, null, adapter);
    }
}
