package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location;

import de.cpgaertner.edu.inf.api.level.BaseLocation;
import lombok.Getter;

public class DoorLocation extends BaseLocation {

    public class Key {}


    @Getter protected boolean open;

    protected Key key;

    public Key generateKey(boolean force) {
        if (key == null || force) {
            return this.key = new Key();
        }

        throw new IllegalStateException("A key already exists for this door: <" + key + ">");
    }

    public boolean openWith(Key key) {
        return this.key == key && (open = true);
    }

}
