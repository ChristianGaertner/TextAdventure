package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;
import lombok.Data;

@Data
public abstract class BaseLocation implements Location {


    protected String name;

    protected Location north;
    protected Location west;
    protected Location south;
    protected Location east;

    protected boolean walkable;

    protected int lightLevel;

    /**
     * Should return a Routine for this Location.
     * Null indicates a null interaction location
     *
     * @param player  Player to engage in the Routine
     * @param adapter The adapter to use for the Routine
     * @return The routine, not entered yet!
     */
    @Override
    public Routine getRoutine(Player player, Adapter adapter) {
        return null;
    }
}
