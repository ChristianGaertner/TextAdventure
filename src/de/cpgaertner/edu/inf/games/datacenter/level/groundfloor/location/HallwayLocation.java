package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;
import lombok.Getter;

public class HallwayLocation implements Location {


    @Getter protected String name = "Flur";

    @Getter protected Location north;
    @Getter protected Location west;
    @Getter protected Location south;
    @Getter protected Location east;

    @Getter protected boolean walkable;

    @Getter protected int lightLevel;

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
