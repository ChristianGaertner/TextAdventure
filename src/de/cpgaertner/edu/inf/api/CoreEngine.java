package de.cpgaertner.edu.inf.api;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.parsing.LastHopeParser;
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

        
        Routine handle = game.getInitialRoutine();
        try {
            boolean boot = true;
            Command cmd;
            LastHopeParser p = new LastHopeParser();
            p.setAdapter(adapter);

            while(isRun()) {

                if (boot) {
                    cmd = null;
                    boot = false;
                } else {
                    cmd = p.get(adapter.read(handle.getPrompt()));
                }

                boolean exit = !handle.handle(game.getPlayer(), game.getPlayer().getLocation(), cmd, adapter);
                if (exit) {
                    handle = root;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
