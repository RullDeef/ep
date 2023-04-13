package explan.model.experiment;

import org.ejml.simple.SimpleMatrix;

/**
 * Матрица дробного факторного эксперимента типа 2^{4-1}_{IV}
 * с генератором x4 = x1x2x3,
 * определяющим контрастом I = x1x2x3x4
 * и разрешающей способностью 4.
 * 
 * Cхема смешивания:
 *   b1  -> beta1 + beta234
 *   b2  -> beta2 + beta134
 *   b3  -> beta3 + beta124
 *   b4  -> beta4 + beta123
 *   b12 -> beta12 + beta34
 *   b13 -> beta13 + beta24
 *   b14 -> beta14 + beta23
 */
public class FracPlanMatrix4 implements IPlanMatrix {
    private SimpleMatrix X;
    private SimpleMatrix Y;

    private SimpleMatrix B;

    public FracPlanMatrix4(SimpleMatrix Y) {
        assert Y.getNumRows() == 8 && Y.getNumCols() == 1;

        X = new SimpleMatrix(new double[][] {
            //            x0  x1  x2  x3  x4 x1x2 x1x3 x1x4 x2x3 x2x4 x3x4
            new double[] { 1, -1, -1, -1, -1,   1,   1,   1,   1,   1,  1},
            new double[] { 1,  1, -1, -1,  1,  -1,  -1,   1,   1,  -1, -1},
            new double[] { 1, -1,  1, -1,  1,  -1,   1,  -1,  -1,   1, -1},
            new double[] { 1,  1,  1, -1, -1,   1,  -1,  -1,  -1,  -1,  1},
            new double[] { 1, -1, -1,  1,  1,   1,  -1,  -1,  -1,  -1,  1},
            new double[] { 1,  1, -1,  1, -1,  -1,   1,  -1,  -1,   1, -1},
            new double[] { 1, -1,  1,  1, -1,  -1,  -1,   1,   1,  -1, -1},
            new double[] { 1,  1,  1,  1,  1,   1,   1,   1,   1,   1,  1}
        });
        this.Y = Y;

        // recalculate B
        B = new SimpleMatrix(11, 1);

        for (int col = 0; col < B.getNumRows(); col++) {
            double b = 0;
            for (int row = 0; row < X.getNumRows(); row++) {
                b += X.get(row, col) * Y.get(row);
            }
            B.set(col, b / Y.getNumRows());
        }
    }

    @Override
    public SimpleMatrix getY() {
        return Y;
    }

    @Override
    public SimpleMatrix getB() {
        return B;
    }

    @Override
    public double calcY(SimpleMatrix X) {
        double x1 = X.get(0);
        double x2 = X.get(1);
        double x3 = X.get(2);
        double x4 = X.get(3);

        return new SimpleMatrix(1, 11, true, 1,
            x1,
            x2,
            x3,
            x4,
            x1*x2,
            x1*x3,
            x1*x4,
            x2*x3,
            x2*x4,
            x3*x4
        ).dot(B);
    }

    @Override
    public SimpleMatrix getYPredicted() {
        return X.mult(B);
    }

    @Override
    public SimpleMatrix getYPredictedError() {
        return Y.minus(getYPredicted()).magnitude();
    }
}
