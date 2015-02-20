package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor;

import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.*;
import lombok.Getter;

public class GroundFloorLevel implements Level {

    @Getter protected String name = "Erdgeschoss";


    @Getter protected Location start;



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

        /*
        Level is a 6x5 Grid (without the walls)
         */


        DoorLocation officeHallwayConnector = new DoorLocation();
        DoorLocation hallwayServerRoomConnector = new DoorLocation();
        DoorLocation hallwayOutsideConnector = new DoorLocation();


        StairsLocation stairs = new StairsLocation();

        generateOffice(officeHallwayConnector);
        this.start = generateHallway(officeHallwayConnector, hallwayServerRoomConnector, hallwayOutsideConnector, stairs);



    }

    /**
     * Close the level aka. the Player leaves the level
     */
    @Override
    public void close() {

    }

    protected void generateOffice(DoorLocation door) {
        OfficeLocation x0y0 = new OfficeLocation();
        OfficeLocation x0y1 = new OfficeLocation();
        OfficeLocation x0y2 = new OfficeLocation();
        OfficeLocation x0y3 = new OfficeLocation();

        OfficeLocation x1y0 = new OfficeLocation();
        OfficeLocation x1y1 = new OfficeLocation();
        OfficeLocation x1y2 = new OfficeLocation();
        OfficeLocation x1y3 = new OfficeLocation();


        // Door
        x0y3.setSouth(door);

        { // Walls
            x0y0.setNorth(new WallLocation());
            x1y0.setNorth(new WallLocation());

            x1y1.setNorth(new WallLocation()); // TODO: change to "Wall w/ Window"

            x0y0.setWest(new WallLocation());
            x0y1.setWest(new WallLocation());
            x0y2.setWest(new WallLocation());
            x0y3.setWest(new WallLocation());
        }

        { // Interconnection
            // Left side
            x0y0.setEast(x1y0);
            x0y0.setSouth(x0y1);

            x0y1.setNorth(x0y0);
            x0y1.setEast(x1y1);
            x0y1.setSouth(x0y2);

            x0y2.setNorth(x0y1);
            x0y2.setEast(x1y2);
            x0y2.setSouth(x0y3);

            x0y3.setNorth(x0y2);
            x0y3.setEast(x1y2);

            // Right side
            x1y0.setWest(x0y0);
            x1y0.setSouth(x1y1);

            x1y1.setNorth(x1y0);
            x1y1.setWest(x0y1);
            x1y1.setSouth(x1y2);

            x1y2.setNorth(x1y1);
            x1y2.setWest(x0y2);
            x1y2.setSouth(x1y3);

            x1y3.setNorth(x1y2);
            x1y3.setWest(x0y3);
        }
    }

    /**
     * Generates and returns the start location
     * @param toOffice office connector
     * @param toServerRoom server room connector
     * @param toOutside outside connector
     * @param stairsLocation stairs to use
     * @return Level Spawn
     */
    protected HallwayLocation generateHallway(DoorLocation toOffice, DoorLocation toServerRoom, DoorLocation toOutside, StairsLocation stairsLocation) {

        HallwayLocation x0y4 = new HallwayLocation();
        HallwayLocation x1y4 = new HallwayLocation();
        HallwayLocation x2y4 = new HallwayLocation();
        HallwayLocation x3y4 = new HallwayLocation();
        HallwayLocation x4y4 = new HallwayLocation();

        x0y4.setNorth(toOffice);
        x3y4.setNorth(toServerRoom);
        x3y4.setSouth(toOutside);

        { // Walls
            x0y4.setWest(new WallLocation());
            x0y4.setSouth(new WallLocation());

            x1y4.setNorth(new WallLocation());
            x1y4.setSouth(new WallLocation());

            x2y4.setNorth(new WallLocation());
            x2y4.setSouth(new WallLocation());

            x4y4.setNorth(new WallLocation());
            x4y4.setSouth(new WallLocation());
        }

        { // Interconnection
            x0y4.setEast(x1y4);

            x1y4.setEast(x0y4);
            x1y4.setWest(x2y4);

            x2y4.setEast(x1y4);
            x2y4.setWest(x3y4);

            x3y4.setEast(x2y4);
            x3y4.setWest(x4y4);

            x4y4.setEast(x1y4);
        }

        x4y4.setWest(stairsLocation);


        return x3y4;
    }
}
