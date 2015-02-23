package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor;

import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.HallwayLocation;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.OfficeLocation;
import lombok.Getter;

public class GroundFloorLevel implements Level {

    @Getter protected String name = "Erdgeschoss";


    @Getter protected Coordinate start;


    protected Location[][] locations;

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

        start = new Coordinate(3, 4);

        locations = new Location[6][5];

        // Office
        locations[0][0] = new OfficeLocation();
        locations[0][1] = new OfficeLocation();
        locations[0][2] = new OfficeLocation();
        locations[0][3] = new OfficeLocation();
        locations[0][4] = new OfficeLocation();

        locations[1][0] = new OfficeLocation();
        locations[1][1] = new OfficeLocation();
        locations[1][2] = new OfficeLocation();
        locations[1][3] = new OfficeLocation();
        locations[1][4] = new OfficeLocation();

        // Hallway
        locations[0][4] = new HallwayLocation();
        locations[1][4] = new HallwayLocation();
        locations[2][4] = new HallwayLocation();
        locations[3][4] = new HallwayLocation();
        locations[4][4] = new HallwayLocation();

    }

    @Override
    public Location getAt(Coordinate coordinate) {
        return locations[coordinate.getX()][coordinate.getY()];
    }

    /**
     * Close the level aka. the Player leaves the level
     */
    @Override
    public void close() {

    }
}
