package de.cpgaertner.edu.inf.parsing;

import de.cpgaertner.edu.inf.command.Command;

import java.io.IOException;

public interface CommandParser {


    public Command parse(String input) throws IOException;

}
