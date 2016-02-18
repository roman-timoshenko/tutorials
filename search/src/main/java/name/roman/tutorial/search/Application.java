package name.roman.tutorial.search;

import name.roman.tutorial.search.measure.Measurer;
import name.roman.tutorial.search.searcher.BinarySearcher;
import name.roman.tutorial.search.searcher.LinearSearcher;
import name.roman.tutorial.search.searcher.Searcher;
import name.roman.tutorial.search.supplier.RandomIntegerSupplier;
import name.roman.tutorial.search.supplier.SortedListSupplier;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Tests different search methods and prints timings.
 */
public class Application implements Runnable {

    private final Collection<Supplier<List<Integer>>> randomIntegerSortedListSuppliers;
    private final Collection<Searcher> searchers;
    private final int tries;

    public Application(Collection<Supplier<List<Integer>>> randomIntegerSortedListSuppliers,
                       Collection<Searcher> searchers,
                       int tries) {
        this.randomIntegerSortedListSuppliers = randomIntegerSortedListSuppliers;
        this.searchers = searchers;
        this.tries = tries;
    }

    @Override
    public void run() {
        final Measurer measurer = new Measurer();
        for (final Supplier<List<Integer>> randomIntegerSortedListSupplier : randomIntegerSortedListSuppliers) {
            for (final Searcher searcher : searchers) {
                final List<Integer> list = randomIntegerSortedListSupplier.get();
                long measure = 0;
                for (int i = 0; i < tries; ++i) {
                    measure += measurer.measure(() ->
                            searcher.getIndex(list, list.get(list.size() / 2 - 1)));
                }
                System.out.println(searcher.getClass() + ": " + measure + " ms per " + tries +
                        " tries on list of size " + list.size());
            }
        }
    }

    public static void main(String[] args) {
        final Random random = new Random();
        final Supplier<Integer> randomIntegerSupplier = new RandomIntegerSupplier(random, 0, 2048);
        final List<Searcher> searchers = Arrays.asList(new BinarySearcher(), new LinearSearcher());
        final Collection<Integer> listSizes = Arrays.asList(10000, 100000, 1000000, 10000000);
        final Collection<Supplier<List<Integer>>> randomIntegerListSuppliers = listSizes.parallelStream()
                .map(i -> new SortedListSupplier<>(randomIntegerSupplier, random, i, i + 1))
                .collect(Collectors.toList());
        final int tries = 25;

        final Runnable application = new Application(randomIntegerListSuppliers, searchers, tries);
        application.run();
    }
}