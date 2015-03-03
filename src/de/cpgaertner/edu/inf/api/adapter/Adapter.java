package de.cpgaertner.edu.inf.api.adapter;

import java.io.Closeable;
import java.io.IOException;

public interface Adapter extends Closeable {

    public void init() throws IOException;

    /**
     * Send, with a linebreak
     * @param msg msg to send
     * @throws IOException
     */
    public void send(String msg) throws IOException;

    /**
     * Send, without a linebreak
     * @param msg msg to send
     * @throws IOException
     */
    public void put(String msg) throws IOException;

    public void sendf(String msg, Object... args) throws IOException;

    public String read(String prompt) throws IOException;
}
