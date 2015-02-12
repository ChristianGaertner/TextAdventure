package de.cpgaertner.edu.inf.api;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;

public interface Game {

    public String getName();

    public Player getPlayer();

    public Routine getInitialRoutine(Adapter adapter);

    public Level getLevel();



    public void changeLevel(Level level);
}
