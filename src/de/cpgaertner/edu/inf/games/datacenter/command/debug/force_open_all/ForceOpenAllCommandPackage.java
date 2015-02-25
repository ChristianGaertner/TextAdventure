package de.cpgaertner.edu.inf.games.datacenter.command.debug.force_open_all;

import de.cpgaertner.edu.inf.api.routine.CommandPackage;
import lombok.Data;

@Data
public class ForceOpenAllCommandPackage implements CommandPackage<ForceOpenAllCommand> {

    protected ForceOpenAllCommandParser parser = new ForceOpenAllCommandParser();

    protected ForceOpenAllCommandHandler handler = new ForceOpenAllCommandHandler();

    protected Class<ForceOpenAllCommand> command = ForceOpenAllCommand.class;
}
