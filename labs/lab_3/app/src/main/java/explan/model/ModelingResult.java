package explan.model;

public class ModelingResult {
    public double rhoTheoretical;
    public double rhoPractical;
    public double avgWaitTime;

    public ModelingResult(double rhoTheoritical, double rhoPractical, double avgWaitTime) {
        this.rhoTheoretical = rhoTheoritical;
        this.rhoPractical = rhoPractical;
        this.avgWaitTime = avgWaitTime;
    }
}
