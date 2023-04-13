package explan.model.experiment;

import org.ejml.simple.SimpleMatrix;

public interface IPlanMatrix {

    double calcY(SimpleMatrix X);

    SimpleMatrix getB();

    SimpleMatrix getY();

    SimpleMatrix getYPredicted();

    SimpleMatrix getYPredictedError();
}
