package name.roman.tutorial.search.searcher;

import java.util.Comparator;
import java.util.List;

/**
 * Search for a value in a list using linear sequential search.
 * <p/>
 * An optimization is done, so search is stopped when element is found.
 */
public class LinearSearcher implements Searcher {

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> int getIndex(List<? extends T> list, T value, Comparator<? super T> comparator) {
        int i = 0;
        for (final T element : list) {
            if (comparator.compare(element, value) == 0) {
                return i;
            }
            ++i;
        }
        return -1;
    }
}
