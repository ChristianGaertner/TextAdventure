package de.cpgaertner.edu.inf.api.command.examples.exit;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;

public class ExitCommand extends BasicCommand {

    public static final String NAME = "exit";

    public ExitCommand(Adapter adapter) {
        super(NAME, null, adapter);
    }

}
