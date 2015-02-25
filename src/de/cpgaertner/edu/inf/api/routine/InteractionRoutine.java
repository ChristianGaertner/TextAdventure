package de.cpgaertner.edu.inf.api.routine;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.parsing.CommandSystemManager;

import java.io.IOException;

public abstract class InteractionRoutine implements Routine {

    public enum Answer {
        ONE, TWO, ABORT
    }

    @Override
    public String getPrompt() {
        return null;
    }

    @Override
    public CommandSystemManager getCommandSystemManager() {
        return null;
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
