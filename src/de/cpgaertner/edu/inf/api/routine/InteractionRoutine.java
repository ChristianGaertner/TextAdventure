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

        int slot = getNumber("slot #:", adapter);

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

    protected int getNumber(String prompt, Adapter adapter) throws IOException {
        boolean answerPending = true;
        int number = 0;
        while (answerPending) {
            String numberString = adapter.read(prompt);
            try {
                number = Integer.parseInt(numberString);
                answerPending = false;
            } catch (NumberFormatException e) {
                answerPending = true;
            }

        }

        return number;
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
