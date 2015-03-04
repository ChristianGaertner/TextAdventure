package de.cpgaertner.edu.inf.games.datacenter.command.talk;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;
import lombok.Setter;

public class TalkCommandParser implements CommandParser {

    @Setter protected Adapter adapter;

    /**
     * Returns a Command, when it can parse the input, null otherwise
     *
     * @param input string to parse
     * @return Command | null
     */
    @Override
    public Command get(String input) {

        if (input.equalsIgnoreCase(TalkCommand.NAME)) {
            return new TalkCommand(adapter);
        }

        return null;

    }

    @Override
    public String getHelp() {
        return "talk []: (no args) starts a conversation with a worker of the datacenter";
    }
}
