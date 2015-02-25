package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.BaseLocation;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.InteractionRoutine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class StairsLocation extends BaseLocation {

    @Getter @Setter protected Level up;
    @Getter @Setter protected Level down;

    @Override
    public void useDefaultRoutine() {
        setRoutine(new DefaultRoutine(this));
    }

    @AllArgsConstructor
    public static class DefaultRoutine extends InteractionRoutine {

        @Getter @Setter protected StairsLocation stairs;

        @Override
        public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {
            adapter.send("Do you want to go up or down?");

            boolean undecided = true;

            while (undecided) {
                String res = adapter.read("[up/down/abort]");
                undecided = false;
                if (res.equalsIgnoreCase("up")) {

                    if (getStairs().getUp() == null) {
                        adapter.send("This staircase doesn't go up, sadly...");
                    } else {
                        player.setLevel(getStairs().getUp());
                    }

                } else if (res.equalsIgnoreCase("down")) {

                    if (getStairs().getDown() == null) {
                        adapter.send("This staircase doesn't go down, sadly...");
                    } else {
                        player.setLevel(getStairs().getDown());
                    }

                } else if (res.equalsIgnoreCase("abort")) {
                    adapter.send("To your command master!");
                } else {
                    undecided = true;
                    adapter.send("Please choose one of the possible answers!");
                }
            }

            return false;
        }
    }

}
