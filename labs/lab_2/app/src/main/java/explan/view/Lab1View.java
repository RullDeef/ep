package explan.view;

import java.util.List;

import explan.model.Point;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

public class Lab1View {
    @FXML
    LineChart<Number, Number> avgWaitTimeChart;

    public void setChartData(List<Point> points) {
        var series = new XYChart.Series<Number, Number>();
        for (var point : points)
            series.getData().add(new XYChart.Data<Number, Number>(point.x, point.y));

        avgWaitTimeChart.getData().clear();
        avgWaitTimeChart.getData().add(series);
    }

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
