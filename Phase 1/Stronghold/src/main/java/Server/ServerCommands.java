package Server;

public enum ServerCommands {
    INIT("init_user"),
    INIT_DONE("init_done"),
    DISCONNECT("disconnect"),
    SENDING_DATA("sending_data");


    private final String string;

    ServerCommands(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
