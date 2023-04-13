package explan.model.experiment;

import java.util.function.Function;

import org.ejml.simple.SimpleMatrix;

/**
 * Матрица ортогонального центрального композиционного плана (ОЦКП)
 * с 4-мя факторами.
 * 
 * Параметры плана:
 *   N = 2^4 + 2*4 + 1 = 25 - общее кол-во опытов
 *   \alpha = \sqrt{2} = 1.41 - звездное плечо
 *   a = \sqrt{16/25} = 0.8 - центрирующий параметр
 */
public class OCCPMatrix4 implements IPlanMatrix {
    private SimpleMatrix X;
    private SimpleMatrix Y;

    private SimpleMatrix B;

    public OCCPMatrix4(Function<SimpleMatrix, Double> Y) {
        final double a = 0.8;
        final double al = Math.sqrt(2);
        final double al2 = 2;

        X = new SimpleMatrix(new double[][] {
            //            x0  x1  x2  x3  x4 x1x2 x1x3 x1x4 x2x3 x2x4 x3x4  x1x1  x2x2  x3x3  x4x4
            new double[] { 1, -1, -1, -1, -1,   1,   1,   1,   1,   1,   1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1,  1, -1, -1, -1,  -1,  -1,  -1,   1,   1,   1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1, -1,  1, -1, -1,  -1,   1,   1,  -1,  -1,   1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1,  1,  1, -1, -1,   1,  -1,  -1,  -1,  -1,   1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1, -1, -1,  1, -1,   1,  -1,   1,  -1,   1,  -1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1,  1, -1,  1, -1,  -1,   1,  -1,  -1,   1,  -1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1, -1,  1,  1, -1,  -1,  -1,   1,   1,  -1,  -1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1,  1,  1,  1, -1,   1,   1,  -1,   1,  -1,  -1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1, -1, -1, -1,  1,   1,   1,  -1,   1,  -1,  -1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1,  1, -1, -1,  1,  -1,  -1,   1,   1,  -1,  -1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1, -1,  1, -1,  1,  -1,   1,  -1,  -1,   1,  -1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1,  1,  1, -1,  1,   1,  -1,   1,  -1,   1,  -1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1, -1, -1,  1,  1,   1,  -1,  -1,  -1,  -1,   1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1,  1, -1,  1,  1,  -1,   1,   1,  -1,  -1,   1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1, -1,  1,  1,  1,  -1,  -1,  -1,   1,   1,   1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1,  1,  1,  1,  1,   1,   1,   1,   1,   1,   1,  1-a,  1-a,  1-a,  1-a},
            new double[] { 1,-al,  0,  0,  0,   0,   0,   0,   0,   0,   0,al2-a,  0-a,  0-a,  0-a},
            new double[] { 1, al,  0,  0,  0,   0,   0,   0,   0,   0,   0,al2-a,  0-a,  0-a,  0-a},
            new double[] { 1,  0,-al,  0,  0,   0,   0,   0,   0,   0,   0,  0-a,al2-a,  0-a,  0-a},
            new double[] { 1,  0, al,  0,  0,   0,   0,   0,   0,   0,   0,  0-a,al2-a,  0-a,  0-a},
            new double[] { 1,  0,  0,-al,  0,   0,   0,   0,   0,   0,   0,  0-a,  0-a,al2-a,  0-a},
            new double[] { 1,  0,  0, al,  0,   0,   0,   0,   0,   0,   0,  0-a,  0-a,al2-a,  0-a},
            new double[] { 1,  0,  0,  0,-al,   0,   0,   0,   0,   0,   0,  0-a,  0-a,  0-a,al2-a},
            new double[] { 1,  0,  0,  0, al,   0,   0,   0,   0,   0,   0,  0-a,  0-a,  0-a,al2-a},
            new double[] { 1,  0,  0,  0,  0,   0,   0,   0,   0,   0,   0,  0-a,  0-a,  0-a,  0-a}
        });
        this.Y = new SimpleMatrix(25, 1);

        for (int row = 0; row < this.Y.getNumRows(); row++) {
            double x1 = X.get(row, 1);
            double x2 = X.get(row, 2);
            double x3 = X.get(row, 3);
            double x4 = X.get(row, 4);

            double y = Y.apply(new SimpleMatrix(1, 4, true, x1, x2, x3, x4));
            this.Y.set(row, y);
        }

        // recalculate B
        B = new SimpleMatrix(15, 1);

        for (int col = 0; col < B.getNumRows(); col++) {
            double b = 0;
            double denom = 0;
            for (int row = 0; row < X.getNumRows(); row++) {
                b += X.get(row, col) * this.Y.get(row);
                denom += Math.pow(X.get(row, col), 2);
            }
            B.set(col, b / denom);
        }

        // fix b_0
        B.set(0, B.get(0) - a * (B.get(11) + B.get(12) + B.get(13) + B.get(14)));
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

        return new SimpleMatrix(1, 15, true, 1,
            x1,
            x2,
            x3,
            x4,
            x1*x2,
            x1*x3,
            x1*x4,
            x2*x3,
            x2*x4,
            x3*x4,
            x1*x1,
            x2*x2,
            x3*x3,
            x4*x4
        ).dot(B);
    }

    @Override
    public SimpleMatrix getYPredicted() {
        var y = new SimpleMatrix(25, 1);
        for (int row = 0; row < 25; row++) {
            var x = X.rows(row, row + 1).cols(1, 5);
            y.set(row, calcY(x));
        }
        return y;
    }

    @Override
    public SimpleMatrix getYPredictedError() {
        return Y.minus(getYPredicted()).magnitude();
    }
}
