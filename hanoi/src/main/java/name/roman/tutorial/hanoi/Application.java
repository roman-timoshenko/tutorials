package name.roman.tutorial.hanoi;

import name.roman.tutorial.hanoi.solver.HanoiSolver;
import name.roman.tutorial.hanoi.solver.PieceMoveListener;
import name.roman.tutorial.hanoi.solver.RecursiveHanoiSolver;

/**
 * A simple application that solves 'Tower of Hanoi" problem and prints out each move step.
 */
public class Application implements Runnable {

    private final HanoiSolver solver;
    private final int size;
    private final PieceMoveListener moveListener;

    public Application(HanoiSolver solver, int size, PieceMoveListener moveListener) {
        this.solver = solver;
        this.size = size;
        this.moveListener = moveListener;
    }

    @Override
    public void run() {
        solver.solve(size, 0, 1, moveListener);
    }

    public static void main(String[] args) {
        final HanoiSolver solver = new RecursiveHanoiSolver();
        final PieceMoveListener moveListener = (fromAxis, toAxis) ->
                System.out.println("Move from " + (fromAxis + 1) + " to " + (toAxis + 1));
        new Application(solver, 3, moveListener).run();
    }

}
