package de.cpgaertner.edu.inf.api.routine;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;

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
}
