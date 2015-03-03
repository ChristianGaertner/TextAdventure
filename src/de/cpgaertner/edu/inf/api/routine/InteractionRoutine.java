package de.cpgaertner.edu.inf.api.routine;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.level.Item;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;

public abstract class InteractionRoutine implements Routine {

    public enum Answer {
        ONE, TWO, ABORT
    }

    public enum YesNoAnswer {
        YES, NO, ABORT
    }

    @Override
    public String getPrompt() {
        return null;
    }

    @Override
    public CommandSystemManager getCommandSystemManager() {
        return null;
    }

    protected YesNoAnswer askYesNoQuestion(String question, Adapter adapter) throws IOException {
        switch (askQuestion(question, "yes", "no", adapter)) {
            case ONE:
                return YesNoAnswer.YES;
            case TWO:
                return YesNoAnswer.NO;
            case ABORT:
                return YesNoAnswer.ABORT;
            default:
                throw new IllegalStateException();
        }
    }

    protected Answer askQuestion(String question, String answer1, String answer2, Adapter adapter) throws IOException {
        adapter.send(question);
        String res;
        while (true) {
            res = adapter.read("[" + answer1 + "/" + answer2 + "/abort]");

            if (res.equalsIgnoreCase(answer1)) {

                return Answer.ONE;


            } else if (res.equalsIgnoreCase(answer2)) {

                return Answer.TWO;

            } else if (res.equalsIgnoreCase("abort")) {

                return Answer.ABORT;

            } else {
                adapter.send("Please choose one of the possible answers!");
            }
        }
    }

    protected Item getItem(InventoryResponseSuite irs, Player player, Adapter adapter) throws IOException {

        if (player.getInventory().isEmpty()) {
            adapter.send(irs.getEmptyInventory());
            return null;
        }

        adapter.send(player.getInventory().toString());

        int slot = getInteger("slot #:", 0, player.getInventory().getSlots(), adapter);

        if (player.getInventory().getSlots() < slot) {
            adapter.send(irs.getNoSuchSlot());
            return null;
        }

        Item i = player.getInventory().getItems().get(slot);

        if (i != null) {
            return i;
        }

        adapter.send(irs.getNoItemAtSlot());
        return null;
    }

    /**
     * Requests a number
     * @param prompt prompt for the user {@link de.cpgaertner.edu.inf.api.adapter.Adapter#read(String)}
     * @param lower lower bound, test is <code>input >= lower</code>
     * @param upper upper bound, test is <code>input <= upper</code>
     * @param adapter adapter to use
     * @return a number in the range of (lower,upper)
     * @throws IOException
     */
    protected int getInteger(String prompt, int lower, int upper, Adapter adapter) throws IOException {
        int integer;
        while (true) {
            String numberString = adapter.read(prompt);
            try {
                integer = Integer.parseInt(numberString);
                if (integer >= lower && integer <= upper) {
                    return integer;
                } else {
                    adapter.sendf("Given integer out of range (%s,%s)", lower, upper);
                }
            } catch (NumberFormatException e) {
                adapter.send("Please enter an integer");
            }
        }
    }

    @AllArgsConstructor @Data protected static class InventoryResponseSuite {

        public static final InventoryResponseSuite DEFAULT = new InventoryResponseSuite(
                "Your inventory looks empty...",
                "There isn't an item at this slot.",
                "No such slot."
        );

        protected String emptyInventory;

        protected String noItemAtSlot;

        protected String noSuchSlot;

    }
}
