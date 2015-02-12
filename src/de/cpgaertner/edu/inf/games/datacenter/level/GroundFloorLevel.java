package de.cpgaertner.edu.inf.games.datacenter.level;

import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;

public class GroundFloorLevel implements Level {
    /**
     * Return the Name of the Level
     *
     * @return Level Name
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Initialize the Level
     * <p/>
     * Provide the previous Level,
     * if this is the first level, just pass in NULL
     *
     * @param from previous Level | null
     */
    @Override
    public void init(Level from) {

    }

    /**
     * Close the level aka. the Player leaves the level
     */
    @Override
    public void close() {

    }

    /**
     * Return the initial Location
     *
     * @return initial location
     */
    @Override
    public Location getStart() {
        return null;
    }
}
