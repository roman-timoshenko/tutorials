package name.roman.tutorial.search.supplier;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Supplies with ordered lists of items. Items are provided by a supplier,
 * size is randomly chosen within [minSize, maxSize).
 */
public class SortedListSupplier<T extends Comparable<? super T>> implements Supplier<List<T>> {

    private final Supplier<T> elementSupplier;
    private final Random random;
    private final int minSize;
    private final int maxSize;

    public SortedListSupplier(Supplier<T> elementSupplier, Random random, int minSize, int maxSize) {
        this.elementSupplier = elementSupplier;
        this.random = random;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public List<T> get() {
        return Stream.generate(elementSupplier)
                .limit(minSize + random.nextInt(maxSize - minSize))
                .sorted()
                .collect(Collectors.toList());
    }
}
