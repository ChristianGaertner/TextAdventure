package de.cpgaertner.edu.inf.games.datacenter.command.debug.debugger;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;
import lombok.Setter;

public class DebuggerCommandParser implements CommandParser {

    @Setter protected Adapter adapter;

    /**
     * Returns a Command, when it can parse the input, null otherwise
     *
     * @param input string to parse
     * @return Command | null
     */
    @Override
    public Command get(String input) {
        // Early testing.
        if (!input.startsWith(DebuggerCommand.NAME)) {
            return null;
        }

        String[] split = input.split(" ");
        if (split.length < 2) {
            return null;
        }

        if (!split[0].equalsIgnoreCase(DebuggerCommand.NAME)) {
            return null;
        }

        Location.Direction direction = Location.Direction.valueOf(
                split[1].toUpperCase() // to uppercase, so it fits the enum!
        );


        return new DebuggerCommand(direction, adapter);
    }

    @Override
    public String getHelp() {
        return "debugger [direction]: %DEBUG% calls toString on this location. direction{north,east,south,west}";
    }
}
