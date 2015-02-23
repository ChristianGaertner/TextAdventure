package de.cpgaertner.edu.inf.api.command.examples.test;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;

public class TestCommand extends BasicCommand {

    public static final String NAME = "test";

    public TestCommand(Adapter adapter) {
        super(NAME, null, adapter);
    }
}
