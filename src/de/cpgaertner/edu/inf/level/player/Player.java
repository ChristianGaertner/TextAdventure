package de.cpgaertner.edu.inf.level.player;

import de.cpgaertner.edu.inf.level.Item;
import de.cpgaertner.edu.inf.level.Location;

public interface Player {

    public String getName();

    public Location getLocation();

    public Inventory getInventory();

    /**
     * Might be null, if hand is empty
     * @return Item in Hand | null
     */
    public Item getItemInHand();

}
