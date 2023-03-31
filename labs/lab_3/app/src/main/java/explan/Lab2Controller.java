package explan;

import explan.model.ExperimentService;
import explan.model.SimulationService;
import explan.view.Lab2View;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Lab2Controller extends Lab2View {

    SimulationService simulationService = new SimulationService();
    ExperimentService experimentService = new ExperimentService();

    @FXML
    TextField x1TextField;

    @FXML
    TextField x2TextField;

    @FXML
    CheckBox normalizedInputCheckBox;

    @FXML
    private void recalcYButtonPressed() {
        try {
            experimentService.setExperimentSpace(
                getMinLambda(), getMaxLambda(),
                getMinMu(), getMaxMu()
            );

            experimentService.recalcCoefficients();
            var plan = experimentService.getPlanMatrix();

            setY(plan.getY());
            setYL(plan.getY_Linear());
            setYNL(plan.getY_NonLinear());
            setYDL(plan.getY_LinearError());
            setYDNL(plan.getY_NonLinearError());
            
            setLinearRegressionNorm(plan.getB_Linear());
            setLinearRegressionDenorm(experimentService.getB_LinearDenorm());
            setNonLinearRegressionNorm(plan.getB_NonLinear());
            setNonLinearRegressionDenorm(experimentService.getB_NonLinearDenorm());
        } catch (Exception e) {
            e.printStackTrace();
            displayErrorMessage("Ошибка", e.getMessage());
        }
    }

    @FXML
    private void predictButtonPressed() {
        double x1, x2;

        try {
            x1 = Double.parseDouble(x1TextField.getText());
        } catch (Exception e) {
            displayInvalidInputErrorMessage("λ");
            return;
        }

        try {
            x2 = Double.parseDouble(x2TextField.getText());
        } catch (Exception e) {
            displayInvalidInputErrorMessage("μ");
            return;
        }

        var plan = experimentService.getPlanMatrix();
        double yLHat = plan.calcY_Linear(x1, x2);
        double yNLHat = plan.calcY_NonLinear(x1, x2);

        if (normalizedInputCheckBox.isSelected()) {
            x1 = experimentService.getExperimentor().denormalizeLambda(x1);
            x2 = experimentService.getExperimentor().denormalizeMu(x2);
        }

        double y = experimentService.realAt(x1, x2);

        setYOutput(y);
        setYLHatOutput(yLHat);
        setYNLHatOutput(yNLHat);
    }

    @FXML
    public void initialize() {
    }
}
