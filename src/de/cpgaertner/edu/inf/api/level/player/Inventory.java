package de.cpgaertner.edu.inf.api.level.player;

import de.cpgaertner.edu.inf.api.level.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    @Getter @Setter protected Map<Integer, Item> items;

    @Getter protected final int slots;

    public Inventory(int slots) {
        this.slots = slots;
        this.items = new HashMap<>(slots);
    }


    public void add(int slot, Item item) throws InsufficientInventorySpaceException {
        if (getItems().get(slot) != null) {
            throw new InsufficientInventorySpaceException();
        }
        getItems().put(slot, item);
    }

    public void add(Item item) throws InsufficientInventorySpaceException {
        add(getItems().size(), item);
    }

    public void remove(int slot) {
        getItems().remove(slot);
    }

    public void clear() {
        getItems().clear();
    }

}
