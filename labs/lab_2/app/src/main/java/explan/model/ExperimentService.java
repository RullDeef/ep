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

    public double bDenormAt(int row) throws Exception {
        // var lambda = experimentor.getLambdaTransform();
        // var mu = experimentor.getMuTransformer();

        // return planner.bDenormAt(row, lambda, mu);
        throw new Exception();
    }

    public double realAt(double lambda, double mu) {
        double x1 = experimentor.normalizeLambda(lambda);
        double x2 = experimentor.normalizeMu(mu);

        return experimentor.y(x1, x2);
    }

    public double diffAt(double lambda, double mu) throws Exception {
        throw new Exception();
        // double pred = predictNormalized(lambda, mu);
        // double act = 0;

        // try {
        //     if (lambda == -1 && mu == -1) {
        //         act = planner.yAt(0);
        //     } else if (lambda == 1 && mu == -1) {
        //         act = planner.yAt(1);
        //     } else if (lambda == -1 && mu == 1) {
        //         act = planner.yAt(2);
        //     } else if (lambda == 1 && mu == 1) {
        //         act = planner.yAt(3);
        //     } else {
        //         throw new Exception();
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        
        // return Math.abs(pred - act);
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
