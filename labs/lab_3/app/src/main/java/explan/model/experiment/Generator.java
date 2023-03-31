package explan.model.experiment;

import java.util.Random;
import java.util.function.Function;

public class Generator {
    private Random random;
    private Function<Double, Double> func;

    private Generator(Random random, Function<Double, Double> func) {
        this.random = random;
        this.func = func;
    }

    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    public double genNext() {
        var result = func.apply(random.nextDouble());
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            assert false;
        }
        return result;
    }

    /**
     * Returns generator with probability function:
     * 
     * F(t) = 1 - exp(-\lambda t), t \ge 0
     *        0, else
     * 
     * @return Exponential random generator
     */
    public static Generator exp(double lambda) {
        if (lambda <= 0) {
            throw new Error(String.format("lambda must be non-negative. Given: %lf", lambda));
        }

        return new Generator(new Random(), (Double r) -> {
            return -Math.log(0.0001 + r) / lambda;
        });
    }
}