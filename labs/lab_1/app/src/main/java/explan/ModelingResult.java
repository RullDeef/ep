package explan;

public class ModelingResult {
    public float rhoTheoretical;
    public float rhoPractical;
    public float avgWaitTime;

    public ModelingResult(float rhoTheoritical, float rhoPractical, float avgWaitTime) {
        this.rhoTheoretical = rhoTheoritical;
        this.rhoPractical = rhoPractical;
        this.avgWaitTime = avgWaitTime;
    }
}
