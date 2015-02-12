package de.cpgaertner.edu.inf;

import de.cpgaertner.edu.inf.api.Game;
import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.adapter.shell.ShellAdapter;
import de.cpgaertner.edu.inf.games.datacenter.DataCenterAdventure;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class Main {

    public static void main(String[] args) throws IOException {

        Game g = new DataCenterAdventure();
        Adapter a = new ShellAdapter();

        g.getInitialRoutine(a).enter(g.getPlayer(), null, a);




    }
}
