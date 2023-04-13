package explan.view;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

public class BaseView {
    public void displayParseErrorMessage(String field) {
        var fmt = "Значение поля \"%s\" не распознано. Введите положительное вещественное число.";
        displayInvalidInputErrorMessage(String.format(fmt, field));
    }

    public void displayInvalidDomainMessage(String field) {
        var fmt = "Значение %s должно быть положительным вещественным числом.";
        displayInvalidInputErrorMessage(String.format(fmt, field));
    }

    public void displayInvalidInputErrorMessage(String content) {
        displayErrorMessage("Некорректный ввод", content);
    }

    public void displayErrorMessage(String header, String content) {
        var errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }
}
