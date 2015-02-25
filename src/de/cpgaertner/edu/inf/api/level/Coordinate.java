package de.cpgaertner.edu.inf.api.level;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 0,0 is the top left corner
 * x rises to east and falls to west
 * y rises to south and falls to north
 */
@Data
@AllArgsConstructor
public class Coordinate {

    protected int x, y;


    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

    public Coordinate getNorth() {
        return new Coordinate(getX(), getY() - 1);
    }

    public Coordinate getEast() {
        return new Coordinate(getX() + 1, getY());
    }

    public Coordinate getSouth() {
        return new Coordinate(getX(), getY() + 1);
    }

    public Coordinate getWest() {
        return new Coordinate(getX() - 1, getY());
    }

    public Coordinate get(Location.Direction direction) {
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
