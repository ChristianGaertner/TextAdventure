package de.cpgaertner.edu.inf.api.command.examples.test;

import de.cpgaertner.edu.inf.api.routine.CommandPackage;
import lombok.Data;

@Data
public class TestCommandPackage implements CommandPackage<TestCommand> {

    protected TestCommandParser parser = new TestCommandParser();

    protected TestCommandHandler handler = new TestCommandHandler();

    protected Class<TestCommand> command = TestCommand.class;

}
