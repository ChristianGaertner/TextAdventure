package de.cpgaertner.edu.inf.api.level.player;

import de.cpgaertner.edu.inf.api.level.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    @Getter @Setter protected List<Item> items;

    @Getter protected final int slots;

    public Inventory(int slots) {
        this.slots = slots;
        this.items = new ArrayList<Item>(slots);
    }


    public void add(Item item) throws InsufficientInventorySpaceException{
        if (remaining() == 0) {
            throw new InsufficientInventorySpaceException();
        }
        getItems().add(item);
    }

    public void remove(Item item) {
        getItems().remove(item);
    }

    public Item getFirst(String name) {
        List<Item> matches = get(name);
        if (matches.isEmpty()) return null;

        return matches.get(0);
    }

    public List<Item> get(String name) {
        // Limit initial size to the size of total items
        List<Item> matching = new ArrayList<Item>(getItems().size());
        for (Item i : getItems()) {
            if (i.getName().equalsIgnoreCase(name)) {
                matching.add(i);
            }
        }

        return matching;
    }

    public int remaining() {
        return getSlots() - getItems().size();
    }

    public int size() {
        return getItems().size();
    }

    public void clear() {
        getItems().clear();
    }

}
