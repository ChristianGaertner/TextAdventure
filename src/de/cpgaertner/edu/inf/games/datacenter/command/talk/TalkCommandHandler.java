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

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TalkCommandHandler implements CommandHandler<TalkCommand> {

    public static final String RACK_POS_REGEX_STRING = ".*rack:([1-3])#(east|west)#([0-2])#([0-9])*.*";
    public static final Pattern RACK_POS_REGEX = Pattern.compile(RACK_POS_REGEX_STRING);

    @Override
    public Routine handle(Player player, TalkCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;

        boolean run = true;


        Adapter adapter = cmd.getAdapter();


        adapter.send(new Greetings().getRandom());

        while (run) {

            Thought thought = think(adapter.read(">").toLowerCase());

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



    protected Thought think(String string) {

        if (string.contains("?")) {
            return handleQuestion(string);
        }



        return null;
    }


    protected Thought handleQuestion(String string) {
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

        } else if (string.contains("when")) {

        } else if (string.contains("why")) {

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
}
