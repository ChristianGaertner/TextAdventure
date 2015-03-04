package de.cpgaertner.edu.inf.games.datacenter.command.talk;

import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;

public class TalkCommandHandler implements CommandHandler<TalkCommand> {
    @Override
    public Routine handle(Player player, TalkCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;

        boolean run = true;

        while (run) {

            Thought thought = think(cmd.getAdapter().read(">"));

            cmd.getAdapter().send(thought.getResponse());
            if (thought.getFollowup() != null) {
                cmd.getAdapter().send(thought.getFollowup());
            }

            if (thought.isExit()) {
                run = false;
            }
        }

        return null;

    }



    protected Thought think(String string) {
        return new Thought(
                false,
                string,
                "WRONG",
                "TOTALLY!"
        );
    }

    @Data @AllArgsConstructor public final class Thought {
        protected boolean exit;
        protected String input;
        protected String response;
        protected String followup;
    }
}
