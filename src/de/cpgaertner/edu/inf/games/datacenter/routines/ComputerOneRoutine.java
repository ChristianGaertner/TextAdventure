package de.cpgaertner.edu.inf.games.datacenter.routines;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.InteractionRoutine;

import java.io.IOException;
import java.util.Date;

import static de.cpgaertner.edu.inf.api.CoreEngine.quickSleep;

public class ComputerOneRoutine extends InteractionRoutine {

    public static final String KEY_RACK_STATUS_1 = "RACK_STATUS_1";
    public static final String KEY_RACK_STATUS_2 = "RACK_STATUS_2";
    public static final String KEY_RACK_STATUS_3 = "RACK_STATUS_3";

    public static final String KEY_RACK_CONN_1 = "RACK_CONN_1";
    public static final String KEY_RACK_CONN_2 = "RACK_CONN_2";
    public static final String KEY_RACK_CONN_3 = "RACK_CONN_3";

    public static final int RACK_STATUS_SUSPENDED_INVALIDCONF = 0xA0000;
    public static final int RACK_STATUS_CRASH_MISSING_HDD = 0xA00001;
    public static final int RACK_STATUS_SUS_MISSING_HDD = 0xA00002;
    public static final int RACK_STATUS_BOOTED = 0xA00003;

    public static final int RACK_DISCONNECTED = 0xB00000;
    public static final int RACK_CONNECTED = 0xB00001;

    @Override
    public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {

        
        loginRoutine(player, adapter);
        
        Date login = new Date();

        adapter.send("Login successful. Use 'log' to see the login history");

        init(player, adapter);

        prompt(login, player, adapter);


        return false;
    }
    
