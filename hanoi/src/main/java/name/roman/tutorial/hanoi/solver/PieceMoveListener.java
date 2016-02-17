package name.roman.tutorial.hanoi.solver;

/**
 * Listener that is called each time a disk is moved from one axis to another one.
 */
public interface PieceMoveListener {

    /**
     * Function that is called each time a disk is moved from 'fromAxis' to 'toAxis'
     *
     * @param fromAxis axis, top disk is being moved from
     * @param toAxis axis, top disk is being moved to
     */
    void move(int fromAxis, int toAxis);

}
