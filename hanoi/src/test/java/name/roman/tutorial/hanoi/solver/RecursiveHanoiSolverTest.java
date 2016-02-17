package name.roman.tutorial.hanoi.solver;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.*;

public class RecursiveHanoiSolverTest {

    private static class VerifyingPieceMoveListener implements PieceMoveListener {

        private final List<Deque<Integer>> axises;

        public VerifyingPieceMoveListener(int size, int firstAxis) {
            this.axises = new ArrayList<>();
            init(size, 3, firstAxis);
        }

        private void init(int size, int numberOfAxises, int firstAxis) {
            for (int i = 0; i < numberOfAxises; ++i) {
                axises.add(new LinkedList<>());
            }
            for (int i = 0; i < size; ++i) {
                axises.get(firstAxis).addFirst(i + 1);
            }
        }

        public int getAxisSize(int axisId) {
            return axises.get(axisId).size();
        }

        @Override
        public void move(int fromAxis, int toAxis) {
            assertTrue(axises.get(fromAxis).size() > 0, "error while moving from " + fromAxis + " to " + toAxis +
                    ": " + "empty stack");
            final int diskToMove = axises.get(fromAxis).pollFirst();
            if (axises.get(toAxis).size() > 0) {
                final int diskToMoveOn = axises.get(toAxis).peekFirst();
                assertTrue(diskToMove > diskToMoveOn, "error while moving from " + fromAxis + " to " + toAxis +
                        ": " + "disk to move is smaller than disk to move onto");
            }
            axises.get(toAxis).addFirst(diskToMove);
        }
    }

    private final HanoiSolver solver = new RecursiveHanoiSolver();

    @Test
    public void testSolveSuccessful() throws Exception {
        final int size = 12;
        final int sourceAxis = 0;
        final int destinationAxis = 1;
        final int thirdAxis = 2;
        final VerifyingPieceMoveListener moveListener = new VerifyingPieceMoveListener(size, sourceAxis);

        solver.solve(size, sourceAxis, destinationAxis, moveListener);

        assertEquals(moveListener.getAxisSize(sourceAxis), 0,
                "source axis should be empty after problem is solved");
        assertEquals(moveListener.getAxisSize(destinationAxis), size,
                "destination axis should contain all disks after problem is solved");
        assertEquals(moveListener.getAxisSize(thirdAxis), 0,
                "third axis should be empty after problem is solved");
    }
}
