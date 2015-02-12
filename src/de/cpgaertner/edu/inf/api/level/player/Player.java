package de.cpgaertner.edu.inf.api.level.player;

import de.cpgaertner.edu.inf.api.level.Item;
import de.cpgaertner.edu.inf.api.level.Location;

import java.util.Map;

public interface Player {

    public Object getMetaData(String key);

    public void setMetaData(String key, Object data);

    public Map<String, Object> getMetaData();

    public void setMetaData(Map<String, Object> data);

    public String getName();

    public Location getLocation();

    public Inventory getInventory();

    /**
     * Might be null, if hand is empty
     * @return Item in Hand | null
     */
    public Item getItemInHand();

}
