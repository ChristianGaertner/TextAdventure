package de.cpgaertner.edu.inf.api.level;

import de.cpgaertner.edu.inf.api.routine.Routine;
import lombok.Data;

@Data
public abstract class BaseLocation implements Location {

    protected String name;

    protected Location north;
    protected Location west;
    protected Location south;
    protected Location east;

    protected boolean walkable;

    protected int lightLevel;

    protected Routine routine;


    public void useDefaultRoutine() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location get(Direction direction) {
        switch (direction) {
            case NORTH:
                return getNorth();
            case EAST:
                return getEast();
            case SOUTH:
                return getSouth();
            case WEST:
                return getWest();
            default:
                throw new IllegalArgumentException("No such direction");
        }
    }
}
