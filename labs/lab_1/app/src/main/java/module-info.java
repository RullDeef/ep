module explan {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens explan to javafx.fxml;
    exports explan;
    exports explan.model;
}
