package de.cpgaertner.edu.inf.games.datacenter.command.debug.debugger;

import de.cpgaertner.edu.inf.api.routine.CommandPackage;
import lombok.Data;

@Data
public class DebuggerCommandPackage implements CommandPackage<DebuggerCommand> {

    protected DebuggerCommandParser parser = new DebuggerCommandParser();

    protected DebuggerCommandHandler handler = new DebuggerCommandHandler();

    protected Class<DebuggerCommand> command = DebuggerCommand.class;

}
