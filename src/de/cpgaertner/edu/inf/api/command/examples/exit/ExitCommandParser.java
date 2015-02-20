package de.cpgaertner.edu.inf.api.command.examples.exit;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;
import lombok.Setter;

public class ExitCommandParser implements CommandParser {

    @Setter protected Adapter adapter;

    /**
     * Returns a Command, when it can parse the input, null otherwise
     *
     * @param input string to parse
     * @return Command | null
     */
    @Override
    public Command get(String input) {
        if (input.equalsIgnoreCase(ExitCommand.NAME)) {
            return new ExitCommand(adapter);
        }

        return null;
    }

    @Override
    public String getHelp() {
        return "exit []: (no args) quits the game";
    }
}
