package de.cpgaertner.edu.inf.api.level;

public interface Level {

    /**
     * Return the Name of the Level
     * @return Level Name
     */
    public String getName();

    /**
     * Initialize the Level
     *
     * Provide the previous Level,
     * if this is the first level, just pass in NULL
     * @param from previous Level | null
     */
    public void init(Level from);


    public Location getAt(Coordinate coordinate);

    /**
     * Close the level aka. the Player leaves the level
     */
    public void close();

    /**
     * Return the initial position
     * @return initial location
     */
    public Coordinate getStart();
}
