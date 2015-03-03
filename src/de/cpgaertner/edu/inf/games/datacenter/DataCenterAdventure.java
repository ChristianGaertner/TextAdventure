package de.cpgaertner.edu.inf.games.datacenter;

import de.cpgaertner.edu.inf.Main;
import de.cpgaertner.edu.inf.api.Game;
import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.level.Level;
import de.cpgaertner.edu.inf.api.level.player.DefaultPlayer;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.parsing.BasicCommandSystemManager;
import de.cpgaertner.edu.inf.api.routine.RootRoutine;
import de.cpgaertner.edu.inf.api.routine.Routine;
import de.cpgaertner.edu.inf.games.datacenter.command.debug.debugger.DebuggerCommandPackage;
import de.cpgaertner.edu.inf.games.datacenter.command.debug.force_open_all.ForceOpenAllCommandPackage;
import de.cpgaertner.edu.inf.games.datacenter.command.debug.teleport.TeleportCommandPackage;
import de.cpgaertner.edu.inf.games.datacenter.command.go.GoCommandPackage;
import de.cpgaertner.edu.inf.games.datacenter.command.interact.InteractCommandPackage;
import de.cpgaertner.edu.inf.games.datacenter.command.look.LookCommandPackage;
import de.cpgaertner.edu.inf.games.datacenter.command.position.PositionCommandPackage;
import de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.GroundFloorLevel;
import de.cpgaertner.edu.inf.games.datacenter.routines.EmptyRoutine;
import de.cpgaertner.edu.inf.games.datacenter.routines.InitialRoutine;
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
        if (Main.DEBUG) {
            return new EmptyRoutine();
        } else {
            return new InitialRoutine(getName(), adapter);
        }
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
        r.addCommand(new PositionCommandPackage());
        r.addCommand(new InteractCommandPackage());

        if (Main.DEBUG) {
            r.addCommand(new ForceOpenAllCommandPackage());
            r.addCommand(new DebuggerCommandPackage());
            r.addCommand(new TeleportCommandPackage());
        }

        return r;
    }

    @Override
    public void changeLevel(Level level) {

        level.init(getLevel());
        getLevel().close();
        this.level = level;

    }
}
