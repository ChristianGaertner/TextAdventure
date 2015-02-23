package de.cpgaertner.edu.inf.games.datacenter.command.position;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;
import lombok.Setter;

public class PositionCommandParser implements CommandParser {

    @Setter protected Adapter adapter;

    /**
     * Returns a Command, when it can parse the input, null otherwise
     *
     * @param input string to parse
     * @return Command | null
     */
    @Override
    public Command get(String input) {

        if (input.equalsIgnoreCase(PositionCommand.NAME)) {
            return new PositionCommand(adapter);
        }

        return null;

    }

    @Override
    public String getHelp() {
        return "where []: (no args) prints out where you are";
    }
}
