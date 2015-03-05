package de.cpgaertner.edu.inf.games.datacenter.command.talk;

import de.cpgaertner.edu.inf.Main;
import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.handler.CommandHandler;
import de.cpgaertner.edu.inf.api.level.Coordinate;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.Routine;
import de.cpgaertner.edu.inf.games.datacenter.command.talk.data.Approval;
import de.cpgaertner.edu.inf.games.datacenter.command.talk.data.GoodFeelings;
import de.cpgaertner.edu.inf.games.datacenter.command.talk.data.Greetings;
import de.cpgaertner.edu.inf.games.datacenter.command.talk.data.Insults;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.OfficeLocation;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location.ServerRoomLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.cpgaertner.edu.inf.api.level.Level.KEY_QUEST;
import static de.cpgaertner.edu.inf.api.level.player.Player.KEY_AGE;
import static de.cpgaertner.edu.inf.games.datacenter.command.talk.TalkCommandHandler.State.*;
import static de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.GroundFloorLevel.QUEST_FIND_COMPUTER;
import static de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.GroundFloorLevel.QUEST_REPLACE_HDD;
import static de.cpgaertner.edu.inf.games.datacenter.routines.ComputerOneRoutine.KEY_LOG_STATUS;
import static de.cpgaertner.edu.inf.games.datacenter.routines.ComputerOneRoutine.LOG_INVALID;

public class TalkCommandHandler implements CommandHandler<TalkCommand> {


    public static final String KEY_CONVERSATION_STATE = "TALK_CONV_STATE";

    public static final String RACK_POS_REGEX_STRING = ".*rack:([1-3])#(east|west)#([0-2])#([0-9])*.*";
    public static final Pattern RACK_POS_REGEX = Pattern.compile(RACK_POS_REGEX_STRING);


    public static final int AGE_BOB = 22;

    @Getter protected ConversationState state;

