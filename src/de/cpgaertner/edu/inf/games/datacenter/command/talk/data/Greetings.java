package de.cpgaertner.edu.inf.games.datacenter.command.talk.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Greetings extends RandomString {

    @Getter protected List<String> data;

    public Greetings(List<String> data) {
        this.data = data;
    }

    public Greetings() {
        data = new ArrayList<>();

        data.add("Hi. How are you?");
        data.add("How are you?");
        data.add("What's up?");
        data.add("How you doin?");
        data.add("Hey. How you doin?");

    }
}
