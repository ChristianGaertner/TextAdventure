package de.cpgaertner.edu.inf.api.parsing;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasicCommandSystemManager implements CommandSystemManager {

    @Getter protected Adapter adapter;

    protected final LastHopeParser lastHopeParser;
    protected List<CommandParser> cmdParser;

    public BasicCommandSystemManager(Adapter adapter) {
        this.lastHopeParser = new LastHopeParser();
        this.adapter = adapter;
        this.lastHopeParser.setAdapter(adapter);
        this.cmdParser = new ArrayList<>();
    }

    @Override
    public void add(CommandParser commandParser) {
        cmdParser.add(commandParser);
    }

    @Override
    public Command get(String prompt) throws IOException {

        String input = adapter.read(prompt);

        Command cmd;
        for (CommandParser parser : cmdParser) {
            cmd = parser.get(input);
            if (cmd != null) {
                return cmd;
            }
        }

        return lastHopeParser.get(input);

    }

    @Override
    public List<CommandParser> getAll() {
        return cmdParser;
    }
}
