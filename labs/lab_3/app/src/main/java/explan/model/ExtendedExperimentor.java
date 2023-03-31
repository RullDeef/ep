package explan.model;

import java.util.HashMap;
import java.util.Map;

import explan.model.plan.FactorTransformer;

public class ExtendedExperimentor {
    private ExtendedSimulationService simulationService;
    private int totalIterations = 100;
    private int totalRequests = 1000;

    private Map<String, FactorTransformer> transformers = new HashMap<>();

    public ExtendedExperimentor(
            ExtendedSimulationService service,
            FactorTransformer lambda1,
            FactorTransformer mu1,
            FactorTransformer lambda2,
            FactorTransformer mu2) {
        simulationService = service;
        transformers.put("x1", lambda1);
        transformers.put("x2", mu1);
        transformers.put("x3", lambda2);
        transformers.put("x4", mu2);
    }

    public void setTransformer(String var, FactorTransformer transformer) {
        transformers.put(var, transformer);
    }

    public FactorTransformer getTransformer(String var) {
        return transformers.get(var);
    }

    public double y(double x1, double x2, double x3, double x4) {
        return yDenorm(
            denormalize("x1", x1),
            denormalize("x2", x2),
            denormalize("x3", x3),
            denormalize("x4", x4)
        );
    }

    public double yDenorm(double lambda1, double mu1, double lambda2, double mu2) {
        var params = new ExtendedSimulationParams(lambda1, mu1, lambda2, mu2);

        double result = 0.0;
        for (int i = 0; i < totalIterations; i++) {
            var simulationResult = simulationService.startSimulationN(params, totalRequests);
            result += simulationResult.avgWaitTime;
        }
        return result / totalIterations;
    }

    public double normalize(String var, double factor) {
        return getTransformer(var).normalize(factor);
    }

    public double denormalize(String var, double factor) {
        return getTransformer(var).denormalize(factor);
    }

    public double I(String var) {
        return getTransformer(var).I();
    }

    public double I0(String var) {
        return getTransformer(var).I0();
    }
}
