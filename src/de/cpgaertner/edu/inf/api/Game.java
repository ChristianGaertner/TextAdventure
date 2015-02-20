package de.cpgaertner.edu.inf.api;

import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;

public interface Game {

    public String getName();

    public Player getPlayer();

    /**
     * Will run on boot
     * @return boot routine
     */
    public Routine getInitialRoutine();

    /**
     * Handles the game.
     * @return main routine
     */
    public Routine getHostRoutine();

    public Level getLevel();



    public void changeLevel(Level level);
}
