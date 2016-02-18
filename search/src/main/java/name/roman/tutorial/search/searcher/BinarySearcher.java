package name.roman.tutorial.search.searcher;

import java.util.Comparator;
import java.util.List;

/**
 * Searches for values in a sorted lists using non-recursive binary search.
 */
public class BinarySearcher implements Searcher {

    /**
     * Returns index of first element in list which is not less than given value.
     * <p/>
     * List items should be ordered or at least partitioned using same comparator.
     *
     * @param list an ordered {@link java.util.List} of elements
     * @param value a value to search
     * @param comparator a custom {@link java.util.Comparator} to compare values in list
     * @param <T> value type, should extend {@link java.lang.Comparable}
     * @return index of first element which is not less than given value
     */
    public static <T> int lowerBound(List<? extends T> list, T value, Comparator<? super T> comparator) {
        int count = list.size();
        int result = 0;
        while (count > 0) {
            final int step = count / 2;
            if (comparator.compare(list.get(result + step), value) < 0) {
                result += step + 1;
                count -= step + 1;
            } else {
                count = step;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> int getIndex(List<? extends T> list, T value, Comparator<? super T> comparator) {
        final int bound = lowerBound(list, value, comparator);
        if (bound < list.size() && comparator.compare(list.get(bound), value) == 0) {
            return bound;
        }
        return -1 - bound;
    }
}
