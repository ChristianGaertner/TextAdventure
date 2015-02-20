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
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command.go.GoCommand;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command.go.GoCommandHandler;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.command.go.GoCommandParser;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.routines.InitialRoutine;
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
        return new InitialRoutine(getName(), adapter);
    }

    /**
     * Handles the game.
     *
     * @return main routine
     */
    @Override
    public Routine getHostRoutine(Adapter adapter) {

        BasicCommandSystemManager csm = new BasicCommandSystemManager(adapter);
        csm.add(new GoCommandParser());
        RootRoutine r = new RootRoutine(csm);
        r.addCommandHandler(GoCommand.class, new GoCommandHandler());

        return r;
    }

    @Override
    public void changeLevel(Level level) {

        level.init(getLevel());
        getLevel().close();
        this.level = level;

    }
}
