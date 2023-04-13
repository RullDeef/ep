package explan.view;

import org.ejml.simple.SimpleMatrix;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Lab3View extends BaseView {
    @FXML TextField minLambda1;
    @FXML TextField maxLambda1;
    @FXML TextField minMu1;
    @FXML TextField maxMu1;

    @FXML TextField minLambda2;
    @FXML TextField maxLambda2;
    @FXML TextField minMu2;
    @FXML TextField maxMu2;

    @FXML TextField yOutputText;
    @FXML TextField yHatOutputText;
    @FXML TextField yErrOutputText;

    @FXML Text Y1;
    @FXML Text Y2;
    @FXML Text Y3;
    @FXML Text Y4;
    @FXML Text Y5;
    @FXML Text Y6;
    @FXML Text Y7;
    @FXML Text Y8;

    @FXML Text Y1P;
    @FXML Text Y2P;
    @FXML Text Y3P;
    @FXML Text Y4P;
    @FXML Text Y5P;
    @FXML Text Y6P;
    @FXML Text Y7P;
    @FXML Text Y8P;

    @FXML Text Y1E;
    @FXML Text Y2E;
    @FXML Text Y3E;
    @FXML Text Y4E;
    @FXML Text Y5E;
    @FXML Text Y6E;
    @FXML Text Y7E;
    @FXML Text Y8E;

    @FXML Label regressionNorm;
    @FXML Label regressionDenorm;

    final static String defaultYFormat = "%.3f";

    public double getMinLambda1() throws Exception {
        return getTextFieldValue("min λ1", minLambda1);
    }

    public double getMaxLambda1() throws Exception {
        return getTextFieldValue("max λ1", maxLambda1);
    }

    public double getMinMu1() throws Exception {
        return getTextFieldValue("min μ1", minMu1);
    }

    public double getMaxMu1() throws Exception {
        return getTextFieldValue("max μ1", maxMu1);
    }

    public double getMinLambda2() throws Exception {
        return getTextFieldValue("min λ2", minLambda2);
    }

    public double getMaxLambda2() throws Exception {
        return getTextFieldValue("max λ2", maxLambda2);
    }

    public double getMinMu2() throws Exception {
        return getTextFieldValue("min μ2", minMu2);
    }

    public double getMaxMu2() throws Exception {
        return getTextFieldValue("max μ2", maxMu2);
    }

    private double getTextFieldValue(String name, TextField field) throws Exception {
        try {
            double value = Double.parseDouble(field.getText());
            if (value <= 0) {
                throw new Exception(String.format("Значение %s должно быть положительным", name));
            }
            return value;
        } catch (NumberFormatException e) {
            throw new Exception(String.format("Некорректный формат ввода (%s)", name));
        }
    }

    public void setYOutput(double y) {
        yOutputText.setText(String.format(defaultYFormat, y));
    }
    
    public void setYHatOutput(double yHat) {
        yHatOutputText.setText(String.format(defaultYFormat, yHat));
    }

    public void setYError(double err) {
        yErrOutputText.setText(String.format(defaultYFormat, err));
    }

    public void setY(SimpleMatrix Y) {
        assert Y.getNumRows() == 8 && Y.getNumCols() == 1;

        Y1.setText(String.format(defaultYFormat, Y.get(0)));
        Y2.setText(String.format(defaultYFormat, Y.get(1)));
        Y3.setText(String.format(defaultYFormat, Y.get(2)));
        Y4.setText(String.format(defaultYFormat, Y.get(3)));
        Y5.setText(String.format(defaultYFormat, Y.get(4)));
        Y6.setText(String.format(defaultYFormat, Y.get(5)));
        Y7.setText(String.format(defaultYFormat, Y.get(6)));
        Y8.setText(String.format(defaultYFormat, Y.get(7)));
    }

    public void setYP(SimpleMatrix Y) {
        assert Y.getNumRows() == 8 && Y.getNumCols() == 1;

        Y1P.setText(String.format(defaultYFormat, Y.get(0)));
        Y2P.setText(String.format(defaultYFormat, Y.get(1)));
        Y3P.setText(String.format(defaultYFormat, Y.get(2)));
        Y4P.setText(String.format(defaultYFormat, Y.get(3)));
        Y5P.setText(String.format(defaultYFormat, Y.get(4)));
        Y6P.setText(String.format(defaultYFormat, Y.get(5)));
        Y7P.setText(String.format(defaultYFormat, Y.get(6)));
        Y8P.setText(String.format(defaultYFormat, Y.get(7)));
    }

    public void setYDP(SimpleMatrix Y) {
        assert Y.getNumRows() == 8 && Y.getNumCols() == 1;

        Y1E.setText(String.format(defaultYFormat, Y.get(0)));
        Y2E.setText(String.format(defaultYFormat, Y.get(1)));
        Y3E.setText(String.format(defaultYFormat, Y.get(2)));
        Y4E.setText(String.format(defaultYFormat, Y.get(3)));
        Y5E.setText(String.format(defaultYFormat, Y.get(4)));
        Y6E.setText(String.format(defaultYFormat, Y.get(5)));
        Y7E.setText(String.format(defaultYFormat, Y.get(6)));
        Y8E.setText(String.format(defaultYFormat, Y.get(7)));
    }

    public void setRegressionNorm(SimpleMatrix B) {
        assert B.getNumRows() == 8 && B.getNumCols() == 1;

        var text = new StringBuilder()
            .append("y = ")
            .append(String.format("%.3f*x0 ", B.get(0)))
            .append(buildNumOpString(B.get(1), "x1"))
            .append(' ')
            .append(buildNumOpString(B.get(2), "x2"))
            .append(' ')
            .append(buildNumOpString(B.get(3), "x3"))
            .append(' ')
            .append(buildNumOpString(B.get(4), "x4"))
            .append(' ')
            .append(buildNumOpString(B.get(5), "x1x2"))
            .append(' ')
            .append(buildNumOpString(B.get(6), "x1x3"))
            .append(' ')
            .append(buildNumOpString(B.get(7), "x1x4"))
            .append(' ')
            .append(buildNumOpString(B.get(7), "x2x3"))
            .append(' ')
            .append(buildNumOpString(B.get(6), "x2x4"))
            .append(' ')
            .append(buildNumOpString(B.get(5), "x3x4"))
            .toString();
        
        System.out.printf("norm: %s\n", text);
        regressionNorm.setText(text);
    }

    public void setRegressionDenorm(SimpleMatrix B) {
        assert B.getNumRows() == 8 && B.getNumCols() == 1;

        var text = new StringBuilder()
            .append("y = ")
            .append(String.format("%.3f ", B.get(0)))
            .append(buildNumOpString(B.get(1), "λ1"))
            .append(' ')
            .append(buildNumOpString(B.get(2), "μ1"))
            .append(' ')
            .append(buildNumOpString(B.get(3), "λ2"))
            .append(' ')
            .append(buildNumOpString(B.get(4), "μ2"))
            .append(' ')
            .append(buildNumOpString(B.get(5), "λ1μ1"))
            .append(' ')
            .append(buildNumOpString(B.get(6), "λ1λ2"))
            .append(' ')
            .append(buildNumOpString(B.get(7), "λ1μ2"))
            .toString();
        
        System.out.printf("denorm: %s\n", text);
        regressionDenorm.setText(text);
    }

    private String buildNumOpString(double num, String postfix) {
        if (num == 0) {
            return String.format("(+ 0*%s)", postfix);
        } else if (num < 0) {
            return String.format("- %.3f*%s", -num, postfix);
        } else {
            return String.format("+ %.3f*%s", num, postfix);
        }
    }
}
