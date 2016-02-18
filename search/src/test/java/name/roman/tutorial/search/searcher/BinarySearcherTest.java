package name.roman.tutorial.search.searcher;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class BinarySearcherTest {

    @DataProvider(name = "searchersProvider")
    public Object[][] getData() {
        return new Object[][] {
                {new BinarySearcher()},
                {new LinearSearcher()}
        };
    }

    @Test(dataProvider = "searchersProvider")
    public void testGetIndex(Searcher searcher) throws Exception {
        final List<Integer> list = Arrays.asList(-4, -2, 0, 2, 4);
        assertTrue(searcher.getIndex(list, -5) < 0);
        assertEquals(searcher.getIndex(list, -4), 0);
        assertTrue(searcher.getIndex(list, -3) < 0);
        assertEquals(searcher.getIndex(list, -2), 1);
        assertTrue(searcher.getIndex(list, -1) < 0);
        assertEquals(searcher.getIndex(list, 0), 2);
        assertTrue(searcher.getIndex(list, 1) < 0);
        assertEquals(searcher.getIndex(list, 2), 3);
        assertTrue(searcher.getIndex(list, 3) < 0);
        assertEquals(searcher.getIndex(list, 4), 4);
        assertTrue(searcher.getIndex(list, 5) < 0);
    }
}