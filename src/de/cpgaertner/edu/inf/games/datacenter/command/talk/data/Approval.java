package de.cpgaertner.edu.inf.games.datacenter.command.talk.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Approval extends RandomString {

    @Getter protected static List<String> sData;

    @Getter protected List<String> data;

    static {
        sData = new ArrayList<>();

        sData.add("yes");
        sData.add("right");
        sData.add("correct");
        sData.add("yeah");
        sData.add("positiv");
    }

    public Approval() {
        this.data = sData;
    }
}
