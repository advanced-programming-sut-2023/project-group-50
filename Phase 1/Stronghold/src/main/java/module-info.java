module Stronghold {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens model.Map.GUI to javafx.fxml;
    exports model.Map.GUI;

    opens view.show.ProfileMenu to javafx.fxml;
    exports view.show.ProfileMenu;

    opens model.Government.GUI to javafx.fxml;
    exports model.Government.GUI;

    opens view.show.MainMenu to javafx.fxml;
    exports view.show.MainMenu;
}