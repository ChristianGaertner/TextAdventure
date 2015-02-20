package de.cpgaertner.edu.inf.api.level.player;

import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Item;
import de.cpgaertner.edu.inf.api.level.Level;
import lombok.Data;

import java.util.Map;

@Data
public class DefaultPlayer implements Player {

    protected Map<String, Object> metaData;

    protected String name;

    protected Level level;

    protected Coordinate position;

    protected Inventory inventory;

    protected Item itemInHand;


    @Override
    public Object getMetaData(String key) {
        return getMetaData().get(key);
    }

    @Override
    public void setMetaData(String key, Object data) {
        getMetaData().put(key, data);
    }
}
