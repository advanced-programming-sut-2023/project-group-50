module Stronghold {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    opens model.Map.GUI to javafx.fxml;
    exports model.Map.GUI;

    opens view.show.ProfileMenu to javafx.fxml;
    exports view.show.ProfileMenu;

    opens model.Government.GUI to javafx.fxml;
    exports model.Government.GUI;

    opens view.show.MainMenu to javafx.fxml;
    exports view.show.MainMenu;
    exports model.Map.GUI.MiniMap;
    opens model.Map.GUI.MiniMap to javafx.fxml;

    exports model.Map.GUI.MapPane;
    opens model.Map.GUI.MapPane to javafx.fxml;

    opens view.show.Menus to javafx.fxml;
    exports view.show.Menus;

    opens view.show.OnlineMenu to javafx.fxml;
    exports view.show.OnlineMenu;

    opens Server to com.google.gson;
    exports Server;

}