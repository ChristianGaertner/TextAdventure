package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.routines;

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

        adapter.sendf("Hallo. Willkommen beim Spiel %s.", getName());
        adapter.send("Magst Du mir zunächst deinen Name verraten?");

        String name = adapter.read(">>");

        player.setName(name);

        adapter.sendf("Hi %s. Ich freue mich das du hier bist. Um ehrlich zu sein haben wir hier gerade ein " +
                "paar Probleme. Irgendjemand hat sich Zugang zu unserem Rechenzentrum verschaffen und alle " +
                "Server lahm gelegt.\nKannst Du uns helfen das Problem zu lösen?", name);

        String answer = adapter.read("[j/n]");

        boolean yes = false;
        if (answer.equalsIgnoreCase("j") || answer.equalsIgnoreCase("ja") || answer.equalsIgnoreCase("jo") || answer.equalsIgnoreCase("y")) {
            yes = true;
        }

        if (!yes) {
            adapter.sendf("Schade %s. Vielleicht beim nächsten Mal", name);
            adapter.send("o/ Tschüss.");
            throw new ExitRequestedException();
        }


        adapter.send("Super. Das ist Klasse. Am Besten du schaust erstmal im Kontroll Raum vorbei, " +
                "der ist links im Gang die erste Tür rechts!");


        // This Routine cannot handle commands, give the handle back to the parent.
        return false;
    }
}
