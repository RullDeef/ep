package explan.model;

import org.ejml.simple.SimpleMatrix;

import explan.model.experiment.PlanMatrix;

public class ExperimentService {
    private Experimentor experimentor;
    private PlanMatrix planMatrix;

    public ExperimentService() {
        experimentor = new Experimentor(new SimulationService(),
                new FactorTransformer(0.8, 1.2),
                new FactorTransformer(2, 4));
    }

    public PlanMatrix getPlanMatrix() {
        assert planMatrix != null;

        return planMatrix;
    }

    public Experimentor getExperimentor() {
        return experimentor;
    }

    public void setLambdaTransform(double min, double max) {
        experimentor.setLambdaTransformer(
                new FactorTransformer(min, max));
    }

    public void setMuTransform(double min, double max) {
        experimentor.setMuTransformer(
                new FactorTransformer(min, max));
    }

    public void recalcCoefficients() {
        try {
            var Y = new SimpleMatrix(4, 1, true,
                experimentor.y(-1, -1),
                experimentor.y( 1, -1),
                experimentor.y(-1,  1),
                experimentor.y( 1,  1)
            );
            planMatrix = new PlanMatrix(Y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SimpleMatrix getB_LinearDenorm() {
        var B = planMatrix.getB_Linear();
        var Bnew = B.copy();

        double dx1 = experimentor.getLambdaTransform().I() / 2;
        double dx2 = experimentor.getMuTransformer().I() / 2;
        double x10 = experimentor.getLambdaTransform().I0();
        double x20 = experimentor.getMuTransformer().I0();

        // transform coeffs
        Bnew.set(1, B.get(1) / dx1);
        Bnew.set(2, B.get(2) / dx2);

        Bnew.set(0, B.get(0) - B.get(1) * x10 / dx1 - B.get(2) * x20 / dx2);

        return Bnew;
    }

    public SimpleMatrix getB_NonLinearDenorm() {
        var B = planMatrix.getB_NonLinear();
        var Bnew = B.copy();

        double dx1 = experimentor.getLambdaTransform().I() / 2;
        double dx2 = experimentor.getMuTransformer().I() / 2;
        double x10 = experimentor.getLambdaTransform().I0();
        double x20 = experimentor.getMuTransformer().I0();

        // transform coeffs
        Bnew.set(1, B.get(1) / dx1 - B.get(3) * x20 / (dx1 * dx2));
        Bnew.set(2, B.get(2) / dx2 - B.get(3) * x10 / (dx1 * dx2));

        Bnew.set(0, B.get(0)
            - B.get(1) * x10 / dx1
            - B.get(2) * x20 / dx2
            + B.get(3) * x10 * x20 / (dx1 * dx2));

        return Bnew;
    }

    public double realAt(double lambda, double mu) {
        double x1 = experimentor.normalizeLambda(lambda);
        double x2 = experimentor.normalizeMu(mu);

        return experimentor.y(x1, x2);
    }

    public double predictNormalized(double lambda, double mu) throws Exception {
        // try {
        //     double x1 = lambda;
        //     double x2 = mu;

        //     if (planner.isLinear()) {
        //         return 1.0 * planner.bAt(0)
        //                 + x1 * planner.bAt(1)
        //                 + x2 * planner.bAt(2);
        //     } else {
        //         return 1.0 * planner.bAt(0)
        //                 + x1 * planner.bAt(1)
        //                 + x2 * planner.bAt(2)
        //                 + x1 * x2 * planner.bAt(3);
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     return Double.NaN;
        // }
        throw new Exception();
    }

    public double predictAt(double lambda, double mu) throws Exception {
        // lambda = experimentor.normalizeLambda(lambda);
        // mu = experimentor.normalizeMu(mu);

        // return predictNormalized(lambda, mu);
        throw new Exception();
    }
}
