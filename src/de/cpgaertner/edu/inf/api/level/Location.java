package de.cpgaertner.edu.inf.api.level;

import de.cpgaertner.edu.inf.api.routine.Routine;

public interface Location {

    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    public String getName();

    public Location get(Direction direction);

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
     * @return The routine, not entered yet!
     */
    public Routine getRoutine();

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
