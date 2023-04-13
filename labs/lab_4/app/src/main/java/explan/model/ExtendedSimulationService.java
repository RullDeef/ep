package explan.model;

import explan.model.experiment.ExtendedSystemModel;
import explan.model.experiment.Generator;

public class ExtendedSimulationService {
    public ModelingResult startSimulationN(ExtendedSimulationParams params, int requests) {
        var modeler = new ExtendedSystemModel();

        modeler.setSourceGenerator1(Generator.exp(params.lambda1));
        modeler.setWorkerGenerator1(Generator.exp(params.mu1));
        modeler.setSourceGenerator2(Generator.exp(params.lambda2));
        modeler.setWorkerGenerator2(Generator.exp(params.mu2));

        var modelingResult = modeler.simulateRequests(requests);

        return new ModelingResult(
                Double.NaN, // params.lambda / params.mu,
                modelingResult.statRho(),
                modelingResult.avgWaitTime());
    }
}
