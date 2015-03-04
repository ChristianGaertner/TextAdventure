package de.cpgaertner.edu.inf.games.datacenter.command.talk.data;

import java.util.List;

import static de.cpgaertner.edu.inf.api.CoreEngine.RANDOM;

public abstract class RandomString {


    protected abstract List<String> getData();


    public String getRandom() {
        return getData().get(RANDOM.nextInt(getData().size()));
    }

}
