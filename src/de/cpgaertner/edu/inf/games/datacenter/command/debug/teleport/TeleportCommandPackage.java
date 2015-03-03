package de.cpgaertner.edu.inf.games.datacenter.command.debug.teleport;

import de.cpgaertner.edu.inf.api.routine.CommandPackage;
import lombok.Data;

@Data
public class TeleportCommandPackage implements CommandPackage<TeleportCommand> {

    protected TeleportCommandParser parser = new TeleportCommandParser();

    protected TeleportCommandHandler handler = new TeleportCommandHandler();

    protected Class<TeleportCommand> command = TeleportCommand.class;

}
