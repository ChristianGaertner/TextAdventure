package de.cpgaertner.edu.inf.api.parsing;

import de.cpgaertner.edu.inf.api.command.BasicCommand;
import de.cpgaertner.edu.inf.api.command.Command;

public class DumpParser extends LastHopeParser {

    /**
     * Returns a Command, when it can parse the input, null otherwise
     *
     * @param input string to parse
     * @return Command | null
     */
    @Override
    public Command get(String input) {
        return new BasicCommand(input, null, getAdapter());
    }
}
