package de.cpgaertner.edu.inf.games.datacenter.command.position;

import de.cpgaertner.edu.inf.api.routine.CommandPackage;
import lombok.Data;

@Data
public class PositionCommandPackage implements CommandPackage<PositionCommand> {

    protected PositionCommandParser parser = new PositionCommandParser();

    protected PositionCommandHandler handler = new PositionCommandHandler();

    protected Class<PositionCommand> command = PositionCommand.class;
}
