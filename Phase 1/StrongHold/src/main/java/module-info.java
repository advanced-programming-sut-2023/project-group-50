module Stronghold {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens model.Map.GUI to javafx.fxml;
    exports model.Map.GUI;
    exports view.show.Menu;
    opens view.show.Menu to javafx.fxml;
    exports view.show.controller;
    opens view.show.controller to javafx.fxml;

}