package explan.model;

public class BuildChartParams {
    public float minX;
    public float maxX;
    public int pointsCount;

    public float modelingTime;
    public int modelingsCount;

    public float deltaX() {
        return (maxX - minX) / (pointsCount - 1);
    }
}
