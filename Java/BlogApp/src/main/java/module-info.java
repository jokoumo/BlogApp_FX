module Modules {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires jasypt;

    opens org.openjfx to javafx.fxml;

    exports org.openjfx;
}
