package de.cpgaertner.edu.inf.level;

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

    /**
     * Close the level aka. the Player leaves the level
     */
    public void close();

    /**
     * Return the initial Location
     * @return initial location
     */
    public Location getStart();
}
