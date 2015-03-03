package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.BaseLocation;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.InsufficientInventorySpaceException;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.InteractionRoutine;
import de.cpgaertner.edu.inf.api.routine.Routine;
import de.cpgaertner.edu.inf.games.datacenter.level.item.HardDrive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class ServerRackLocation extends BaseLocation {


    @Getter
    protected Server[] server;

    public ServerRackLocation() {
        setWalkable(false);
        server = new Server[5];
    }

    /**
     * Sets the Routine to the default routine, if there is any.
     *
     * @return self (chaining support)
     */
    @Override
    public Location useDefaultRoutine() {
        setRoutine(new DefaultRoutine(this));
        return this;
    }


    @AllArgsConstructor
    public static class DefaultRoutine extends InteractionRoutine {

        @Getter @Setter
        protected ServerRackLocation serverRack;

        @Override
        public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {
            adapter.sendf("This Rack houses %s server.", getServerRack().getServer().length);
            adapter.send("Which one do you want to access?");
            int servernumber = getInteger("server #:", 0, getServerRack().getServer().length, adapter);

            Server server = getServerRack().getServer()[servernumber];

            server.getRoutine().handle(player, null, adapter);

            return false;
        }
    }

    @Data public static class Server {

        protected HardDrive hdd;

        protected Routine routine = new InteractionRoutine() {
            @Override
            public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {
                if (hdd == null) {
                    adapter.send("This server doesn't hold a harddrive!");
                    switch (askYesNoQuestion("Do you want to insert a harddrive?", adapter)) {
                        case YES:
                            ItemSlotPaylod i = getItem(InventoryResponseSuite.DEFAULT, player, adapter);
                            if (i == null) {
                                return false;
                            }
                            if (i.getItem() instanceof HardDrive) {
                                setHdd((HardDrive) i.getItem());
                                player.getInventory().remove(i.getSlot());
                            } else {
                                adapter.send("This doesn't fit in here...");
                            }
                            break;
                        default:
                            adapter.send("Ok then go ahead");
                    }
                } else {
                    adapter.send("This server holds a harddrive!");
                    switch (askYesNoQuestion("Do you want to remove the harddrive?", adapter)) {
                        case YES:
                            try {
                                player.getInventory().add(getHdd());
                                setHdd(null);
                            } catch (InsufficientInventorySpaceException e) {
                                adapter.send("You do not have enough inventory space!");
                            }
                            break;
                        default:
                            adapter.send("Ok then go ahead!");
                    }
                }

                return false;
            }
        };

    }

}
