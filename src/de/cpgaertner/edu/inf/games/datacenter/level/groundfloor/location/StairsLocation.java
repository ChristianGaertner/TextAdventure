package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location;

import de.cpgaertner.edu.inf.api.level.BaseLocation;
import de.cpgaertner.edu.inf.api.level.Level;
import lombok.Getter;
import lombok.Setter;

public class StairsLocation extends BaseLocation {

    @Getter @Setter protected Level up;
    @Getter @Setter protected Level down;

}
