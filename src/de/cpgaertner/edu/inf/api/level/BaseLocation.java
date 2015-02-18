package de.cpgaertner.edu.inf.api.level;

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
}
