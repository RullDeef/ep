package explan.model;

import org.ejml.simple.SimpleMatrix;

public interface IExtendedExperimentService {
    void setExperimentSpace(
        double minLambda1, double maxLambda1,
        double minMu1, double maxMu1,
        double minLambda2, double maxLambda2,
        double minMu2, double maxMu2
    ) throws Exception;

    void recalcCoefficients();

    SimpleMatrix getY();
    SimpleMatrix getYPredicted();
    SimpleMatrix getYPredictedError();

    SimpleMatrix getB_Norm();
    SimpleMatrix getB_Denorm();

    double realAt(
        double lambda1, double mu1,
        double lambda2, double mu2
    );

    double realAtNorm(
        double x1, double x2,
        double x3, double x4
    );

    double calcY(
        double lambda1, double mu1,
        double lambda2, double mu2
    );

    double calcYNorm(
        double x1, double x2,
        double x3, double x4
    );
}
