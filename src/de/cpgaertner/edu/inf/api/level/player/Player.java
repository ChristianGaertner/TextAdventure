package de.cpgaertner.edu.inf.api.level.player;

import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Item;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;

import java.util.Map;

public interface Player {


    public static final String KEY_AGE = "age";

    public Object getMetaData(String key);

    public void setMetaData(String key, Object data);

    public int getMetaValue(String key);

    public void setMetaValue(String key, int data);

    public Map<String, Object> getMetaData();

    public void setMetaData(Map<String, Object> data);

    public String getName();

    public void setName(String name);

    public Coordinate getPosition();

    public void setPosition(Coordinate coordinate);

    public Inventory getInventory();

    public Level getLevel();

    public void setLevel(Level level);

    /**
     * Wrapper for
     * <code>player.getLevel().getAt(player.getPosition().get(direction))</code>
     * @param direction direction of interest
     * @return location in that direction, relative to the player
     */
    public Location get(Location.Direction direction);

    /**
     * Might be null, if hand is empty
     * @return Item in Hand | null
     */
    public Item getItemInHand();

    public void setItemInHand(Item item);

}
