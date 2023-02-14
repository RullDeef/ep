package explan.model;

import java.util.Random;
import java.util.function.Function;

public class Generator {
    private Random random;
    private Function<Float, Float> func;

    private Generator(Random random, Function<Float, Float> func) {
        this.random = random;
        this.func = func;
    }

    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    public float genNext() {
        return func.apply(random.nextFloat());
    }

    /**
     * Returns generator with probability function:
     * 
     * F(t) = 1 - exp(-\lambda t), t \ge 0
     *        0, else
     * 
     * @return Exponential random generator
     */
    public static Generator exp(float lambda) {
        if (lambda <= 0) {
            throw new Error(String.format("lambda must be non-negative. Given: %lf", lambda));
        }

        return new Generator(new Random(), (Float r) -> {
            return -(float)Math.log((double)r) / lambda;
        });
    }
}