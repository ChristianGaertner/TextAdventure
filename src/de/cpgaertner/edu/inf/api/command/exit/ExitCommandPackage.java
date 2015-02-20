package de.cpgaertner.edu.inf.api.command.exit;

import de.cpgaertner.edu.inf.api.routine.CommandPackage;
import lombok.Data;

@Data
public class ExitCommandPackage implements CommandPackage<ExitCommand> {

    protected ExitCommandParser parser = new ExitCommandParser();

    protected ExitCommandHandler handler = new ExitCommandHandler();

    protected Class<ExitCommand> command = ExitCommand.class;

}
