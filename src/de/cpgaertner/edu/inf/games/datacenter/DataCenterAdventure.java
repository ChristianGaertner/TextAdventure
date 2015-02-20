package de.cpgaertner.edu.inf.games.datacenter;

import de.cpgaertner.edu.inf.api.Game;
import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.player.DefaultPlayer;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.parsing.BasicCommandSystemManager;
import de.cpgaertner.edu.inf.api.routine.RootRoutine;
import de.cpgaertner.edu.inf.api.routine.Routine;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.GroundFloorLevel;
import de.cpgaertner.edu.inf.games.datacenter.command.go.GoCommandPackage;
import de.cpgaertner.edu.inf.games.datacenter.command.look.LookCommandPackage;
import de.cpgaertner.edu.inf.games.datacenter.routines.EmptyRoutine;
import lombok.Getter;

public class DataCenterAdventure implements Game {

    @Getter protected final String name = "DataCenter";

    @Getter protected Player player;

    @Getter protected Level level;

    public DataCenterAdventure() {
        this.player = new DefaultPlayer();
        this.level = new GroundFloorLevel();
    }

    @Override
    public Routine getInitialRoutine(Adapter adapter) {
        return new EmptyRoutine();
        // return new InitialRoutine(getName(), adapter);
    }

    /**
     * Handles the game.
     *
     * @return main routine
     */
    @Override
    public Routine getHostRoutine(Adapter adapter) {
        RootRoutine r = new RootRoutine(new BasicCommandSystemManager(adapter));

        r.addCommand(new GoCommandPackage());
        r.addCommand(new LookCommandPackage());

        return r;
    }

    @Override
    public void changeLevel(Level level) {

        level.init(getLevel());
        getLevel().close();
        this.level = level;

    }
}
