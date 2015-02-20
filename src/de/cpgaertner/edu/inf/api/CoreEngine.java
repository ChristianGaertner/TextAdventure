package de.cpgaertner.edu.inf.api;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;
import de.cpgaertner.edu.inf.api.routine.RootRoutine;
import de.cpgaertner.edu.inf.api.routine.Routine;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class CoreEngine implements Runnable {


    protected Game game;
    protected Adapter adapter;

    @Getter @Setter protected boolean run;

    public CoreEngine(Game game, Adapter adapter) {
        this.game = game;
        this.adapter = adapter;

        /*
        Do some bootstrap stuff
        TODO: remove this from here
         */

        this.game.getLevel().init(null);
        this.game.getPlayer().setLevel(this.game.getLevel());
        this.game.getPlayer().setPosition(this.game.getLevel().getStart());
    }

    @Override
    public void run() {

        setRun(true);

        Routine root = game.getHostRoutine(adapter);

        if (root == null) {
            root = new RootRoutine(adapter); // Fallback
        }

        Routine previousRoutine = null;
        Routine activeRoutine = game.getInitialRoutine(adapter);

        try {

            Command cmd = null;
            CommandSystemManager csm = null;


            while(isRun()) {

                // TODO clean up!
                if (previousRoutine == null) {

                    previousRoutine = activeRoutine;
                    csm = activeRoutine.getCommandSystemManager();
                } else {


                    if (previousRoutine != activeRoutine) {

                        csm = activeRoutine.getCommandSystemManager();
                        previousRoutine = activeRoutine; // reset it!

                    }

                    // THIS BLOCKS until the user hits enter!
                    cmd = csm.get(activeRoutine.getPrompt());

                }

                boolean exit = !activeRoutine.handle(game.getPlayer(), cmd, adapter);
                if (exit) {
                    previousRoutine = activeRoutine;
                    if (activeRoutine == root) {
                        // This would be an infinite loop
                        log.warning("Infinite Loop detected. Host Routine, declines action. Falling back to RootRoutine");
                        root = new RootRoutine(adapter);
                    }
                    activeRoutine = root;
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
