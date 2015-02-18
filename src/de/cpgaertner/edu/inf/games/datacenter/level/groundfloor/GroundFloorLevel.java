package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor;

import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.DoorLocation;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.OfficeLocation;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.WallLocation;
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
        Level is a 5x4 Grid (without the walls
         */


        DoorLocation officeHallwayConnector = new DoorLocation();
        DoorLocation hallwayServerRoomConnector = new DoorLocation();

        generateOffice(officeHallwayConnector);
        generateHallway(officeHallwayConnector, hallwayServerRoomConnector);



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

    protected void generateHallway(DoorLocation toOffice, DoorLocation toServerRoom) {

    }
}
