package de.cpgaertner.edu.inf.level;

public interface Location {

    public String getName();

    public Location getNorth();

    public Location getWest();

    public Location getSouth();

    public Location getEast();

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
