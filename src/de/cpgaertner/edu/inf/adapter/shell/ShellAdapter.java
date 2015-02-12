package de.cpgaertner.edu.inf.adapter.shell;

import de.cpgaertner.edu.inf.adapter.Adapter;
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

    @Override
    public void sendf(String msg, Object... args) throws IOException {
        System.out.printf(msg, args);
    }

    @Override
    public String read(String prompt) throws IOException {
        return getReader().readLine();
    }

    @Override
    public void close() throws IOException {
        getReader().close();
        this.reader = null;
    }
}
