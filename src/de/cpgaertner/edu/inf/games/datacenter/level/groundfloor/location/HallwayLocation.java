package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location;

import de.cpgaertner.edu.inf.api.level.BaseLocation;

public class HallwayLocation extends BaseLocation {

    public HallwayLocation(int lightLevel) {
        setLightLevel(lightLevel);
        setRoutine(routine);
        setWalkable(true);
    }

    public HallwayLocation() {
        this(10);
    }
}
