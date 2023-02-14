package explan;

import java.util.ArrayList;
import java.util.List;

import explan.model.Generator;
import explan.model.Point;
import explan.model.SimulationParams;
import explan.model.SystemModel;

public class SimulationService {

    public ModelingResult startSimulation(SimulationParams params) {
        var modeler = new SystemModel();

        modeler.setSimulationTime(params.tMax);
        modeler.setSourceGenerator(Generator.exp(params.lambda));
        modeler.setWorkerGenerator(Generator.exp(params.mu));

        var modelingResult = modeler.simulate();

        return new ModelingResult(
                params.lambda / params.mu,
                modelingResult.statRho());
    }

    public List<Point> avgWaitTimeOverRho(BuildChartParams params) {
        var points = new ArrayList<Point>();

        for (int i = 0; i < params.pointsCount; i++) {
            float x = params.minX + i * params.deltaX();

            float rho = x; // rho = lambda / mu
            float lambda = rho;
            float mu = 1.0f;

            float avgWaitTime = 0.0f;

            for (int j = 0; j < params.modelingsCount; j++) {
                var modeler = new SystemModel();
    
                modeler.setSimulationTime(params.modelingTime);
                modeler.setSourceGenerator(Generator.exp(lambda));
                modeler.setWorkerGenerator(Generator.exp(mu));
    
                var modelingResult = modeler.simulate();
                avgWaitTime += modelingResult.avgWaitTime();
            }

            points.add(new Point(x, avgWaitTime / params.modelingsCount));
        }

        return points;
    }
}