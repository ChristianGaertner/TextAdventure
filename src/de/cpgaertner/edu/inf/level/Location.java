package de.cpgaertner.edu.inf.level;

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
