package de.cpgaertner.edu.inf.api;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;

public interface Game {

    public String getName();

    public Player getPlayer();

    /**
     * Runs at boot time
     *
     * The Adapter will be used to create the CSM
     * @param adapter adapter
     * @return initial routine
     */
    public Routine getInitialRoutine(Adapter adapter);

    /**
     * Handles the game
     *
     * The Adapter will be used to create the CSM
     * @param adapter adapter
     * @return host routine
     */
    public Routine getHostRoutine(Adapter adapter);

    public Level getLevel();



    public void changeLevel(Level level);
}
