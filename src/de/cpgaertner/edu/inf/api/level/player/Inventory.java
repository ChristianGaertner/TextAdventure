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

    public boolean isEmpty() {
        return getItems().isEmpty();
    }

    public void remove(int slot) {
        getItems().remove(slot);
    }

    public void clear() {
        getItems().clear();
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (getItems().isEmpty()) {
            return "Inventory is empty!";
        }

        for (int i = 0; i < getSlots(); i++) {
            builder
                .append(i)
                .append(": ");
            Item item = getItems().get(i);

            if (item == null) {
                builder.append("<none>");
            } else {
                builder
                    .append(item.getName())
                    .append(" (")
                    .append(item.getClass().getSimpleName())
                    .append(")\n");
            }
        }

        return builder.toString();
    }
}
