package de.cpgaertner.edu.inf.routine;

import de.cpgaertner.edu.inf.adapter.Adapter;
import de.cpgaertner.edu.inf.level.Location;
import de.cpgaertner.edu.inf.level.player.Player;

public interface Routine {


    public void enter(Player player, Location location);

    /**
     * Returns true when the player is required to input text
     * @return interaction needed
     */
    public boolean isInteractive();


    public Adapter getAdapter();

    public void setAdapter(Adapter adapter);

}
