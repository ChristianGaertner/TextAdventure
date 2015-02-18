package de.cpgaertner.edu.inf.api;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.parsing.CommandParser;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;
import de.cpgaertner.edu.inf.api.parsing.LastHopeParser;
import de.cpgaertner.edu.inf.api.routine.RootRoutine;
import de.cpgaertner.edu.inf.api.routine.Routine;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoreEngine implements Runnable {


    protected Game game;
    protected Adapter adapter;

    @Getter protected CommandSystemManager cmdManager;

    @Getter @Setter protected boolean run;

    public CoreEngine(Game game, Adapter adapter) {
        this.game = game;
        this.adapter = adapter;
    }

    @Override
    public void run() {

        setRun(true);

        RootRoutine root = new RootRoutine();

        cmdManager = new QuickCommandSystemManager(adapter);
        
        Routine activeRoutine = game.getInitialRoutine();

        try {
            boolean boot = true;
            Command cmd;


            while(isRun()) {

                if (boot) {
                    cmd = null;
                    boot = false;
                } else {
                    cmd = cmdManager.get(activeRoutine.getPrompt());
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


    //TMP!!
    final class QuickCommandSystemManager implements CommandSystemManager {

        final LastHopeParser lastHopeParser;
        private List<CommandParser> cmdParser;
        @Getter private Adapter adapter;

        public QuickCommandSystemManager(Adapter adapter) {
            lastHopeParser = new LastHopeParser();
            this.adapter = adapter;
            lastHopeParser.setAdapter(adapter);
            cmdParser = new ArrayList<>();
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
    }
}
