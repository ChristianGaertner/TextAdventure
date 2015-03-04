package de.cpgaertner.edu.inf.games.datacenter.command.talk.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GoodFeelings extends RandomString {

    @Getter protected static List<String> sData;

    @Getter protected List<String> data;

    static {
        sData = new ArrayList<>();

        sData.add("fine");
        sData.add("well");
        sData.add("good");
        sData.add("great");
        sData.add("happy");
        sData.add("lucky");
        sData.add("gleeful");
        sData.add("satisfied");
        sData.add("cheerful");
        sData.add("energetic");
        sData.add("wonderful");
        sData.add("content");
        sData.add("relaxed");
    }

    public GoodFeelings() {
        data = sData;
    }

}
