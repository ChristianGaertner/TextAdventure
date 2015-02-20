package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command.go;

import de.cpgaertner.edu.inf.api.routine.CommandPackage;
import lombok.Data;

@Data
public class GoCommandPackage implements CommandPackage<GoCommand> {

    protected GoCommandParser parser = new GoCommandParser();

    protected GoCommandHandler handler = new GoCommandHandler();

    protected Class<GoCommand> command = GoCommand.class;

}
