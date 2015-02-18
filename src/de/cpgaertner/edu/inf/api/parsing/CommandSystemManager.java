package de.cpgaertner.edu.inf.api.parsing;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;

import java.io.IOException;

public interface CommandSystemManager {


    public void add(CommandParser commandParser);

    public Command get(Adapter adapter) throws IOException;

}
