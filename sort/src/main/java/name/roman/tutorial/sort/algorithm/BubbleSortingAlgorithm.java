package name.roman.tutorial.sort.algorithm;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class BubbleSortingAlgorithm implements SortingAlgorithm {

    @Override
    public <T extends Comparable<? super T>> void sort(List<T> list) {
        for (int i = 0; i < list.size() - 1; ++i) {
            for (int j = i + 1; j < list.size(); ++j) {
                if (list.get(i).compareTo(list.get(j)) < 0) {
                    Collections.swap(list, i, j);
                }
            }
        }
    }
}
