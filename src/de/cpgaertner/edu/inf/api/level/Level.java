package de.cpgaertner.edu.inf.api.level;

public interface Level {

    public static final String KEY_QUEST = "PLAYER_QUEST_ID";

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
     * Returns the lower right corner of the level
     * @return lower right corner
     */
    public Coordinate getBound();

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
