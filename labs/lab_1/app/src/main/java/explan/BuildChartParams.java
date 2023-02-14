package explan;

public class BuildChartParams {
    float minX;
    float maxX;
    int pointsCount;

    float modelingTime;
    int modelingsCount;

    public float deltaX() {
        return (maxX - minX) / (pointsCount - 1);
    }
}
