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
            experimentService.recalcCoefficients();
            var plan = experimentService.getPlanMatrix();

            setY(plan.getY());
            setYL(plan.getY_Linear());
            setYNL(plan.getY_NonLinear());
            setYDL(plan.getY_LinearError());
            setYDNL(plan.getY_NonLinearError());

            updateRegressionTexts();
        } catch (Exception e) {
            e.printStackTrace();
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

        if (normalizedInputCheckBox.isSelected()) {
            x1 = experimentService.getExperimentor().denormalizeLambda(x1);
            x2 = experimentService.getExperimentor().denormalizeMu(x2);
        }

        double y = experimentService.realAt(x1, x2);

        var plan = experimentService.getPlanMatrix();
        double yLHat = plan.calcY_Linear(x1, x2);
        double yNLHat = plan.calcY_NonLinear(x1, x2);

        setYOutput(y);
        setYLHatOutput(yLHat);
        setYNLHatOutput(yNLHat);
    }

    private void updateRegressionTexts() {
        try {
            var plan = experimentService.getPlanMatrix();

            setLinearRegressionNorm(plan.getB_Linear());
            setNonLinearRegressionNorm(plan.getB_NonLinear());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
    }
}
