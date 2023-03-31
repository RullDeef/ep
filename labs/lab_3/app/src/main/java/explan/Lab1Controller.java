package explan;

import java.io.IOException;

import explan.model.BuildChartParams;
import explan.model.SimulationParams;
import explan.model.SimulationService;
import explan.view.Lab1View;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Lab1Controller extends Lab1View {
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

    private void updateAvgWaitTimeChart() {
        var params = new BuildChartParams();
        params.minX = 0.05f;
        params.maxX = 1.7f;
        params.pointsCount = 31;
        params.modelingTime = 2000.0f;
        params.modelingsCount = 150;

        var points = simulationService.avgWaitTimeOverRho(params);
        setChartData(points);
    }

    @FXML
    public void initialize() {
        updateAvgWaitTimeChart();
    }
}
