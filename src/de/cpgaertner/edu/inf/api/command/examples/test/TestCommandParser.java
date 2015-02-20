package de.cpgaertner.edu.inf.api.command.examples.test;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;
import lombok.Setter;

public class TestCommandParser implements CommandParser {

    @Setter protected Adapter adapter;

    /**
     * Returns a Command, when it can parse the input, null otherwise
     *
     * @param input string to parse
     * @return Command | null
     */
    @Override
    public Command get(String input) {
        if (input.equalsIgnoreCase(TestCommand.NAME)) {
            return new TestCommand(adapter);
        }

        return null;
    }

    @Override
    public String getHelp() {
        return "test []: (no args) answers 'passed' just to make sure the command-system works";
    }
}
