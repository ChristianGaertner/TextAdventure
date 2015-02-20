package de.cpgaertner.edu.inf.api;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;
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

        Routine root = game.getHostRoutine();

        if (root == null) {
            root = new RootRoutine(); // Fallback
        }

        Routine previousRoutine = null;
        Routine activeRoutine = game.getInitialRoutine();

        try {

            Command cmd = null;
            CommandSystemManager csm = null;


            while(isRun()) {

                // TODO clean up!
                if (previousRoutine == null) {

                    previousRoutine = activeRoutine;
                    csm = activeRoutine.getCommandSystemManager(adapter);
                } else {


                    if (previousRoutine != activeRoutine) {

                        csm = activeRoutine.getCommandSystemManager(adapter);
                        previousRoutine = activeRoutine; // reset it!

                    }

                    // THIS BLOCKS until the user hits enter!
                    cmd = csm.get(activeRoutine.getPrompt());

                }

                boolean exit = !activeRoutine.handle(game.getPlayer(), game.getPlayer().getLocation(), cmd, adapter);
                if (exit) {
                    previousRoutine = activeRoutine;
                    activeRoutine = root;
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
