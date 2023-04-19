package explan.model;

import org.ejml.simple.SimpleMatrix;

import explan.model.experiment.IPlanMatrix;
import explan.model.experiment.OCCPMatrix4;
import explan.model.plan.FactorTransformer;

public class OCCPExperimentService implements IExtendedExperimentService {
    private ExtendedExperimentor experimentor;
    private IPlanMatrix planMatrix;

    public OCCPExperimentService() {
        experimentor = new ExtendedExperimentor(new ExtendedSimulationService(),
            new FactorTransformer(0.8, 1.2),
            new FactorTransformer(2, 4),
            new FactorTransformer(0.8, 1.2),
            new FactorTransformer(2, 4)
        );
    }

    @Override
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

    @Override
    public void recalcCoefficients() {
        try {
            planMatrix = new OCCPMatrix4(
                t -> experimentor.y(t.get(0), t.get(1), t.get(2), t.get(3))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SimpleMatrix getB_Denorm() {
        var B = planMatrix.getB().copy();
        
        double b0 = B.get(0);
        double b1 = B.get(1);
        double b2 = B.get(2);
        double b3 = B.get(3);
        double b4 = B.get(4);
        double b12 = B.get(5);
        double b13 = B.get(6);
        double b14 = B.get(7);
        double b23 = B.get(8);
        double b24 = B.get(9);
        double b34 = B.get(10);
        double b11 = B.get(11);
        double b22 = B.get(12);
        double b33 = B.get(13);
        double b44 = B.get(14);

        // TODO: implement

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
        double dx11 = dx1 * dx1;
        double dx22 = dx2 * dx2;
        double dx33 = dx3 * dx3;
        double dx44 = dx4 * dx4;

        double x10 = experimentor.I0("x1");
        double x20 = experimentor.I0("x2");
        double x30 = experimentor.I0("x3");
        double x40 = experimentor.I0("x4");

        // transform coeffs
        B.set(1, b1 / dx1 - b12 * x20 / dx12 - b13 * x30 / dx13 - b14 * x40 / dx14 - 2 * b11 * x10 / dx11);
        B.set(2, b2 / dx2 - b12 * x10 / dx12 - b23 * x30 / dx23 - b24 * x40 / dx24 - 2 * b22 * x20 / dx22);
        B.set(3, b3 / dx3 - b13 * x10 / dx13 - b23 * x20 / dx23 - b34 * x40 / dx34 - 2 * b33 * x30 / dx33);
        B.set(4, b4 / dx4 - b14 * x10 / dx14 - b24 * x40 / dx24 - b34 * x30 / dx34 - 2 * b44 * x40 / dx44);

        B.set(5, b12 / dx12);
        B.set(6, b13 / dx13);
        B.set(7, b14 / dx14);
        B.set(8, b23 / dx23);
        B.set(9, b24 / dx24);
        B.set(10, b34 / dx34);
        B.set(11, b11 / dx11);
        B.set(12, b22 / dx22);
        B.set(13, b33 / dx33);
        B.set(14, b44 / dx44);

        B.set(0, b0
            - b1 * x10 / dx1 - b2 * x20 / dx2 - b3 * x30 / dx3 - b4 * x40 / dx4
            + b12 * x10 * x20 / dx12
            + b13 * x10 * x30 / dx13
            + b14 * x10 * x40 / dx14
            + b23 * x20 * x30 / dx23
            + b24 * x20 * x40 / dx24
            + b34 * x30 * x40 / dx34
            + b11 * x10 * x10 / dx11
            + b22 * x20 * x20 / dx22
            + b33 * x30 * x30 / dx33
            + b44 * x40 * x40 / dx44
        );

        return B;
    }

    @Override
    public double realAt(double lambda1, double mu1, double lambda2, double mu2) {
        return experimentor.yDenorm(lambda1, mu1, lambda2, mu2);
    }

    @Override
    public double realAtNorm(double x1, double x2, double x3, double x4) {
        return experimentor.y(x1, x2, x3, x4);
    }

    @Override
    public double calcY(double lambda1, double mu1, double lambda2, double mu2) {
        return planMatrix.calcY(
            new SimpleMatrix(1, 4, true, 
                experimentor.normalize("x1", lambda1),
                experimentor.normalize("x2", mu1),
                experimentor.normalize("x3", lambda2),
                experimentor.normalize("x4", mu2)
            )
        );
    }

    @Override
    public double calcYNorm(double x1, double x2, double x3, double x4) {
        return planMatrix.calcY(
            new SimpleMatrix(1, 4, true, x1, x2, x3, x4)
        );
    }

    @Override
    public SimpleMatrix getB_Norm() {
        return planMatrix.getB();
    }

    @Override
    public SimpleMatrix getY() {
        return planMatrix.getY();
    }

    @Override
    public SimpleMatrix getYPredicted() {
        return planMatrix.getYPredicted();
    }

    @Override
    public SimpleMatrix getYPredictedError() {
        return planMatrix.getYPredictedError();
    }
}
