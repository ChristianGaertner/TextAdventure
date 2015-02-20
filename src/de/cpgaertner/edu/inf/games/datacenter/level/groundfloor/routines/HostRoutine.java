package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.routines;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;
import de.cpgaertner.edu.inf.api.routine.RootRoutine;

import java.io.IOException;
import java.util.Map;

public class HostRoutine extends RootRoutine {


    protected Map<Class<? extends Command>, CommandHandler> cmdHandler;

    public HostRoutine(CommandSystemManager csm) {
        super(csm);
    }

    public HostRoutine(Adapter adapter) {
        super(adapter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {

        if (cmd == null) return true;


        if (cmdHandler.containsKey(cmd.getClass())) {
            cmdHandler.get(cmd.getClass())
                    .handle(player, cmd);
            return true;
        }



        return false;
    }

}
