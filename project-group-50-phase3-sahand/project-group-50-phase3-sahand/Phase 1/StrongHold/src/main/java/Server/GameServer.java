package Server;

import model.Game;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GameServer extends Thread {


    private Server server;
    private static final int maxPlayerNumber=8;
    private HashMap<String, Socket> players;
    public GameServer(Server server,HashMap<String,Socket> players){
        this.players=players;
        this.server=server;

    }

    public synchronized void addPlayer(String name,Socket socket){
        this.players.put ( name,socket );

    }
    public synchronized void removePlayer(String name){
        this.players.remove (name );
    }

    @Override
    public void run () {
        Timer timer=new Timer ();
        timer.schedule ( new TimerTask () {
            @Override
            public void run () {
                try {
                    updatePlayer();
                } catch (IOException e) {
                    throw new RuntimeException ( e );
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException ( e );
                }
            }
        },3000 );
    }

    private void updatePlayer () throws IOException, ClassNotFoundException {


        for ( Map.Entry<String,Socket> set:players.entrySet () ){
            Client.getData ();
            new ObjectOutputStream ( set.getValue ().getOutputStream () ).writeObject (
                    new Packet ( ServerCommands.NEXT_TURN ) );



        }





    }


}
