package explan.model;

import org.ejml.simple.SimpleMatrix;

import explan.model.experiment.FracPlanMatrix4;
import explan.model.plan.FactorTransformer;

public class ExtendedExperimentService {
    private ExtendedExperimentor experimentor;
    private FracPlanMatrix4 planMatrix;

    public ExtendedExperimentService() {
        experimentor = new ExtendedExperimentor(new ExtendedSimulationService(),
            new FactorTransformer(0.8, 1.2),
            new FactorTransformer(2, 4),
            new FactorTransformer(0.8, 1.2),
            new FactorTransformer(2, 4)
        );
    }

    public FracPlanMatrix4 getPlanMatrix() {
        assert planMatrix != null;
        return planMatrix;
    }

    public void setExperimentSpace(
        double minLambda1, double maxLambda1,
        double minMu1, double maxMu1,
        double minLambda2, double maxLambda2,
        double minMu2, double maxMu2
    ) throws Exception {
        var lambda1 = new FactorTransformer(minLambda1, maxLambda1);
        var mu1 = new FactorTransformer(minMu1, maxMu1);
        var lambda2 = new FactorTransformer(minLambda2, maxLambda2);
        var mu2 = new FactorTransformer(minMu2, maxMu2);

        if (lambda1.isInverted()) {
            throw new Exception("Некорректный интервал варьирования λ1");
        } else if (lambda1.I() < 0.01) {
            throw new Exception("Интервал варьирования λ1 слишком мал");
        }
        if (lambda2.isInverted()) {
            throw new Exception("Некорректный интервал варьирования λ2");
        } else if (lambda2.I() < 0.01) {
            throw new Exception("Интервал варьирования λ2 слишком мал");
        }

        if (mu1.isInverted()) {
            throw new Exception("Некорректный интервал варьирования μ1");
        } else if (mu1.I() < 0.01) {
            throw new Exception("Интервал варьирования μ1 слишком мал");
        }
        if (mu2.isInverted()) {
            throw new Exception("Некорректный интервал варьирования μ2");
        } else if (mu2.I() < 0.01) {
            throw new Exception("Интервал варьирования μ2 слишком мал");
        }

        experimentor.setTransformer("x1", lambda1);
        experimentor.setTransformer("x2", mu1);
        experimentor.setTransformer("x3", lambda2);
        experimentor.setTransformer("x4", mu2);
    }

    public void recalcCoefficients() {
        try {
            // Используем генератор плана: x4 = x1x2x3
            var Y = new SimpleMatrix(8, 1, true,
                experimentor.y(-1, -1, -1, -1),
                experimentor.y( 1, -1, -1,  1),
                experimentor.y(-1,  1, -1,  1),
                experimentor.y( 1,  1, -1, -1),
                experimentor.y(-1, -1,  1,  1),
                experimentor.y( 1, -1,  1, -1),
                experimentor.y(-1,  1,  1, -1),
                experimentor.y( 1,  1,  1,  1)
            );
            planMatrix = new FracPlanMatrix4(Y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SimpleMatrix getB_Denorm() {
        var B = planMatrix.getB().copy();
        
        double b0 = B.get(0);
        double b1 = B.get(1);
        double b2 = B.get(2);
        double b3 = B.get(3);
        double b4 = B.get(4);
        double b12 = B.get(5);
        double b13 = B.get(6);
        double b23 = B.get(7);
        double b14 = b23;
        double b24 = b13;
        double b34 = b12;

        double dx1 = experimentor.I("x1");
        double dx2 = experimentor.I("x2");
        double dx3 = experimentor.I("x3");
        double dx4 = experimentor.I("x4");
        double dx12 = dx1 * dx2;
        double dx13 = dx1 * dx3;
        double dx14 = dx1 * dx4;
        double dx23 = dx2 * dx3;
        double dx24 = dx2 * dx4;
        double dx34 = dx3 * dx4;

        double x10 = experimentor.I0("x1");
        double x20 = experimentor.I0("x2");
        double x30 = experimentor.I0("x3");
        double x40 = experimentor.I0("x4");

        // transform coeffs
        B.set(1, b1 / dx1 - b12 * x20 / dx12 - b13 * x30 / dx13 - b14 * x40 / dx14);
        B.set(2, b2 / dx2 - b12 * x10 / dx12);
        B.set(3, b3 / dx3 - b13 * x20 / dx12);
        B.set(4, b4 / dx4 - b14 * x20 / dx12);

        B.set(5, b12 / dx12);
        B.set(6, b13 / dx13);
        B.set(7, b14 / dx14);

        B.set(0, b0
            - b1 * x10 / dx1 - b2 * x20 / dx2 - b3 * x30 / dx3 - b4 * x40 / dx4
            + b12 * x10 * x20 / dx12
            + b13 * x10 * x30 / dx13
            + b14 * x10 * x40 / dx14
        );

        return B;
    }

    public double realAt(double lambda1, double mu1, double lambda2, double mu2) {
        return experimentor.yDenorm(lambda1, mu1, lambda2, mu2);
    }

    public double realAtNorm(double x1, double x2, double x3, double x4) {
        return experimentor.y(x1, x2, x3, x4);
    }
}
