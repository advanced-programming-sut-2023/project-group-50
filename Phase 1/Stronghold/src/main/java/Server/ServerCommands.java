package Server;

public enum ServerCommands {
    INIT("init_user"),
    INIT_DONE("init_done"),
    DISCONNECT("disconnect"),
    SENDING_SAVE("sending_data"),
    SENDING_PRIVATE_MAP("sending_private_map"),
    SHARING_MAP("sharing_map");


    private final String string;

    ServerCommands(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
