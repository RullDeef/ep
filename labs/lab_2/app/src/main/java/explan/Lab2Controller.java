package explan;

import explan.model.ExperimentService;
import explan.model.SimulationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class Lab2Controller {

    SimulationService simulationService = new SimulationService();
    ExperimentService experimentServiceL = new ExperimentService();
    ExperimentService experimentServiceNL = new ExperimentService();

    @FXML
    TextField x1TextField;

    @FXML
    TextField x2TextField;

    @FXML
    CheckBox normalizedInputCheckBox;

    @FXML
    TextField yOutputText;

    @FXML
    TextField yLHatOutputText;

    @FXML
    TextField yNLHatOutputText;

    @FXML
    GridPane planMatrixGrid;

    @FXML Label regressionNormL;
    @FXML Label regressionDenormL;
    @FXML Label regressionNormNL;
    @FXML Label regressionDenormNL;

    @FXML Text Y1;
    @FXML Text Y2;
    @FXML Text Y3;
    @FXML Text Y4;

    @FXML Text Y1L;
    @FXML Text Y2L;
    @FXML Text Y3L;
    @FXML Text Y4L;

    @FXML Text Y1NL;
    @FXML Text Y2NL;
    @FXML Text Y3NL;
    @FXML Text Y4NL;

    @FXML Text Y1DL;
    @FXML Text Y2DL;
    @FXML Text Y3DL;
    @FXML Text Y4DL;

    @FXML Text Y1DNL;
    @FXML Text Y2DNL;
    @FXML Text Y3DNL;
    @FXML Text Y4DNL;

    @FXML
    private void recalcYButtonPressed() {
        experimentServiceL.recalcCoefficients();
        experimentServiceNL.recalcCoefficients();

        updateRegressionTexts();

        try {
            Y1.setText(String.format("%.3f", experimentServiceL.getPlanner().yAt(0)));
            Y2.setText(String.format("%.3f", experimentServiceL.getPlanner().yAt(1)));
            Y3.setText(String.format("%.3f", experimentServiceL.getPlanner().yAt(2)));
            Y4.setText(String.format("%.3f", experimentServiceL.getPlanner().yAt(3)));
            
            Y1L.setText(String.format("%.3f", experimentServiceL.predictNormalized(-1, -1)));
            Y2L.setText(String.format("%.3f", experimentServiceL.predictNormalized(1, -1)));
            Y3L.setText(String.format("%.3f", experimentServiceL.predictNormalized(-1, 1)));
            Y4L.setText(String.format("%.3f", experimentServiceL.predictNormalized(1, 1)));
            
            Y1NL.setText(String.format("%.3f", experimentServiceNL.predictNormalized(-1, -1)));
            Y2NL.setText(String.format("%.3f", experimentServiceNL.predictNormalized(1, -1)));
            Y3NL.setText(String.format("%.3f", experimentServiceNL.predictNormalized(-1, 1)));
            Y4NL.setText(String.format("%.3f", experimentServiceNL.predictNormalized(1, 1)));
            
            Y1DL.setText(String.format("%.3f", experimentServiceL.diffAt(-1, -1)));
            Y2DL.setText(String.format("%.3f", experimentServiceL.diffAt(1, -1)));
            Y3DL.setText(String.format("%.3f", experimentServiceL.diffAt(-1, 1)));
            Y4DL.setText(String.format("%.3f", experimentServiceL.diffAt(1, 1)));

            Y1DNL.setText(String.format("%.3f", experimentServiceNL.diffAt(-1, -1)));
            Y2DNL.setText(String.format("%.3f", experimentServiceNL.diffAt(1, -1)));
            Y3DNL.setText(String.format("%.3f", experimentServiceNL.diffAt(-1, 1)));
            Y4DNL.setText(String.format("%.3f", experimentServiceNL.diffAt(1, 1)));
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
            x1 = experimentServiceL.getExperimentor().denormalizeLambda(x1);
            x2 = experimentServiceL.getExperimentor().denormalizeMu(x2);
        }

        double y = experimentServiceL.realAt(x1, x2);
        double yLHat = experimentServiceL.predictAt(x1, x2);
        double yNLHat = experimentServiceNL.predictAt(x1, x2);

        yOutputText.setText(String.format("%.2f", y));
        yLHatOutputText.setText(String.format("%.2f", yLHat));
        yNLHatOutputText.setText(String.format("%.2f", yNLHat));
    }

    private void updateRegressionTexts() {
        try {
            regressionNormL.setText(String.format(
                "y = %.2gx0 + %.2gx1 + %.2gx2",
                experimentServiceL.getPlanner().bAt(0),
                experimentServiceL.getPlanner().bAt(1),
                experimentServiceL.getPlanner().bAt(2)
            ));

            regressionDenormL.setText(String.format(
                "y = %.2g + %.2g*lambda + %.2g*mu",
                experimentServiceL.bDenormAt(0),
                experimentServiceL.bDenormAt(1),
                experimentServiceL.bDenormAt(2)
            ));

            regressionNormNL.setText(String.format(
                "y = %.2gx0 + %.2gx1 + %.2gx2 + %.2gx1x2",
                experimentServiceNL.getPlanner().bAt(0),
                experimentServiceNL.getPlanner().bAt(1),
                experimentServiceNL.getPlanner().bAt(2),
                experimentServiceNL.getPlanner().bAt(3)
            ));

            regressionDenormNL.setText(String.format(
                "y = %.2g + %.2g*lambda + %.2g*mu + %.2g*lambda*mu",
                experimentServiceNL.bDenormAt(0),
                experimentServiceNL.bDenormAt(1),
                experimentServiceNL.bDenormAt(2),
                experimentServiceNL.bDenormAt(3)
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {
        experimentServiceL.getPlanner().setLinear();
        experimentServiceNL.getPlanner().setNonLinear();
    }

    @FXML
    private void aboutMenuAction() {
        var errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("О программе");
        errorAlert.setContentText(
                "Лабораторные работы\nпо курсу \"Планирование Эксперимента\".\nВариант 1.\nСтудент: Клименко Алексей, ИУ7-85Б.");
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
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
