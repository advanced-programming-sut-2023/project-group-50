module Stronghold {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens model.Map.GUI to javafx.fxml;
    exports model.Map.GUI;
}