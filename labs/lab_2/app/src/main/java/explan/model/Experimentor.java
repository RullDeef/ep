package explan.model;

/**
 * Класс предоставляющий простой интерфейс для работы с
 * экспериментом как с функцией двух переменных.
 */
public class Experimentor {
    private SimulationService simulationService;
    private double tMax = 1000.0; // Double.NaN;
    private double totalIterations = 100;

    private FactorTransformer lambdaTransformer;
    private FactorTransformer muTransformer;

    public Experimentor(
            SimulationService service,
            FactorTransformer lambda,
            FactorTransformer mu) {
        simulationService = service;
        lambdaTransformer = lambda;
        muTransformer = mu;
    }

    public void setTMax(double tMax) {
        this.tMax = tMax;
    }

    public void setLambdaTransformer(FactorTransformer transformer) {
        lambdaTransformer = transformer;
    }

    public FactorTransformer getLambdaTransform() {
        return lambdaTransformer;
    }

    public void setMuTransformer(FactorTransformer transformer) {
        muTransformer = transformer;
    }

    public FactorTransformer getMuTransformer() {
        return muTransformer;
    }

    public double y(double x1, double x2) {
        var params = new SimulationParams(
            (float) denormalizeLambda(x1),
            (float) denormalizeMu(x2),
            (float) tMax
        );
        
        double result = 0.0;
        for (int i = 0; i < totalIterations; i++) {
            var simulationResult = simulationService.startSimulationN(params, 1000);
            result += simulationResult.avgWaitTime;
        }
        return result / totalIterations;
    }

    public double normalizeLambda(double factor) {
        return lambdaTransformer.normalize(factor);
    }

    public double denormalizeLambda(double factor) {
        return lambdaTransformer.denormalize(factor);
    }

    public double normalizeMu(double factor) {
        return muTransformer.normalize(factor);
    }

    public double denormalizeMu(double factor) {
        return muTransformer.denormalize(factor);
    }
}
