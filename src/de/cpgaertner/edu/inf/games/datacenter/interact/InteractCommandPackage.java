package de.cpgaertner.edu.inf.games.datacenter.interact;

import de.cpgaertner.edu.inf.api.routine.CommandPackage;
import lombok.Data;

@Data
public class InteractCommandPackage implements CommandPackage<InteractCommand> {

    protected InteractCommandParser parser = new InteractCommandParser();

    protected InteractCommandHandler handler = new InteractCommandHandler();

    protected Class<InteractCommand> command = InteractCommand.class;

}
