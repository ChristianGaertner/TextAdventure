package de.cpgaertner.edu.inf.games.datacenter.command.debug.teleport;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;
import lombok.Setter;

public class TeleportCommandParser implements CommandParser {

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
        if (!input.startsWith(TeleportCommand.NAME)) {
            return null;
        }

        String[] split = input.split(" ");
        if (split.length < 3) {
            return null;
        }

        if (!split[0].equalsIgnoreCase(TeleportCommand.NAME)) {
            return null;
        }

        try {

            int x = Integer.parseInt(split[1]);
            int y = Integer.parseInt(split[2]);

            return new TeleportCommand(new Coordinate(x, y), adapter);
        } catch (NumberFormatException e) {
            // ignore
        }

        return null;
    }

    @Override
    public String getHelp() {
        return "teleport [x] [y]: %DEBUG% sets the player's location.";
    }
}
