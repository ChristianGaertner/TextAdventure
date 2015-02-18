package de.cpgaertner.edu.inf.api.parsing;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;

public interface CommandParser {


    public void setAdapter(Adapter adapter);


    /**
     * Returns a Command, when it can parse the input, null otherwise
     * @param input string to parse
     * @return Command | null
     */
    public Command get(String input);

}
