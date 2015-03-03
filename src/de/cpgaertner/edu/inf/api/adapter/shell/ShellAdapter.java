package de.cpgaertner.edu.inf.api.adapter.shell;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellAdapter implements Adapter {

    @Getter protected BufferedReader reader;

    @Override
    public void init() throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void send(String msg) throws IOException {
        System.out.println(msg);
    }

    /**
     * Send, without a linebreak
     *
     * @param msg msg to send
     * @throws java.io.IOException
     */
    @Override
    public void put(String msg) throws IOException {
        System.out.print(msg);
    }

    @Override
    public void sendf(String msg, Object... args) throws IOException {
        System.out.printf(msg, args);
        System.out.println();
    }

    @Override
    public String read(String prompt) throws IOException {
        System.out.print(prompt);
        return getReader().readLine();
    }

    @Override
    public void close() throws IOException {
        getReader().close();
        this.reader = null;
    }
}
