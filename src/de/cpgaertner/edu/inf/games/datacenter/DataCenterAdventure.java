package de.cpgaertner.edu.inf.games.datacenter;

import de.cpgaertner.edu.inf.api.ExitRequestedException;
import de.cpgaertner.edu.inf.api.Game;
import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.DefaultPlayer;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.GroundFloorLevel;
import lombok.Getter;

import java.io.IOException;

public class DataCenterAdventure implements Game {

    @Getter protected final String name = "DataCenter";

    @Getter protected Player player;

    @Getter protected Level level;

    public DataCenterAdventure() {
        this.player = new DefaultPlayer();
        this.level = new GroundFloorLevel();
    }

    @Override
    public Routine getInitialRoutine() {
        return new Routine() {
            @Override
            public boolean handle(Player player, Location location, Command cmd, Adapter adapter) throws IOException {

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


                /*
                Just set a few properties of the Player
                 */

                player.setLocation(location);

                // This Routine cannot handle commands, give the handle back to the parent.
                return false;
            }
        };
    }

    @Override
    public void changeLevel(Level level) {

        level.init(getLevel());
        getLevel().close();
        this.level = level;

    }
}
