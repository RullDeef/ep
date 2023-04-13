package explan.model.experiment;

import org.ejml.simple.SimpleMatrix;

/**
 * Matrix for full factor plan of type 2^2.
 */
public class FullPlanMatrix2 {
    private SimpleMatrix X;
    private SimpleMatrix Y;

    private SimpleMatrix B;

    public FullPlanMatrix2(SimpleMatrix Y) {
        assert Y.getNumRows() == 4 && Y.getNumCols() == 1;

        X = new SimpleMatrix(new double[][] {
            new double[] { 1, -1, -1,  1},
            new double[] { 1,  1, -1, -1},
            new double[] { 1, -1,  1, -1},
            new double[] { 1,  1,  1,  1}
        });
        this.Y = Y;

        // recalculate B
        B = new SimpleMatrix(new double[] {0, 0, 0, 0});

        for (int col = 0; col < 4; col++) {
            double b = 0;
            for (int row = 0; row < 4; row++) {
                b += xAt(row, col) * yAt(row);
            }
            B.set(col, b / 4);
        }
    }

    public double xAt(int row, int col) {
        return X.get(row, col);
    }

    public double yAt(int row) {
        return Y.get(row);
    }

    public double bAt(int col) {
        return B.get(col);
    }

    public SimpleMatrix getY() {
        return Y;
    }

    public SimpleMatrix getB_Linear() {
        return B.rows(0, 3);
    }
    
    public SimpleMatrix getB_NonLinear() {
        return B;
    }

    public double calcY_Linear(double x1, double x2) {
        return new SimpleMatrix(1, 3, true, 1, x1, x2).dot(B.rows(0, 3));
    }

    public double calcY_NonLinear(double x1, double x2) {
        return new SimpleMatrix(1, 4, true, 1, x1, x2, x1*x2).dot(B);
    }

    public SimpleMatrix getY_Linear() {
        return X.cols(0, 3).mult(B.rows(0, 3));
    }

    public SimpleMatrix getY_NonLinear() {
        return X.mult(B);
    }

    public SimpleMatrix getY_LinearError() {
        return Y.minus(getY_Linear()).magnitude();
    }

    public SimpleMatrix getY_NonLinearError() {
        return Y.minus(getY_NonLinear()).magnitude();
    }
}
