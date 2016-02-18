package name.roman.tutorial.search.measure;

/**
 * Measures time a {@link Runnable} runs.
 */
public class Measurer {

    /**
     * Runs a {@link java.lang.Runnable} and return time it took to accomplish.
     * @param runnable runnable to run and measure
     * @return time in millis taken
     */
    public long measure(Runnable runnable) {
        final long start = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - start;
    }
}
