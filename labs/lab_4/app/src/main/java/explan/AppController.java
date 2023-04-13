package explan;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

public class AppController {

    @FXML
    private void aboutMenuAction() {
        var errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("О программе");
        errorAlert.setContentText(
                "Лабораторные работы\nпо курсу \"Планирование Эксперимента\".\nВариант 1.\nСтудент: Клименко Алексей, ИУ7-85Б.");
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }
}
