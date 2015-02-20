package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command.look;

import de.cpgaertner.edu.inf.api.routine.CommandPackage;
import lombok.Data;

@Data
public class LookCommandPackage implements CommandPackage<LookCommand> {

    protected LookCommandParser parser = new LookCommandParser();

    protected LookCommandHandler handler = new LookCommandHandler();

    protected Class<LookCommand> command = LookCommand.class;
}
