module d.fomichev.launchpad {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;
    requires java.prefs;

    opens d.fomichev.launchpad to javafx.fxml;
    exports d.fomichev.launchpad;
}