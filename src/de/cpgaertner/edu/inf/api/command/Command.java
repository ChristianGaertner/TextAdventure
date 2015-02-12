package de.cpgaertner.edu.inf.api.command;

public interface Command {

    public String getName();

    public String getHelp();

    public String[] getArgs();

}
