package Server;

import java.io.IOException;

public class ServerStart {
    public static void main(String[] args) throws IOException {
        Server server = new Server(8888);
        server.run();
    }
}
