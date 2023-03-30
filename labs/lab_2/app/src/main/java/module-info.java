module explan {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    // matrix operations
    requires ejml.simple;
    requires transitive ejml.core;

    opens explan to javafx.fxml;
    opens explan.view to javafx.fxml;
    exports explan;
}
