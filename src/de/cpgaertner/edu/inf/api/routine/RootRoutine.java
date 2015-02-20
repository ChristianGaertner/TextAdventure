package de.cpgaertner.edu.inf.api.routine;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.command.exit.ExitCommandPackage;
import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.parsing.BasicCommandSystemManager;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RootRoutine implements Routine {


    protected CommandSystemManager csm;

    protected Map<Class<? extends Command>, CommandHandler> cmdHandler;

    public RootRoutine(CommandSystemManager csm) {
        this.csm = csm;
        this.cmdHandler = new HashMap<>();

        /*
        Add some default commands
         */
        addCommand(new ExitCommandPackage());
    }

    public RootRoutine(Adapter adapter) {
        this(new BasicCommandSystemManager(adapter));
    }

    @Override
    public String getPrompt() {
        return ">>";
    }

    @Override
    public CommandSystemManager getCommandSystemManager() {
        return csm;
    }

    public <T extends Command> void addCommandHandler(Class<T> cls, CommandHandler<T> handler) {
        cmdHandler.put(cls, handler);
    }

    @SuppressWarnings("unchecked")
    public void addCommand(CommandPackage commandPackage) {
        commandPackage.getParser().setAdapter(csm.getAdapter());
        csm.add(commandPackage.getParser());
        cmdHandler.put(commandPackage.getCommand(), commandPackage.getHandler());
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {

        if (cmd == null) {
            adapter.send("Type something, try 'help' to get started!");
            return true;
        }

        if (cmdHandler.containsKey(cmd.getClass())) {
            cmdHandler.get(cmd.getClass())
                    .handle(player, cmd);
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("help")) {
            cmd.respond(
                    "help: (no args) this help display\n" +
                    "test: (no args) prints 'ok'\n" +
                    "exit: (no args) quits the game"
            );

            for (CommandParser p : csm.getAll()) {
                cmd.respond(p.getHelp());
            }

            return true;
        }

        if (cmd.getName().equalsIgnoreCase("test")) {
            cmd.respond("ok");
            return true;
        }

        adapter.sendf("Unknow comand <%s>, try 'help' to get started", cmd.getName());
        return true;
    }
}
