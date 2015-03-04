package de.cpgaertner.edu.inf.games.datacenter.command.talk;

import de.cpgaertner.edu.inf.api.routine.CommandPackage;
import lombok.Data;

@Data
public class TalkCommandPackage implements CommandPackage<TalkCommand> {

    protected TalkCommandParser parser = new TalkCommandParser();

    protected TalkCommandHandler handler = new TalkCommandHandler();

    protected Class<TalkCommand> command = TalkCommand.class;
}
