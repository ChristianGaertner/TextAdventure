package de.cpgaertner.edu.inf.api.level.player;

import de.cpgaertner.edu.inf.api.level.Item;
import de.cpgaertner.edu.inf.api.level.Location;
import lombok.Data;

import java.util.Map;

@Data
public class DefaultPlayer implements Player {

    protected Map<String, Object> metaData;

    protected String name;

    protected Location location;

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