    @Override
    public Routine handle(Player player, TalkCommand cmd) throws IOException {
        assert player != null;
        assert cmd != null;

        boolean run = true;


        Adapter adapter = cmd.getAdapter();



        if (player.getMetaData(KEY_CONVERSATION_STATE) == null) {
            player.setMetaData(KEY_CONVERSATION_STATE, new ConversationState());
        }

        this.state = (ConversationState) player.getMetaData(KEY_CONVERSATION_STATE);

        getState().add(Q_FEELINGS);
        adapter.send(new Greetings().getRandom());

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

        if (string.equals("%%debug_dump_state%%") || (string.equals("dds") && Main.DEBUG)) {
            return new Thought(string, getState().toString());
        }

        if (string.equals("exit") || string.contains("bye")) {
            return new Thought(true, string, "Good bye!", null);
        }

        if (getState().contains(INSULTED)) {
            getState().remove(INSULTED);
            if (getState().contains(Q_VALIDATION)) {
                getState().remove(Q_VALIDATION);
                if (new Approval().contains(string)) {
                    return new Thought(false, string, "This is not nice of you.", new Insults().getRandom());
                } else {
                    return new Thought(string, "I hope so!");
                }
            }

            return new Thought(string, "Shut up, fucking " + new Insults().getRandom());
        }

        if (new Insults().contains(string)) {
            getState().add(Q_VALIDATION);
            getState().remove(Q_SHOULD_HELP);
            getState().add(INSULTED);
            return new Thought(string, "Did you say that to me?");
        }

        if (getState().contains(Q_FEELINGS)) {
            getState().remove(Q_FEELINGS);
            getState().add(Q_SHOULD_HELP);
            if (new GoodFeelings().contains(string)) {
                return new Thought(string, "Nice to hear. Can I help you in some way?");
            } else {
                return new Thought(string, "What a shame... Can I help you in some way then?");
            }
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

        if (getState().contains(Q_WHICH_TOPIC)) {
            getState().remove(Q_WHICH_TOPIC);



            if (string.contains("game")) {
                getState().add(T_GAME);
                return new Thought(string, "Well, this game is awesome!");
            }
            if (string.contains("weather")) {
                getState().add(Q_WHICH_TOPIC);
                return new Thought(false, string,
                        "Listen, I am just a computer, I cannot talk about the weather with you",
                        "What do you want to talk about instead?"
                        );
            }
        }

        if (getState().contains(ANSWERED)) {
            getState().remove(ANSWERED);
            if (string.contains("thank")) {
                return new Thought(string, "You're welcome");
            }
        }

        if (getState().contains(T_BOB)) {

            if (getState().contains(Q_VALIDATION)) {
                getState().remove(Q_VALIDATION);
                if (new Approval().contains(string)) {
                    getState().remove(T_BOB);
                    getState().add(T_USER);
                    return new Thought(false, string, "I'm so good at this.", "Tell me more about you!");
                } else {
                    getState().remove(T_BOB);
                    getState().add(Q_WHOAREYOU);
                    return new Thought(false, string, "Oh. I'm sorry!", "Who are you then?");
                }
            }

            if (string.contains("meet")) {
                getState().add(Q_VALIDATION);
                return new Thought(false, string, "The honor is on mine!", "You are " + player.getName() + ", right?");
            }

            if (string.contains("tell")) {
                getState().remove(T_BOB);
                getState().add(T_USER);
                return new Thought(string, "Well I started working here like 2 years ago, now I'm " + AGE_BOB + " years old and\n" +
                        "do not know what to do with my life... It's complicated... What about you?");
            }

        }

        if (getState().contains(T_USER)) {

            if (getState().contains(Q_VALIDATION) && getState().contains(DUPL_INF)) {
                getState().remove(DUPL_INF);
                getState().remove(Q_VALIDATION);
                if (new Approval().contains(string)) {
                    return new Thought(string, "Yeah, see... I Computer never forgets!");
                } else {
                    return new Thought(string, "Anyway... Let's talk about you again!");
                }
            }

            if (string.contains("years") || string.contains("old")) {

                if (player.getMetaData(KEY_AGE) != null) {
                    getState().add(Q_VALIDATION);
                    getState().add(DUPL_INF);
                    return new Thought(string, "I thought you already told me that...");
                }

                // Extract age from string
                String ageString = string.replaceAll("\\D+","");
                int age = Integer.parseInt(ageString);

                if (age == 0) {
                    return new Thought(string, "I'd bet $1000 that you are lying to me!");
                }

                if (age <= 5) {
                    return new Thought(string, "You are bit young to play this game, aren't you?!");
                }

                if (age > 70) {
                    return new Thought(string, "Didn't know old people like you know how to operate a command line app.");
                }

                player.setMetaValue(KEY_AGE, age);

                int diff = AGE_BOB - age;

                if (diff == 0) {
                    return new Thought(string, "Oh, we are the same age");
                } else if (diff > 0) {
                    return new Thought(string, "Well I guess I'm just " + diff + " years older than you.");
                } else {
                    return new Thought(string, "Well I guess I'm just " + (-diff) + " years younger than you.");
                }
            }

            if ((string.contains("don't") || string.contains("do not")) && (string.contains("talk") || string.contains("tell"))) {
                getState().remove(T_USER);
                getState().add(Q_WHICH_TOPIC);
                return new Thought(false, string, "I can understand this!", "What do you want to talk about instead?");
            }
        }

        if (getState().contains(Q_WHOAREYOU)) {
            getState().remove(Q_WHOAREYOU);

            return new Thought(string, "Nice to meet you!");

        }

        if (string.contains("?")) {
            return handleQuestion(player, string);
        }

        if (string.contains("question") && string.contains("I") || string.contains("ask")) {
            return new Thought(string, "Go ahead and ask me!");
        }

        if (getState().contains(Q_VALIDATION)) {
            getState().remove(Q_VALIDATION);

            if (new Approval().contains(string)) {
                return new Thought(string, "Great!");
            } else {
                return new Thought(string, "We can agree that we disagree.");
            }
        }


        return new Thought(string, "Well well...");
    }


    protected Thought handleQuestion(Player player, String string) {

        getState().add(ANSWERED);

        if (string.contains("where")) {

            if (string.startsWith("where")) {
                if (string.contains("office") || string.contains("control")) {
                    return new Thought(string, "The office is down the hallway to the west, door facing north");
                }
                if (string.contains("server") || (string.contains("rack") && !string.matches(RACK_POS_REGEX_STRING)) || string.contains("datacenter")) {
                    return new Thought(string, "The serverroom is in the middle from the hallway, door facing north");
                }
                if (string.contains("basement")) {
                    return new Thought(string, "Downstairs. The staircase is on the eastern side of the hallway");
                }
                if (string.contains("switch")) {
                    return new Thought(string, "The switches are on the west wall of the serverroom.");
                }
                if (string.contains("fuse")) {
                    return new Thought(string, "The fuseboxes are on the east wall of the serverroom.");
                }
                if (string.contains("vent")) {
                    return new Thought(string, "The only vent is on the east wall of the serverroom.");
                }
                if (string.matches(RACK_POS_REGEX_STRING)) {
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

        }

        if (string.contains("who")) {

            if (string.contains("are you")) {
                getState().add(T_BOB);
                return new Thought(string, "I'm Bob, I work in this datacenter!");
            }

            if (string.contains("hacked")) {
                if ( player.getMetaData(KEY_LOG_STATUS) == null || player.getMetaValue(KEY_LOG_STATUS) == LOG_INVALID) {
                    return new Thought(string, "I don't know yet, maybe you can help us?");
                } else {
                    return new Thought(string, "We only know, that his username was peter, no more no less");
                }
            }

        }

        if (string.contains("when")) {
            return new Thought(string, "Time is not absolute, it's relative, so the answer has to be 'anytime'.");
        }

        if (string.contains("why")) {
            return new Thought(string, "How long is a piece of string...?");
        }

        if (string.contains("what")) {
            if (string.contains("do")) {
                switch (player.getMetaValue(KEY_QUEST)) {
                    case QUEST_FIND_COMPUTER:
                        if (player.getLevel().getAt(player.getPosition()) instanceof OfficeLocation) {
                            return new Thought(string, "The computer is at (0,0) to the north, go ahead and interact with it!");
                        } else {
                            return new Thought(string, "You should look into the office ad find the computer!");
                        }
                    case QUEST_REPLACE_HDD:
                        if (player.getLevel().getAt(player.getPosition()) instanceof ServerRoomLocation) {
                            return new Thought(string, "You should replace the HDD of the Server RACK:1#EAST#1#3");
                        } else {
                            return new Thought(string, "You should look into the serverroom");
                        }
                    default:
                        return new Thought(string, "Just look around a bit. I cannot tell you everything!");
                }
            }
        }

        getState().add(MISUNDERSTOOD);
        getState().remove(ANSWERED);

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
        Q_FEELINGS,
        Q_SHOULD_HELP,
        Q_WHICH_TOPIC,
        Q_WHOAREYOU,
        Q_VALIDATION,


        T_GAME,

        T_BOB,
        T_USER,

        INSULTED,


        ANSWERED,
        MISUNDERSTOOD,

        DUPL_INF,


    }
}
