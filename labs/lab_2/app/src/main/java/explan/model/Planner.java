package explan.model;

/**
 * Содержит матрицу планирования и производит перерасчет коэффициентов.
 * Подходит только для ПФЭ вида 2^2.
 */
public class Planner {
    private double[] y;
    private double[] b;

    private boolean linearRegression = true;

    public void setLinear() {
        if (!linearRegression && y != null) {
            linearRegression = true;
            recalcB();
        } else {
            linearRegression = true;
        }
    }

    public void setNonLinear() {
        if (linearRegression && y != null) {
            linearRegression = false;
            recalcB();
        } else {
            linearRegression = false;
        }
    }

    public boolean isLinear() {
        return linearRegression;
    }

    public void setY(double[] y) throws Exception {
        if (y.length != 4) {
            throw new Exception("Размер вектора Y должен быть равен 4");
        }
        this.y = y;

        recalcB();
    }

    /**
     * Возвращает элемент матрицы планирования X.
     * Так как рассматривается ПФЭ вида 2^2, то результат - либо +1 либо -1.
     * 
     * @param row строка матрицы
     * @param col столбец матрицы
     */
    public double planMatrixAt(int row, int col) {
        if (row < 0 || row > 3) {
            throw new IndexOutOfBoundsException();
        }

        if (linearRegression && (col < 0 || col > 2) || !linearRegression && (col < 0 || col > 3)) {
            throw new IndexOutOfBoundsException();
        }

        if (col == 2) {
            return planMatrixAt(row, col - 2) * planMatrixAt(row, col - 1);
        } else if (col == 0 || row == col || row == 3) {
            return 1.0;
        } else {
            return -1.0;
        }
    }

    public double yAt(int row) throws Exception {
        if (y == null) {
            throw new Exception("Не установлен вектор значений Y");
        }

        if (row < 0 || row > 3) {
            throw new IndexOutOfBoundsException();
        }

        return y[row];
    }

    public double bAt(int row) throws Exception {
        if (b == null) {
            throw new Exception("Коэффициенты не расчитаны. Не установлен вектор значений Y");
        }

        if (row < 0 || row > 3) {
            throw new IndexOutOfBoundsException();
        }

        return b[row];
    }

    public double bDenormAt(int row, FactorTransformer x1, FactorTransformer x2) throws Exception {
        if (b == null) {
            throw new Exception("Коэффициенты не расчитаны. Не установлен вектор значений Y");
        }

        if (row < 0 || row > 3) {
            throw new IndexOutOfBoundsException();
        }

        if (linearRegression) {
            if (row == 0) {
                return b[0] + x1.min() * 2 / x1.I() + x2.min() * 2 / x2.I();
            } else if (row == 1) {
                return b[1] * x1.I() / 2;
            } else if (row == 2) {
                return b[2] * x2.I() / 2;
            } else {
                throw new Exception("invalid row");
            }
        } else {
            double II = x1.I() * x2.I() / 4;

            if (row == 0) {
                return b[0] + x1.min() * 2 / x1.I() + x2.min() * 2 / x2.I() + x1.min() * x2.min() / II;
            } else if (row == 1) {
                return b[1] * x1.I() / 2 - x2.min() / II;
            } else if (row == 2) {
                return b[2] * x2.I() / 2 - x1.min() / II;
            } else if (row == 3) {
                return b[1] * II;
            } else {
                throw new Exception("invalid row");
            }
        }
    }

    private void recalcB() {
        if (linearRegression) {
            b = new double[3];
            for (int i = 0; i < 3; i++) {
                b[i] = 0.0;
                for (int u = 0; u < 4; u++) {
                    b[i] += planMatrixAt(u, i) * y[u];
                }
                b[i] /= 4;
            }
        } else {
            b = new double[4];
            for (int i = 0; i < 4; i++) {
                b[i] = 0.0;
                for (int u = 0; u < 4; u++) {
                    b[i] += planMatrixAt(u, i) * y[u];
                }
                b[i] /= 4;
            }
        }
    }
}
