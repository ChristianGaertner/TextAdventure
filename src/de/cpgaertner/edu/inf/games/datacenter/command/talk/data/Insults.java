package de.cpgaertner.edu.inf.games.datacenter.command.talk.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Insults extends RandomString {


    @Getter protected static List<String> sData;

    @Getter protected List<String> data;

    static {
        sData = new ArrayList<>();

        sData.add("stupid");
        sData.add("ass");
        sData.add("idiot");
        sData.add("dumb");
        sData.add("silly");
        sData.add("bimbo");
        sData.add("bugger");
        sData.add("dag");
        sData.add("dago");
        sData.add("dickhead");
        sData.add("donkey");
        sData.add("dope");
        sData.add("dork");
        sData.add("flake");
        sData.add("freak");
        sData.add("gasbag");
        sData.add("git");
        sData.add("jerk");
        sData.add("lardass");
        sData.add("louse");
        sData.add("nerd");
        sData.add("nut");
        sData.add("nutter");
        sData.add("pig");
        sData.add("prat");
        sData.add("rat");
        sData.add("redneck");
        sData.add("scum");
        sData.add("scumbag");
        sData.add("arse");
        sData.add("toff");
        sData.add("tool");
        sData.add("twit");
        sData.add("wanker");
        sData.add("wimp");
        sData.add("zero");
    }

    public Insults() {
        this.data = sData;
    }
}
