package name.roman.tutorial.hanoi.solver;

/**
 * A simple recursive Hanoi solver.
 */
public class RecursiveHanoiSolver implements HanoiSolver {

    private int getThirdAxis(int fromAxis, int toAxis) {
        return (7 ^ (1 << fromAxis | 1 << toAxis)) >> 1;
    }

    @Override
    public final void solve(int size, int fromAxis, int toAxis, PieceMoveListener moveListener) {
        if (size > 0) {
            int thirdAxis = getThirdAxis(fromAxis, toAxis);
            solve(size - 1, fromAxis, thirdAxis, moveListener);
            moveListener.move(fromAxis, toAxis);
            solve(size - 1, thirdAxis, toAxis, moveListener);
        }
    }
}
