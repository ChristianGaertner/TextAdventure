package de.cpgaertner.edu.inf.api.command;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;

@AllArgsConstructor
public class BasicCommand implements Command {

    @Getter protected String name;

    @Getter protected String[] args;

    @Getter protected Adapter adapter;


    /**
     * Utility Method.
     * <p/>
     * Wraps {@link de.cpgaertner.edu.inf.api.adapter.Adapter#send}
     *
     * @param msg message to send
     * @throws java.io.IOException
     */
    @Override
    public void respond(String msg) throws IOException {
        this.adapter.send(msg);
    }

    /**
     * Utility Method.
     * <p/>
     * Wraps {@link de.cpgaertner.edu.inf.api.adapter.Adapter#sendf}
     *
     * @param msg  message to send
     * @param args args to replace
     * @throws java.io.IOException
     * @see de.cpgaertner.edu.inf.api.adapter.Adapter#sendf
     */
    @Override
    public void respondf(String msg, Object... args) throws IOException {
        this.adapter.sendf(msg, args);
    }
}
