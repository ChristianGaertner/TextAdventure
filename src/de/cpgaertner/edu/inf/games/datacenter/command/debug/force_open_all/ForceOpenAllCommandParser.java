package de.cpgaertner.edu.inf.games.datacenter.command.debug.force_open_all;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;
import lombok.Setter;

public class ForceOpenAllCommandParser implements CommandParser {

    @Setter protected Adapter adapter;

    /**
     * Returns a Command, when it can parse the input, null otherwise
     *
     * @param input string to parse
     * @return Command | null
     */
    @Override
    public Command get(String input) {

        if (input.equalsIgnoreCase(ForceOpenAllCommand.NAME)) {
            return new ForceOpenAllCommand(adapter);
        }

        return null;

    }

    @Override
    public String getHelp() {
        return "force_open_all []: (no args) %DEBUG% opens all doors!";
    }
}
