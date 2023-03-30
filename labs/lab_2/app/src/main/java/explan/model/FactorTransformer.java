package explan.model;

public class FactorTransformer {
    private double minValue;
    private double maxValue;

    public FactorTransformer(double min, double max) {
        minValue = min;
        maxValue = max;
    }

    public double min() {
        return minValue;
    }

    public double max() {
        return maxValue;
    }

    public double normalize(double value) {
        return 2 * (value - minValue) / (maxValue - minValue) - 1;
    }

    public double denormalize(double value) {
        return (minValue + maxValue) / 2 + value * (maxValue - minValue) / 2;
    }

    public double I() {
        return maxValue - minValue;
    }

    public double I0() {
        return (minValue + maxValue) / 2;
    }

    public boolean isInverted() {
        return minValue > maxValue;
    }
}
