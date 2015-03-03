package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor;

import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.LocationFactory;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.*;
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

        generate(new LocationFactory() {
            @Override
            public Location generate(Coordinate coordinate) {
                OfficeLocation location = new OfficeLocation();

                // Walls
                if (coordinate.getX() == 0) {
                    location.setWest(new WallLocation());
                }

                if (coordinate.getX() == 1) {
                    location.setEast(new WallLocation());
                }

                if (coordinate.getY() == 0) {
                    location.setNorth(new WallLocation());
                }

                if (coordinate.equals(1, 3)) {
                    location.setSouth(new WallLocation());
                }


                return location;
            }
        }, 0, 1, 0, 3);

        generate(new LocationFactory() {
            @Override
            public Location generate(Coordinate coordinate) {
                HallwayLocation location = new HallwayLocation();
                location.setSouth(new WallLocation());
                location.setNorth(new WallLocation());

                if (coordinate.equals(0, 4)) {
                    location.setWest(new WallLocation());
                }

                return location;
            }
        }, 0, 4, 4, 4);

        generate(new LocationFactory() {
            @Override
            public Location generate(Coordinate coordinate) {
                ServerRoomLocation location = new ServerRoomLocation();

                int x = coordinate.getX();
                int y = coordinate.getY();


                // Walls
                if (x == 2) {
                    location.setWest(new WallLocation());
                }

                if (x == 5) {
                    location.setEast(new WallLocation());
                }

                if (y == 3 && x != 3) {
                    location.setSouth(new WallLocation());
                }

                if (y == 0) {
                    location.setNorth(new WallLocation());
                }

                // Server
                if ((x == 2 || x == 3 || x == 4) && y != 3) {
                    location.setEast(new ServerRackLocation().useDefaultRoutine());
                }

                if ((x == 3 || x == 4 || x == 5) && y != 3) {
                    location.setWest(new ServerRackLocation().useDefaultRoutine());
                }


                return location;
            }
        }, 2, 5, 0, 3);

        // Stairs
        StairsLocation stairs = new StairsLocation().useDefaultRoutine();

        locations[5][4] = stairs;


        // Doors
        DoorLocation officeHallway = new DoorLocation().useDefaultRoutine();
        DoorLocation hallwayServer = new DoorLocation().useDefaultRoutine();

        // This door should be open at launch.
        officeHallway.forceOpen();


        locations[0][3].setSouth(officeHallway);
        locations[0][4].setNorth(officeHallway);

        locations[3][3].setSouth(hallwayServer);
        locations[3][4].setNorth(hallwayServer);


        // Window
        WindowLocation window = new WindowLocation();

        locations[1][1].setEast(window);
        locations[2][1].setWest(window);

    }

    @Override
    public Location getAt(Coordinate coordinate) {
        return locations[coordinate.getX()][coordinate.getY()];
    }

    /**
     * Returns the lower right corner of the level
     *
     * @return lower right corner
     */
    @Override
    public Coordinate getBound() {
        return new Coordinate(6, 5);
    }

    /**
     * Close the level aka. the Player leaves the level
     */
    @Override
    public void close() {

    }

    protected void generate(LocationFactory factory, int x1, int x2, int y1, int y2) {
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                locations[x][y] = factory.generate(new Coordinate(x, y));
            }
        }
    }
}
