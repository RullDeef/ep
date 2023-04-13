package explan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main_window"), 1200, 600);

        // populate labs here
        var labTabs = (TabPane) scene.lookup("#labTabs");
        labTabs.getTabs().add(loadLabTab("ЛР №1", "lab1_view"));
        labTabs.getTabs().add(loadLabTab("ЛР №2", "lab2_view"));
        labTabs.getTabs().add(loadLabTab("ЛР №3", "lab3_view"));
        labTabs.getTabs().add(loadLabTab("ЛР №4", "lab4_view"));

        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static Tab loadLabTab(String title, String filename) throws IOException {
        var tab = new Tab();
        tab.setText(title);
        tab.setContent(loadFXML(filename));
        return tab;
    }

    public static void main(String[] args) {
        launch();
    }
}
