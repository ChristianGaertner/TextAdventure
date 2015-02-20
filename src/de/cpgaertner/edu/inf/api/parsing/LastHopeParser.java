package de.cpgaertner.edu.inf.api.parsing;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.BasicCommand;
import de.cpgaertner.edu.inf.api.command.Command;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public class LastHopeParser implements CommandParser {

    @Getter @Setter protected Adapter adapter;

    /**
     * This will ALWAYS return a Command object, it just splits it up by the spaces in the input string.
     *
     * @param input string to parse
     * @return Command
     */
    @Override
    public Command get(String input) {

        final String[] words = input.split(" ");

        return new BasicCommand(words[0], Arrays.copyOfRange(words, 1, words.length), getAdapter());
    }

    @Override
    public String getHelp() {
        return null;
    }
}
