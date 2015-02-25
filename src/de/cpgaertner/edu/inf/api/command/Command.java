package de.cpgaertner.edu.inf.api.command;

import de.cpgaertner.edu.inf.api.adapter.Adapter;

import java.io.IOException;

/**
 * Command send by the User
 */
public interface Command {

    /**
     * Get the main command (e.g. 'help', 'exit', etc.)
     *
     * Example:
     *
     * input String: 'help me now'
     *
     * this method returns 'help'
     *
     * NOTE: The main command can contain spaces, this depends on the parser!
     *
     * @return main command
     */
    public String getName();

    /**
     * Get the arguments of the main command
     *
     * Example:
     *
     * input String: 'help me now'
     *
     * this method returns ['exit','now']
     *
     * NOTE: The args can contain spaces, this depends on the parser!
     *
     * @return arguments of main command
     */
    public String[] getArgs();

    /**
     * Utility Method.
     *
     * Wraps {@link de.cpgaertner.edu.inf.api.adapter.Adapter#send}
     * @param msg message to send
     * @throws IOException
     */
    public void respond(String msg) throws IOException;

    /**
     * Utility Method.
     *
     * Wraps {@link de.cpgaertner.edu.inf.api.adapter.Adapter#sendf}
     * @param msg message to send
     * @param args args to replace
     * @throws IOException
     *
     * @see de.cpgaertner.edu.inf.api.adapter.Adapter#sendf
     */
    public void respondf(String msg, Object... args) throws IOException;

    /**
     * A command should provide the adapter
     * @return the adapter used to issue this command
     */
    public Adapter getAdapter();
}
