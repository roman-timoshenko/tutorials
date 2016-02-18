package name.roman.tutorial.search.searcher;

import java.util.Comparator;
import java.util.List;

/**
 * Searches a value in a list.
 */
public interface Searcher {

    /**
     * Searches for an element in a list using a custom comparator.
     *
     * @param list a {@link java.util.List} to search in
     * @param value a value to search for
     * @param comparator a custom comparator for elements comparision
     * @param <T> value type
     * @return true if element exists in given list, false otherwise
     */
    default <T> boolean exists(List<? extends T> list, T value, Comparator<? super T> comparator) {
        return getIndex(list, value, comparator) >= 0;
    }

    /**
     * Searches for an element in a list of {@link java.lang.Comparable} items.
     *
     * @param list a {@link java.util.List} to search in
     * @param value a value to search for
     * @param <T> value type, extends {@link java.lang.Comparable}
     * @return true if element exists in given list, false otherwise
     */
    default <T extends Comparable<? super T>> boolean exists(List<? extends T> list, T value) {
        return exists(list, value, Comparable::compareTo);
    }

    /**
     * Searches a value in a list and returns index of found element, or a negative value if value is not found.
     *
     * @param list a {@link java.util.List} to search in
     * @param value a value to search for
     * @param comparator a custom comparator for elements comparision
     * @param <T> value type
     * @return a value >= 0, if an element is found or a negative value otherwise
     */
    <T> int getIndex(List<? extends T> list, T value, Comparator<? super T> comparator);

    /**
     * Searches a value in a list and returns index of found element, or a negative value if value is not found.
     *
     * @param list a {@link java.util.List} to search in
     * @param value a value to search for
     * @param <T> value type
     * @return a value >= 0, if an element is found or a negative value otherwise
     */
    default <T extends Comparable<? super T>> int getIndex(List<? extends T> list, T value) {
        return getIndex(list, value, Comparable::compareTo);
    }
}
