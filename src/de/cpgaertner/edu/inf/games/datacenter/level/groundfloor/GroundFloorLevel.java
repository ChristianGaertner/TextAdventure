package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor;

import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.HallwayLocation;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.OfficeLocation;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.ServerRoomLocation;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.StairsLocation;
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

        try {
            generate(OfficeLocation.class, 0, 1, 0, 3);
            generate(HallwayLocation.class, 0, 4, 4, 4);
            generate(ServerRoomLocation.class, 2, 5, 0, 3);

        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        // Stairs
        locations[5][4] = new StairsLocation();

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

    protected void generate(Class<? extends Location> type, int x1, int y1, int x2, int y2) throws IllegalAccessException, InstantiationException {
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                locations[x][y] = type.newInstance();
            }
        }
    }
}
