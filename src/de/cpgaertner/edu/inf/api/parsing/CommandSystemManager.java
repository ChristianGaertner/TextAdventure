package de.cpgaertner.edu.inf.api.parsing;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;

import java.io.IOException;
import java.util.List;

public interface CommandSystemManager {


    public void add(CommandParser commandParser);

    public Command get(String prompt) throws IOException;

    public Adapter getAdapter();

    public List<CommandParser> getAll();

}
