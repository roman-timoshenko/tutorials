package name.roman.tutorial.sort.algorithm;

import java.util.List;

/**
 *
 */
public class InsertionSortingAlgorithm implements SortingAlgorithm {

    @Override
    public <T extends Comparable<? super T>> void sort(List<T> list) {
        for (int i = 0; i < list.size(); ++i) {
            int j = i;
            final T element = list.get(i);
            while (j > 0 && list.get(j - 1).compareTo(element) < 0) {
                list.set(j, list.get(j - 1));
                j--;
            }
            list.set(j, element);
        }
    }
}
