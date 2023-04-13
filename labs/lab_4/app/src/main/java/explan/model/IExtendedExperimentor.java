package explan.model;

import explan.model.plan.FactorTransformer;

// unused
public interface IExtendedExperimentor {
    void setTransformer(String var, FactorTransformer transformer);
    FactorTransformer getTransformer(String var);

    double y(double x1, double x2, double x3, double x4);

    double yDenorm(
        double lambda1, double mu1,
        double lambda2, double mu2
    );

    public double normalize(String var, double factor);
    public double denormalize(String var, double factor);

    double I(String var);
    double I0(String var);
}
