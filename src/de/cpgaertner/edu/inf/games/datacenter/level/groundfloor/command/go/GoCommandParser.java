package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command.go;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;
import lombok.Setter;

public class GoCommandParser implements CommandParser {

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
        if (!input.startsWith(GoCommand.NAME)) {
            return null;
        }

        String[] split = input.split(" ");
        if (split.length < 2) {
            return null;
        }

        if (!split[0].equalsIgnoreCase(GoCommand.NAME)) {
            return null;
        }

        Location.Direction direction = Location.Direction.valueOf(
                split[1].toUpperCase() // to uppercase, so it fits the enum!
        );


        return new GoCommand(direction, adapter);
    }

    @Override
    public String getHelp() {
        return "go [direction]: moves the player to another location. direction{north,east,south,west}";
    }
}
