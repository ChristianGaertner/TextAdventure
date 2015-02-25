package de.cpgaertner.edu.inf.games.datacenter.routines;

import de.cpgaertner.edu.inf.api.ExitRequestedException;
import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.parsing.BasicCommandSystemManager;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;
import de.cpgaertner.edu.inf.api.routine.Routine;
import lombok.Getter;

import java.io.IOException;

public class InitialRoutine implements Routine {

    @Getter protected String name;

    @Getter protected CommandSystemManager commandSystemManager;

    public InitialRoutine(String name, Adapter adapter) {
        this.name = name;
        this.commandSystemManager = new BasicCommandSystemManager(adapter);
    }

    @Override
    public String getPrompt() {
        return ">>";
    }

    @Override
    public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {

        /*
        An initial routine should never receive a command!
         */
        assert cmd == null;

        adapter.sendf("Hi. Welcome to the game %s.", getName());
        adapter.send("To get started: what's your name?");

        String name = adapter.read(">>");

        player.setName(name);

        adapter.sendf("Hi %s. I'm glad to see you. To be honest, we are experiencing major issues " +
                "with our center at this point. A random hacker as gained access to the control room " +
                "and has shutdown everything.\n" +
                "Would you like to help us?", name);

        String answer = adapter.read("[y/n]");

        boolean yes = false;
        if (answer.equalsIgnoreCase("j") || answer.equalsIgnoreCase("ja") || answer.equalsIgnoreCase("jo") || answer.equalsIgnoreCase("y")) {
            yes = true;
        }

        if (!yes) {
            adapter.sendf("What a shame %s. Maybe next time..", name);
            adapter.send("o/ Bye.");
            throw new ExitRequestedException();
        }


        adapter.send("Awesome! Maybe you should go the control room first. " +
                "It's the first door to your right (north) at the end of this hallway.\n" +
                "To get started type: 'help' and to get to the control room try to use " +
                "'go west' a couple of times\nand then go through the door 'go north'.");


        // This Routine cannot handle commands, give the handle back to the parent.
        return false;
    }
}
