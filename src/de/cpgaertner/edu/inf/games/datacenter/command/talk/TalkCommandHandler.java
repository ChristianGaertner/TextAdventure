package de.cpgaertner.edu.inf.games.datacenter.command.talk;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;
import de.cpgaertner.edu.inf.games.datacenter.command.talk.data.Greetings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.cpgaertner.edu.inf.games.datacenter.command.talk.TalkCommandHandler.State.*;
import static de.cpgaertner.edu.inf.games.datacenter.routines.ComputerOneRoutine.KEY_LOG_STATUS;
import static de.cpgaertner.edu.inf.games.datacenter.routines.ComputerOneRoutine.LOG_INVALID;

public class TalkCommandHandler implements CommandHandler<TalkCommand> {


    public static final String KEY_CONVERSATION_STATE = "TALK_CONV_STATE";

    public static final String RACK_POS_REGEX_STRING = ".*rack:([1-3])#(east|west)#([0-2])#([0-9])*.*";
    public static final Pattern RACK_POS_REGEX = Pattern.compile(RACK_POS_REGEX_STRING);

    @Getter protected ConversationState state;

    @Override
    public Routine handle(Player player, TalkCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;

        boolean run = true;


        Adapter adapter = cmd.getAdapter();


        adapter.send(new Greetings().getRandom());

        if (player.getMetaData(KEY_CONVERSATION_STATE) == null) {
            player.setMetaData(KEY_CONVERSATION_STATE, new ConversationState());
        }

        this.state = (ConversationState) player.getMetaData(KEY_CONVERSATION_STATE);

        while (run) {

            Thought thought = think(player, adapter.read(">").toLowerCase());

            adapter.send(thought.getResponse());

            if (thought.getFollowup() != null) {
                adapter.send(thought.getFollowup());
            }

            if (thought.isExit()) {
                run = false;
            }
        }


        return null;

    }



    protected Thought think(Player player, String string) {

        if (string.equals("debug_dump_state")) {
            return new Thought(string, getState().toString());
        }

        if (string.contains("fine") || string.contains("great") || string.contains("ok")) {
            getState().add(Q_SHOULD_HELP);
            return new Thought(string, "Nice to hear. Can I help you in some way?");
        }

        if (getState().contains(Q_SHOULD_HELP)) {
            getState().remove(Q_SHOULD_HELP);
            if (string.contains("y")) {
                return new Thought(string, "What an honor. Go ahead an ask!");
            } else if (string.contains("n")){
                getState().add(Q_WHICH_TOPIC);
                return new Thought(string, "Ok, what do you want to talk about then?");
            } else {
                getState().add(Q_SHOULD_HELP);
                return new Thought(string, "Maybe answer with yes or no...");
            }
        }

        if (string.contains("?")) {
            return handleQuestion(player, string);
        }



        return null;
    }


    protected Thought handleQuestion(Player player, String string) {
        if (string.contains("where")) {

            if (string.startsWith("where")) {
                if (string.contains("office") || string.contains("control")) {
                    return new Thought(string, "The office is down the hallway to the west, door facing north");
                } else if (string.contains("server") || (string.contains("rack") && !string.matches(RACK_POS_REGEX_STRING)) || string.contains("datacenter")) {
                    return new Thought(string, "The serverroom is in the middle from the hallway, door facing north");
                } else if (string.contains("basement")) {
                    return new Thought(string, "Downstairs. The staircase is on the eastern side of the hallway");
                } else if (string.contains("switch")) {
                    return new Thought(string, "The switches are on the west wall of the serverroom.");
                } else if (string.contains("fuse")) {
                    return new Thought(string, "The fuseboxes are on the east wall of the serverroom.");
                } else if (string.contains("vent")) {
                    return new Thought(string, "The only vent is on the east wall of the serverroom.");
                } else if (string.matches(RACK_POS_REGEX_STRING)) {
                    Matcher matcher = RACK_POS_REGEX.matcher(string);
                    String rack = matcher.group(1);
                    String direction = matcher.group(2);
                    String y_coordinate = matcher.group(3);
                    String servernumber = matcher.group(4);

                    int rackNumber = Integer.parseInt(rack);

                    Coordinate coordinate = new Coordinate(0, Integer.parseInt(y_coordinate));

                    switch (rackNumber) {
                        case 1:
                            if (direction.equals("west")) {
                                coordinate.setX(2);
                            } else {
                                coordinate.setX(3);
                            }
                            break;
                        case 2:
                            if (direction.equals("west")) {
                                coordinate.setX(3);
                            } else {
                                coordinate.setX(4);
                            }
                            break;
                        case 3:
                            if (direction.equals("west")) {
                                coordinate.setX(4);
                            } else {
                                coordinate.setX(5);
                            }
                            break;
                        default:
                            throw new IllegalStateException();
                    }

                    String lookAt = direction.equals("west") ?
                            Location.Direction.EAST.toString() : Location.Direction.WEST.toString();

                    return new Thought(string, "Ok you're looking for a specific server, great!\n" +
                            "You can find the rack #" + rack + " at " + coordinate.toString() + ".\n" +
                            "Then interact to the rack to your " + lookAt + ". The choose server #" + servernumber
                    );
                }
            }

        } else if (string.contains("who")) {

            if (string.contains("hacked")) {
                if ( player.getMetaData(KEY_LOG_STATUS) == null || player.getMetaValue(KEY_LOG_STATUS) == LOG_INVALID) {
                    return new Thought(string, "I don't know yet, maybe you can help us?");
                } else {
                    return new Thought(string, "We only know, that his username was peter, no more no less");
                }
            }

        } else if (string.contains("when")) {
            return new Thought(string, "Time is not absolute, it's relative, so the answer has to be 'anytime'.");
        } else if (string.contains("why")) {
            return new Thought(string, "How long is a piece of string...?");
        }

        return new Thought(string, "I don't understand this question...");

    }

    @Data @AllArgsConstructor public final class Thought {
        protected boolean exit;
        protected String input;
        protected String response;
        protected String followup;

        public Thought(String input, String response) {
            this(false, input, response, null);
        }
    }

    public final class ConversationState extends Vector<State> {}

    public enum State {
        Q_SHOULD_HELP,
        Q_WHICH_TOPIC,

    }
}
