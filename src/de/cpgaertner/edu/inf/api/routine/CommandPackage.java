package de.cpgaertner.edu.inf.api.routine;

import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;

public interface CommandPackage<T extends Command> {

    public CommandParser getParser();

    public CommandHandler<T> getHandler();

    public Class<T> getCommand();

}
