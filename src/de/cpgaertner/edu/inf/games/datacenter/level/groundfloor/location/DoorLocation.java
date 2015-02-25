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

    @Override
    public DoorLocation useDefaultRoutine() {
        setRoutine(new DefaultRoutine(this));
        return this;
    }

    @AllArgsConstructor
    public static class DefaultRoutine extends InteractionRoutine {

        @Getter @Setter
        protected DoorLocation door;

        @Override
        public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {

            adapter.sendf("This door is %s.",
                    getDoor().isOpen() ? "open" : "locked"
            );

            if (getDoor().isOpen()) {
                openDoorRoutine(player, adapter);
            } else {
                lockDoorRoutine(player, adapter);
            }

            return false;
        }


        protected void openDoorRoutine(Player player, Adapter adapter) throws IOException {
            switch (askYesNoQuestion("Do you want to lock it?", adapter)) {
                case YES:
                    Key key = null;
                    try {
                        key = door.generateKey(false);
                    } catch (IllegalStateException e) {
                        adapter.send("You need to provide a key to lock this door!");
                        return;
                    }

                    door.lock();

                    try {
                        player.getInventory().add(key);
                    } catch (InsufficientInventorySpaceException e) {
                        e.printStackTrace();
                    }
                    break;
                case NO: /* falls through */
                default:
                    adapter.send("Ok, then go ahead!");
            }
        }

        protected void lockDoorRoutine(Player player, Adapter adapter) throws IOException {

            switch (askYesNoQuestion("Do you want to open it?", adapter)) {
                case YES:
                    adapter.send("Please choose a key!");
                    if (player.getInventory().isEmpty()) {
                        adapter.send("Looks like your inventory is empty. Then you cannot open this door, find the key first!");
                        return;
                    }
                    adapter.send(player.getInventory().toString());

                    boolean answerPending = true;
                    int slot = 0;
                    while (answerPending) {
                        String slotString = adapter.read("slot #:");
                        try {
                            slot = Integer.parseInt(slotString);
                            answerPending = false;
                        } catch (NumberFormatException e) {
                            answerPending = true;
                        }

                    }

                    Item i = player.getInventory().getItems().get(slot);
                    if (i instanceof Key) {
                        boolean properKey = getDoor().openWith((Key) i);

                        if (properKey) {
                            adapter.send("The door is now open");
                        } else {
                            adapter.send("You provided the wrong key!");
                        }

                    } else {
                        adapter.send("None key item provided");
                    }

                    break;
                case NO: /* falls through */
                default:
                    adapter.send("Ok, then go ahead!");

            }

        }

    }

}