    protected static void prompt(Date login, Player player, Adapter adapter) throws IOException {
        boolean run = true;

        boolean gameplay_logrepaired = false;
        
        while (run) {
            String command = adapter.read("$");


            if (command.equalsIgnoreCase("exit")) {
                adapter.send("Leaving computer screen...");
                run = false;
            } else if (command.equalsIgnoreCase("log")) {
                adapter.send("Access log:");
                adapter.sendf("%s >> %s", player.getName(), login.toString());
                adapter.sendf("%s >> %s", "bob", new Date(login.getTime() - 1000 * 60 * 45).toString());

                if (gameplay_logrepaired) {
                    adapter.sendf("%s >> %s", "peter", new Date(login.getTime() - 1000 * 60 * 60 * 12).toString());

                    adapter.send("View command history by running 'history peter'");

                } else {
                    adapter.sendf("%s >> %s", "%$&TFKΩ¢", new Date(login.getTime() - 1000 * 60 * 60 * 12).toString());

                    adapter.send("Error accessing log, invalid string for username given. Use 'repair log' and run 'log' again");
                }

            } else if (command.equalsIgnoreCase("repair log")) {
                adapter.send("Invalid String found: '%$&TFKΩ¢'");
                adapter.send("Matching against user-database");

                adapter.put("Solving");
                for (int i = 0; i < 10; i++) {

                    quickSleep(100);

                    adapter.put(".");
                }

                adapter.send("");
                adapter.send(">>Solved.");
                adapter.send("Access log has been repaired! Re-run 'log' now!");
                gameplay_logrepaired = true;

            } else if (command.equalsIgnoreCase("history peter")) {

                adapter.send("./login.sh");
                adapter.send("./monitor.sh SERVER RACK 1");
                adapter.send("./shutdown.sh SERVER RACK 1");
                adapter.send("./access_ctrl.sh DENY ALL REMOTE");

                adapter.send("===");

                adapter.send("Looks like the hacker has denied all remote access to the server and shutdown the first server rack");
                adapter.send("Before rebooting the server and allowing remote ctrl, you should check what the server is doing.");
                adapter.send("Run './monitor.sh SERVER RACK 1");


            } else if (command.equalsIgnoreCase("./monitor.sh SERVER RACK 1")) {

                adapter.send("SERVER RACK 1.");
                adapter.send("TRACEROUTE:");
                adapter.send("NETWORK CARD");
                adapter.send("SWITCH - ROUTER");
                adapter.send("SWITCH - 1");
                adapter.send("===");

                if (((Integer) player.getMetaData(KEY_RACK_STATUS_1)) == RACK_STATUS_SUSPENDED_INVALIDCONF) {
                    adapter.send("STATUS: SUSPENDED");
                    adapter.put("Scanning OS files");

                    for (int i = 0; i < 20; i++) {
                        quickSleep(100);
                        adapter.put(".");
                    }

                    adapter.send("");

                    adapter.send("WARNING: files out of sync with configuration.");
                    adapter.send("REMOVE THE HARDDRIVE BEFORE BOOTING!");
                    adapter.send("HHD located at 'RACK:1#EAST#1#3'");
                    adapter.send("");
                    adapter.send("HINT: Rack locations are as follows:");
                    adapter.send("RACK:_number_#_direction_#_y-coordinate_#_server-number_");
                    adapter.send("Removing a HDD is a manual process, you have to physically stand in front of the server.");

                } else if (((Integer) player.getMetaData(KEY_RACK_STATUS_1)) == RACK_STATUS_CRASH_MISSING_HDD) {

                    adapter.send("STATUS: CRASHED");
                    adapter.send("MISSING HDD @ RACK:1#EAST#1#3");
                    adapter.send("Insert new HDD and then run './configure.sh SERVER RACK 1'");

                } else if (((Integer) player.getMetaData(KEY_RACK_STATUS_1)) == RACK_STATUS_BOOTED) {
                    adapter.send("STATUS: BOOTED");

                    adapter.put("Testing connectivity");

                    for (int i = 0; i < 20; i++) {
                        quickSleep(100);
                        adapter.put(".");
                    }

                    adapter.send("");

                    if (((Integer) player.getMetaData(KEY_RACK_CONN_1)) == RACK_DISCONNECTED) {
                        adapter.send("WARNING: Packet Drop 100%.");
                        adapter.send("Make sure to allow remote access, by running:");
                        adapter.send("./access_ctrl.sh ALLOW ALL REMOTE");
                    } else if (((Integer) player.getMetaData(KEY_RACK_CONN_1)) == RACK_CONNECTED) {

                        adapter.send("Connected!");

                    } else {
                        panic(adapter, "INVALID CONN CODE: " + String.valueOf((int) player.getMetaData(KEY_RACK_CONN_1)));
                    }

                }

            } else if (command.equalsIgnoreCase("./configure.sh SERVER RACK 1")) {

                if (((Integer) player.getMetaData(KEY_RACK_STATUS_1)) == RACK_STATUS_SUS_MISSING_HDD) {
                    adapter.send("Configure SERVER RACK 1");
                    adapter.send("Missing HDD");
                    adapter.send("Abort.");
                } else {
                    panic(adapter);
                }

            } else if (command.equalsIgnoreCase("./boot.sh SERVER RACK 1")) {

                adapter.send("Starting boot sequence");

                for (int i = 0; i < 15; i++) {
                    quickSleep(100);
                    adapter.put(".");
                }

                adapter.send("");
                adapter.send("Booted.");
                adapter.send("Check status with './monitor.sh SERVER RACK 1'");
                adapter.send("Check DataCenter Status on the Status Monitor in this room!");

                if (((Integer) player.getMetaData(KEY_RACK_STATUS_1)) == RACK_STATUS_SUSPENDED_INVALIDCONF) {
                    player.setMetaData(KEY_RACK_STATUS_1, RACK_STATUS_BOOTED);
                } else if (((Integer) player.getMetaData(KEY_RACK_STATUS_1)) == RACK_STATUS_SUS_MISSING_HDD) {
                    player.setMetaData(KEY_RACK_STATUS_1, RACK_STATUS_CRASH_MISSING_HDD);
                } else {
                    player.setMetaData(KEY_RACK_STATUS_1, RACK_STATUS_BOOTED);
                }

            } else {
                adapter.sendf("No such command '%s'", command);
            }


        }
    }
    
    protected static void loginRoutine(Player player, Adapter adapter) throws IOException {
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
    }
    
    protected static void init(Player player, Adapter adapter) throws IOException {
        if (player.getMetaData(KEY_RACK_STATUS_1) == null) {
            player.setMetaData(KEY_RACK_STATUS_1, RACK_STATUS_SUSPENDED_INVALIDCONF);
            player.setMetaData(KEY_RACK_CONN_1, RACK_DISCONNECTED);
        }
    }

    protected static void panic(Adapter adapter) throws IOException {
        panic(adapter, "");
    }

    protected static void panic(Adapter adapter, String arg) throws IOException {
        adapter.sendf("KERNAL PANIC, %s", arg);
    }

}