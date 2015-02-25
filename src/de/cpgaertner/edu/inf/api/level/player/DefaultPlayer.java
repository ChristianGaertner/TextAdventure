package de.cpgaertner.edu.inf.api.level.player;

import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Item;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.Location;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class DefaultPlayer implements Player {

    public static final int INITIAL_INVENTORY_SIZE = 2;

    protected Map<String, Object> metaData;

    protected String name;

    protected Level level;

    protected Coordinate position;

    protected Inventory inventory;

    protected Item itemInHand;

    public DefaultPlayer() {
        metaData = new HashMap<>();
        inventory = new Inventory(INITIAL_INVENTORY_SIZE);
    }

    @Override
    public Object getMetaData(String key) {
        return getMetaData().get(key);
    }

    @Override
    public void setMetaData(String key, Object data) {
        getMetaData().put(key, data);
    }

    /**
     * Wrapper for
     * <code>player.getLevel().getAt(player.getPosition().get(direction))</code>
     *
     * @param direction direction of interest
     * @return location in that direction, relative to the player
     */
    @Override
    public Location get(Location.Direction direction) {
        return getLevel().getAt(getPosition().get(direction));
    }
}
