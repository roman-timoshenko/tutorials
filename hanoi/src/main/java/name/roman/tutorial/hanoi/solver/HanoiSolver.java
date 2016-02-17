package name.roman.tutorial.hanoi.solver;

/**
 * An interface for three-axis "Towers of Hanoi" problem solver.
 */
public interface HanoiSolver {

    /**
     * Solves "Tower of Hanoi" problem, calling provided {@link name.roman.tutorial.hanoi.solver.PieceMoveListener}
     * on each move.
     *
     * @param size         number of discs
     * @param fromAxis     source axis to move all disks from
     * @param toAxis       target axis to move all disks to
     * @param moveListener an instance of {@link name.roman.tutorial.hanoi.solver.PieceMoveListener} that is called
     *                     each time a move is made
     */
    void solve(int size, int fromAxis, int toAxis, PieceMoveListener moveListener);

}
