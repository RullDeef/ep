package explan.view;

import org.ejml.simple.SimpleMatrix;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Lab2View extends BaseView {
    @FXML TextField minLambda;
    @FXML TextField maxLambda;
    @FXML TextField minMu;
    @FXML TextField maxMu;

    @FXML TextField yOutputText;
    @FXML TextField yLHatOutputText;
    @FXML TextField yNLHatOutputText;

    @FXML GridPane planMatrixGrid;

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

    @FXML Label regressionNormL;
    @FXML Label regressionDenormL;
    @FXML Label regressionNormNL;
    @FXML Label regressionDenormNL;

    final static String defaultYFormat = "%.3f";

    public double getMinLambda() throws Exception {
        return getTextFieldValue("min λ", minLambda);
    }

    public double getMaxLambda() throws Exception {
        return getTextFieldValue("max λ", maxLambda);
    }

    public double getMinMu() throws Exception {
        return getTextFieldValue("min μ", minMu);
    }

    public double getMaxMu() throws Exception {
        return getTextFieldValue("max μ", maxMu);
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
    
    public void setYLHatOutput(double yLHat) {
        yLHatOutputText.setText(String.format(defaultYFormat, yLHat));
    }
    
    public void setYNLHatOutput(double yNLHat) {
        yNLHatOutputText.setText(String.format(defaultYFormat, yNLHat));
    }

    public void setY(SimpleMatrix Y) {
        assert Y.getNumRows() == 4 && Y.getNumCols() == 1;

        Y1.setText(String.format(defaultYFormat, Y.get(0)));
        Y2.setText(String.format(defaultYFormat, Y.get(1)));
        Y3.setText(String.format(defaultYFormat, Y.get(2)));
        Y4.setText(String.format(defaultYFormat, Y.get(3)));
    }

    public void setYL(SimpleMatrix Y) {
        assert Y.getNumRows() == 4 && Y.getNumCols() == 1;

        Y1L.setText(String.format(defaultYFormat, Y.get(0)));
        Y2L.setText(String.format(defaultYFormat, Y.get(1)));
        Y3L.setText(String.format(defaultYFormat, Y.get(2)));
        Y4L.setText(String.format(defaultYFormat, Y.get(3)));
    }

    public void setYNL(SimpleMatrix Y) {
        assert Y.getNumRows() == 4 && Y.getNumCols() == 1;

        Y1NL.setText(String.format(defaultYFormat, Y.get(0)));
        Y2NL.setText(String.format(defaultYFormat, Y.get(1)));
        Y3NL.setText(String.format(defaultYFormat, Y.get(2)));
        Y4NL.setText(String.format(defaultYFormat, Y.get(3)));
    }

    public void setYDL(SimpleMatrix Y) {
        assert Y.getNumRows() == 4 && Y.getNumCols() == 1;

        Y1DL.setText(String.format(defaultYFormat, Y.get(0)));
        Y2DL.setText(String.format(defaultYFormat, Y.get(1)));
        Y3DL.setText(String.format(defaultYFormat, Y.get(2)));
        Y4DL.setText(String.format(defaultYFormat, Y.get(3)));
    }

    public void setYDNL(SimpleMatrix Y) {
        assert Y.getNumRows() == 4 && Y.getNumCols() == 1;

        Y1DNL.setText(String.format(defaultYFormat, Y.get(0)));
        Y2DNL.setText(String.format(defaultYFormat, Y.get(1)));
        Y3DNL.setText(String.format(defaultYFormat, Y.get(2)));
        Y4DNL.setText(String.format(defaultYFormat, Y.get(3)));
    }

    public void setLinearRegressionNorm(SimpleMatrix B) {
        assert B.getNumRows() == 3 && B.getNumCols() == 1;

        var text = new StringBuilder()
            .append("y = ")
            .append(String.format("%.3f*x0 ", B.get(0)))
            .append(buildNumOpString(B.get(1), "x1"))
            .append(' ')
            .append(buildNumOpString(B.get(2), "x2"))
            .toString();
        
        regressionNormL.setText(text);
    }

    public void setLinearRegressionDenorm(SimpleMatrix B) {
        assert B.getNumRows() == 3 && B.getNumCols() == 1;

        var text = new StringBuilder()
            .append("y = ")
            .append(String.format("%.3f ", B.get(0)))
            .append(buildNumOpString(B.get(1), "λ"))
            .append(' ')
            .append(buildNumOpString(B.get(2), "μ"))
            .toString();
        
        regressionDenormL.setText(text);
    }

    public void setNonLinearRegressionNorm(SimpleMatrix B) {
        assert B.getNumRows() == 4 && B.getNumCols() == 1;

        var text = new StringBuilder()
            .append("y = ")
            .append(String.format("%.3f*x0 ", B.get(0)))
            .append(buildNumOpString(B.get(1), "x1"))
            .append(' ')
            .append(buildNumOpString(B.get(2), "x2"))
            .append(' ')
            .append(buildNumOpString(B.get(3), "x1*x2"))
            .toString();
        
        regressionNormNL.setText(text);
    }

    public void setNonLinearRegressionDenorm(SimpleMatrix B) {
        assert B.getNumRows() == 4 && B.getNumCols() == 1;

        var text = new StringBuilder()
            .append("y = ")
            .append(String.format("%.3f ", B.get(0)))
            .append(buildNumOpString(B.get(1), "λ"))
            .append(' ')
            .append(buildNumOpString(B.get(2), "μ"))
            .append(' ')
            .append(buildNumOpString(B.get(3), "λ*μ"))
            .toString();
        
        regressionDenormNL.setText(text);
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
