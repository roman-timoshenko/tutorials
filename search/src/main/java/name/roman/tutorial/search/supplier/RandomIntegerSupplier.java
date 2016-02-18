package name.roman.tutorial.search.supplier;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Supplies with random integers in given range [min, max).
 */
public class RandomIntegerSupplier implements Supplier<Integer> {

    private final Random random;
    private final int min;
    private final int max;

    public RandomIntegerSupplier(Random random, int min, int max) {
        this.random = random;
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer get() {
        return min + random.nextInt(max - min);
    }
}
