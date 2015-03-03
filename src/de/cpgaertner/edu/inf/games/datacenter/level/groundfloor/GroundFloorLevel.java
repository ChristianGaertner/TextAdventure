package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.LocationFactory;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.InteractionRoutine;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.*;
import lombok.Getter;

import java.io.IOException;
import java.util.Date;

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


                int x = coordinate.getX();
                int y = coordinate.getY();

                // Walls
                if (coordinate.equals(0, 3)) {
                    location.setWest(new WallLocation());
                }

                if (coordinate.equals(0, 0)) {
                    location.setWest(new WallLocation());
                }

                if (coordinate.equals(1, 0)) {
                    location.setEast(new WallLocation());
                }

                // Computer
                if (y == 0 && x <= 1) {
                    location.setNorth(new ComputerLocation());
                }

                // CableBoxes
                if (x == 0 && (y == 1 || y == 2)) {
                    location.setWest(new CableBoxLocation());
                }

                // Monitor
                if (coordinate.equals(1, 3)) {
                    location.setEast(new MonitorscreenLocation());
                    location.setSouth(new MonitorscreenLocation());
                }

                // Whiteboard
                if (coordinate.equals(1, 2)) {
                    location.setEast(new WhiteboardLocation());
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
                if (x >= 2 && x <= 4 && y != 3) {
                    location.setEast(new ServerRackLocation().useDefaultRoutine());
                }

                if (x >= 3 && x <= 5 && y != 3) {
                    location.setWest(new ServerRackLocation().useDefaultRoutine());
                }

                // Fuseboxes
                if (x == 5 && y < 3) {
                    location.setEast(new FuseboxLocation());
                    if (y == 0) {
                        location.setNorth(new FuseboxLocation());
                    }
                }

                // Vent
                if (coordinate.equals(5, 3)) {
                    location.setEast(new VentLocation());
                }

                // Switches
                if (coordinate.equals(2, 0) || coordinate.equals(2, 2)) {
                    location.setWest(new SwitchLocation());
                    if (y == 0) {
                        location.setNorth(new SwitchLocation());
                    }
                }

                // Router
                if (coordinate.equals(2, 3)) {
                    location.setWest(new RouterLocation());
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


        ComputerLocation computer = (ComputerLocation) locations[0][0].getNorth();

        computer.setRoutine(new InteractionRoutine() {
            @Override
            public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {

                adapter.send("Locked. Please login.");

                adapter.sendf("Username: %s", player.getName());

                adapter.send("Password hint:");
                adapter.send("Password is 8 digits long, and contains the following numbers");
                adapter.send("2x '1'");
                adapter.send("2x '2'");
                adapter.send("2x '3'");
                adapter.send("2x '4'");
                adapter.send("The '1's have to be separated by 1 digit,");
                adapter.send("The '2's have to be seperated by 2 digit,");
                adapter.send("The '3's have to be seperated by 3 digit,");
                adapter.send("The '4's have to be seperated by 4 digit.");

                String pwd = "INITIAL_WRONG_STRING";

                while (!(pwd.equalsIgnoreCase("23421314") || pwd.equalsIgnoreCase("41312432"))) {
                    if (!pwd.equals("INITIAL_WRONG_STRING")) {
                        adapter.send("Wrong password.");
                    }
                    pwd = adapter.read("password: ");
                }

                Date date = new Date();

                adapter.send("Login successful. Use 'log' to see the login history");

                boolean run = true;
                while (run) {
                    String command = adapter.read("$");

                    if (command.equalsIgnoreCase("log")) {
                        adapter.send("Access log:");
                        adapter.sendf("%s >> %s", player.getName(), date.toString());
                        adapter.sendf("%s >> %s", "Bob", new Date(date.getTime() - 1000 * 60 * 45).toString());
                        adapter.sendf("%s >> %s", "%$&TFKΩ¢", new Date(date.getTime() - 1000 * 60 * 60 * 12).toString());

                        adapter.send("Error accessing log, invalid string for username given. Use 'repair log' and run 'log' again");

                    } else if (command.equalsIgnoreCase("repair log")) {
                        adapter.send("Invalid String found: '%$&TFKΩ¢'");
                        adapter.send("Matching against user-database");
                        adapter.send("Solving...");

                        for (int i = 0; i < 500; i++) {
                            adapter.put(".");
                        }

                        adapter.send("Solved.");
                        adapter.send("Access log has been repaired!");
                    }


                }


                return false;
            }
        });


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
