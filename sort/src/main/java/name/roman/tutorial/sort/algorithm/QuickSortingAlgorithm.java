package name.roman.tutorial.sort.algorithm;

import java.util.List;

/**
 *
 */
public class QuickSortingAlgorithm implements SortingAlgorithm {

    private <T extends Comparable<? super T>>  int partition(List<T> list, int left, int right) {
        int i = left;
        int j = right;
        final T pivot = list.get((left + right) / 2);
        while (i <= j) {
            while (list.get(i).compareTo(pivot) < 0) {
                i++;
            }
            while (list.get(j).compareTo(pivot) > 0) {
                j--;
            }
            if (i <= j) {
                final T tmp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, tmp);
                i++;
                j--;
            }
        }
        return i;
    }

    private <T extends Comparable<? super T>> void quickSort(List<T> list, int left, int right) {
        final int index = partition(list, left, right);
        if (left < index - 1) {
            quickSort(list, left, index - 1);
        }
        if (index < right) {
            quickSort(list, index, right);
        }
    }

    @Override
    public <T extends Comparable<? super T>> void sort(List<T> list) {
        quickSort(list, 0, list.size() - 1);
    }
}
