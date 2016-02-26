package name.roman.tutorial.sort.algorithm;

import java.util.List;

/**
 *
 */
public interface SortingAlgorithm {

    public <T extends Comparable<? super T>> void sort(List<T> list);

}
