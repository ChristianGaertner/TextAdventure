package de.cpgaertner.edu.inf.games.datacenter.command.talk;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;

public class TalkCommand extends BasicCommand {

    public static final String NAME = "talk";

    public TalkCommand(Adapter adapter) {
        super(NAME, null, adapter);
    }
}
