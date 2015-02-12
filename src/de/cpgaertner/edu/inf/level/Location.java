package de.cpgaertner.edu.inf.level;

import de.cpgaertner.edu.inf.adapter.Adapter;
import de.cpgaertner.edu.inf.level.player.Player;
import de.cpgaertner.edu.inf.routine.Routine;

public interface Location {

    public String getName();

    public Location getNorth();

    public Location getWest();

    public Location getSouth();

    public Location getEast();

    /**
     * Whether a Player can walk into this location,
     * Hallways, etc. but not lockers, desks, etc.
     * @return walkable
     */
    public boolean isWalkable();


    /**
     * Should return a Routine for this Location.
     * Null indicates a null interaction location
     * @param player Player to engage in the Routine
     * @param adapter The adapter to use for the Routine
     * @return The routine, not entered yet!
     */
    public Routine getRoutine(Player player, Adapter adapter);

    /**
     * Return the LightLevel of the Location,
     *
     * this might be changed by the Level, Player Items, etc.
     *
     * Range from 0 - 10
     *
     * @return 0 - 10 LightLevel
     */
    public int getLightLevel();

}
