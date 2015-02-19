package de.cpgaertner.edu.inf.api;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.routine.RootRoutine;
import de.cpgaertner.edu.inf.api.routine.Routine;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class CoreEngine implements Runnable {


    protected Game game;
    protected Adapter adapter;

    @Getter @Setter protected boolean run;

    public CoreEngine(Game game, Adapter adapter) {
        this.game = game;
        this.adapter = adapter;
    }

    @Override
    public void run() {

        setRun(true);

        RootRoutine root = new RootRoutine();
        
        Routine activeRoutine = game.getInitialRoutine();

        try {
            boolean boot = true;
            Command cmd;


            while(isRun()) {

                // TODO clean up!
                if (boot) {
                    cmd = null;
                    boot = false;
                } else {

                    cmd = activeRoutine.getCommandSystemManager(adapter).get(activeRoutine.getPrompt());
                }

                boolean exit = !activeRoutine.handle(game.getPlayer(), game.getPlayer().getLocation(), cmd, adapter);
                if (exit) {
                    activeRoutine = root;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
