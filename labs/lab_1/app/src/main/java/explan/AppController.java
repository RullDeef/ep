package explan;

import java.io.IOException;

import explan.model.SimulationParams;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class AppController {

    SimulationService simulationService = new SimulationService();

    @FXML
    TextField lambdaTextField;

    @FXML
    TextField muTextField;

    @FXML
    TextField tMaxTextField;

    @FXML
    TextField rhoTheoretical;

    @FXML
    TextField rhoPractical;

    @FXML
    TextField avgWaitTime;

    @FXML
    LineChart<Number, Number> avgWaitTimeChart;

    @FXML
    public void initialize() {
        updateAvgWaitTimeChart();
    }

    @FXML
    private void updateAvgWaitTimeButtonPressed() throws IOException {
        updateAvgWaitTimeChart();
    }

    @FXML
    private void startSimulationButtonPressed() throws IOException {
        float lambda, mu, tMax;

        try {
            lambda = Float.parseFloat(lambdaTextField.getText());
        } catch (NumberFormatException e) {
            displayParseErrorMessage("λ");
            return;
        }

        try {
            mu = Float.parseFloat(muTextField.getText());
        } catch (NumberFormatException e) {
            displayParseErrorMessage("μ");
            return;
        }

        try {
            tMax = Float.parseFloat(tMaxTextField.getText());
        } catch (NumberFormatException e) {
            displayParseErrorMessage("t_max");
            return;
        }

        if (lambda <= 0.0) {
            displayInvalidDomainMessage("λ");
        } else if (mu <= 0.0) {
            displayInvalidDomainMessage("μ");
        } else if (tMax <= 0.0) {
            displayInvalidDomainMessage("t_max");
        } else {
            var params = new SimulationParams(lambda, mu, tMax);

            try {
                var result = simulationService.startSimulation(params);

                rhoTheoretical.setText(String.format("%.3f", result.rhoTheoretical));
                rhoPractical.setText(String.format("%.3f", result.rhoPractical));
                avgWaitTime.setText(String.format("%.3f", result.avgWaitTime));
            } catch (Exception e) {
                displayErrorMessage("Ошибка при моделировании", e.getMessage());

                rhoTheoretical.setText("");
                rhoPractical.setText("");
            }
        }
    }

    @FXML
    public void aboutMenuAction() {
        var errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("О программе");
        errorAlert.setContentText(
                "Лабораторная работа №1\nпо курсу \"Планирование Эксперимента\".\nВариант 1.\nСтудент: Клименко Алексей, ИУ7-85Б.");
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    private void updateAvgWaitTimeChart() {
        var params = new BuildChartParams();
        params.minX = 0.05f;
        params.maxX = 1.7f;
        params.pointsCount = 31;
        params.modelingTime = 2000.0f;
        params.modelingsCount = 150;

        var points = simulationService.avgWaitTimeOverRho(params);

        var series = new XYChart.Series<Number, Number>();
        for (var point : points)
            series.getData().add(new XYChart.Data<Number, Number>(point.x, point.y));

        avgWaitTimeChart.getData().clear();
        avgWaitTimeChart.getData().add(series);
    }

    private void displayParseErrorMessage(String field) {
        var fmt = "Значение поля \"%s\" не распознано. Введите положительное вещественное число.";
        displayInvalidInputErrorMessage(String.format(fmt, field));
    }

    private void displayInvalidDomainMessage(String field) {
        var fmt = "Значение %s должно быть положительным вещественным числом.";
        displayInvalidInputErrorMessage(String.format(fmt, field));
    }

    private void displayInvalidInputErrorMessage(String content) {
        displayErrorMessage("Некорректный ввод", content);
    }

    private void displayErrorMessage(String header, String content) {
        var errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }
}
