package Server;

import java.io.*;

public class Packet implements Serializable {
    public ServerCommands command;
    public Class<?>[] argClass;
    public Object[] args;

    public Packet(ServerCommands command, Object... args) {
        this.command = command;
        this.args = args;

        argClass = new Class[args.length];
        for (int i = 0; i < args.length; i++)
            argClass[i] = args[i].getClass();
    }

    public static Packet fromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return (Packet) in.readObject();
        }
    }

    public byte[] toBytes() throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(this);
            return bos.toByteArray();
        }
    }
}
