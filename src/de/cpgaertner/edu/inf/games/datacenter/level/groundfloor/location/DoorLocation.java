package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.BaseLocation;
import de.cpgaertner.edu.inf.api.level.Item;
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
        if (!isOpen()) {
            return false;
        }
        this.open = false;

        return true;
    }

    public void forceOpen() {
        this.open = true;
    }

    public boolean openWith(Key key) {
        return this.key == key && (open = true);
    }

    @Override
    public DoorLocation useDefaultRoutine() {
        setRoutine(new DefaultRoutine(this));
        return this;
    }

    @Override
    public boolean isWalkable() {
        return isOpen();
    }

    @Override
    public String toString() {
        return "DoorLocation[open=" + isOpen() + "]>>" + super.toString();
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
                lockedDoorRoutine(player, adapter);
            }

            return false;
        }


        protected void openDoorRoutine(Player player, Adapter adapter) throws IOException {
            switch (askYesNoQuestion("Do you want to lock it?", adapter)) {
                case YES:
                    door.lock();
                    adapter.send("The door is now locked.");
                    break;
                default:
                    adapter.send("Ok, then go ahead!");
            }
        }

        protected void lockedDoorRoutine(Player player, Adapter adapter) throws IOException {

            switch (askYesNoQuestion("Do you want to open it?", adapter)) {
                case YES:
                    Key k = chooseKey(player, adapter);

                    if (k == null) {
                        adapter.send("I try it without key then");
                    }

                    boolean properKey = getDoor().openWith(k);

                    if (properKey) {
                        adapter.send("The door is now open");
                    } else {
                        adapter.send("You provided the wrong key!");
                    }

                    break;
                default:
                    adapter.send("Ok, then go ahead!");

            }

        }

        protected Key chooseKey(Player player, Adapter adapter) throws IOException {
            adapter.send("Please choose a key!");

            ItemSlotPaylod i = getItem(InventoryResponseSuite.DEFAULT, player, adapter);

            if (i == null) {
                return null;
            }

            if (i.getItem() instanceof Key) {
                return (Key) i.getItem();
            } else {
                adapter.send("None key item provided");
            }

            return null;
        }

    }

}
