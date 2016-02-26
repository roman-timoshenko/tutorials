package name.roman.tutorial.sort;

import name.roman.tutorial.sort.algorithm.BubbleSortingAlgorithm;
import name.roman.tutorial.sort.algorithm.SortingAlgorithm;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class Application {
    public static void main(String[] args) {
        SortingAlgorithm sortingAlgorithm = new BubbleSortingAlgorithm();
        final List<Integer> list = Arrays.asList(5, 7, 2, 8, 0);
        sortingAlgorithm.sort(list);
        System.out.println(list);
    }
}
