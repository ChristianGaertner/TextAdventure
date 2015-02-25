package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.BaseLocation;
import de.cpgaertner.edu.inf.api.level.Item;
import de.cpgaertner.edu.inf.api.level.player.InsufficientInventorySpaceException;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.InteractionRoutine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class DoorLocation extends BaseLocation {

    @Data
    @AllArgsConstructor
    public class Key implements Item {

        protected String name;

    }


    @Getter protected boolean open;

    protected Key key;

    public Key generateKey(boolean force) {
        if (key == null || force) {
            return this.key = new Key("Key <> " + getName() + "");
        }

        throw new IllegalStateException("A key already exists for this door: <" + key + ">");
    }

    public boolean lock() {
        if (isOpen()) {
            return false;
        }
        this.open = false;

        return true;
    }

    public boolean openWith(Key key) {
        return this.key == key && (open = true);
    }

    @AllArgsConstructor
    public static class DefaultRoutine extends InteractionRoutine {

        @Getter @Setter
        protected DoorLocation door;

        @Override
        public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {

            adapter.sendf("This door is %s.",
                    door.isOpen() ? "open" : "locked"
            );

            if (door.isOpen()) {

                switch (askQuestion("Do you want to lock it?", "yes", "no", adapter)) {
                    case ONE:
                        Key key = null;
                        try {
                            key = door.generateKey(false);
                        } catch (IllegalStateException e) {
                            adapter.send("You need to provide a key to lock this door!");
                            return false;
                        }

                        door.lock();

                        try {
                            player.getInventory().add(key);
                        } catch (InsufficientInventorySpaceException e) {
                            e.printStackTrace();
                        }
                        break;
                    case TWO: /* falls through */
                    default:
                        adapter.send("Ok, then go ahead!");
                }

            } else {

                switch (askQuestion("Do you want to open it?", "yes", "no", adapter)) {
                    case ONE:
                        adapter.send("Please choose a key!");
                        adapter.send(player.getInventory().toString());
                        String slot = adapter.read("slot #:");
                        adapter.send("Key opening is not implemented yet... Sorry!");
                        break;
                    case TWO: /* falls through */
                    default:
                        adapter.send("Ok, then go ahead!");

                }



            }

            return false;
        }
    }

}
