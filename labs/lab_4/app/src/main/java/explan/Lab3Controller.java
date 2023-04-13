package explan;

import explan.model.ExtendedExperimentService;
import explan.model.ExtendedSimulationService;
import explan.view.Lab3View;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Lab3Controller extends Lab3View {

    ExtendedSimulationService simulationService = new ExtendedSimulationService();
    ExtendedExperimentService experimentService = new ExtendedExperimentService();

    @FXML TextField x1TextField;
    @FXML TextField x2TextField;
    @FXML TextField x3TextField;
    @FXML TextField x4TextField;

    @FXML CheckBox normalizedInputCheckBox;

    @FXML
    private void recalcYButtonPressed() {
        try {
            experimentService.setExperimentSpace(
                getMinLambda1(), getMaxLambda1(),
                getMinMu1(), getMaxMu1(),
                getMinLambda2(), getMaxLambda2(),
                getMinMu2(), getMaxMu2()
            );

            experimentService.recalcCoefficients();

            setY(experimentService.getY());
            setYP(experimentService.getYPredicted());
            setYDP(experimentService.getYPredictedError());

            setRegressionNorm(experimentService.getB_Norm());
            setRegressionDenorm(experimentService.getB_Denorm());
        } catch (Exception e) {
            e.printStackTrace();
            displayErrorMessage("Ошибка", e.getMessage());
        }
    }

    @FXML
    private void predictButtonPressed() {
        double x1, x2, x3, x4;

        try {
            x1 = Double.parseDouble(x1TextField.getText());
        } catch (Exception e) {
            displayInvalidInputErrorMessage("λ1");
            return;
        }

        try {
            x2 = Double.parseDouble(x2TextField.getText());
        } catch (Exception e) {
            displayInvalidInputErrorMessage("μ1");
            return;
        }

        try {
            x3 = Double.parseDouble(x3TextField.getText());
        } catch (Exception e) {
            displayInvalidInputErrorMessage("λ2");
            return;
        }

        try {
            x4 = Double.parseDouble(x4TextField.getText());
        } catch (Exception e) {
            displayInvalidInputErrorMessage("μ2");
            return;
        }

        double yHat;
        double y;
        
        if (normalizedInputCheckBox.isSelected()) {
            y = experimentService.realAtNorm(x1, x2, x3, x4);
            yHat = experimentService.calcYNorm(x1, x2, x3, x4);
        } else {
            y = experimentService.realAt(x1, x2, x3, x4);
            yHat = experimentService.calcY(x1, x2, x3, x4);
        }

        setYOutput(y);
        setYHatOutput(yHat);
        setYError(Math.abs(y - yHat));
    }

    @FXML
    public void initialize() {
        recalcYButtonPressed();
    }
}
