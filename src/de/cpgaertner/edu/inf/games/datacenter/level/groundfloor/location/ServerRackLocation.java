package de.cpgaertner.edu.inf.games.datacenter.level.groundfloor.location;

import de.cpgaertner.edu.inf.api.adapter.Adapter;
import de.cpgaertner.edu.inf.api.command.Command;
import de.cpgaertner.edu.inf.api.level.BaseLocation;
import de.cpgaertner.edu.inf.api.level.Location;
import de.cpgaertner.edu.inf.api.level.player.Player;
import de.cpgaertner.edu.inf.api.routine.InteractionRoutine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class ServerRackLocation extends BaseLocation {


    @Getter
    protected Server[] server;

    public ServerRackLocation() {
        setWalkable(false);
        server = new Server[5];
    }

    /**
     * Sets the Routine to the default routine, if there is any.
     *
     * @return self (chaining support)
     */
    @Override
    public Location useDefaultRoutine() {
        setRoutine(new DefaultRoutine(this));
        return this;
    }


    @AllArgsConstructor
    public static class DefaultRoutine extends InteractionRoutine {

        @Getter @Setter
        protected ServerRackLocation serverRack;

        @Override
        public boolean handle(Player player, Command cmd, Adapter adapter) throws IOException {
            adapter.sendf("This Rack houses %s server.", getServerRack().getServer().length);
            adapter.send("Which one do you want to access?");
            int servernumber = getInteger("server #:", 0, getServerRack().getServer().length, adapter);

            Server server = getServerRack().getServer()[servernumber];

            adapter.send(server.toString());

            return false;
        }
    }

    public static class Server {

        // TODO: add properties

    }

}
