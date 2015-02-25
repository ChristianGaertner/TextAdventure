package de.cpgaertner.edu.inf.games.datacenter.command.debug.force_open_all;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;

public class ForceOpenAllCommand extends BasicCommand {

    public static final String NAME = "force_open_all";

    public ForceOpenAllCommand(Adapter adapter) {
        super(NAME, null, adapter);
    }
}
