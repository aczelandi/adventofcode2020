package day3;

import common.Solution;
import day3.model.Forest;

public class SolutionImpl extends Solution<Forest, Long, Long> {

    public SolutionImpl(Forest input) {
        super(input);
    }

    @Override
    public Long solvePart1() {
        return this.computeTreesOnPath(3, 1);
    }

    @Override
    public Long solvePart2() {
        var treesOnPath1 = this.computeTreesOnPath(3, 1);
        var treesOnPath2 = this.computeTreesOnPath(1, 1);
        var treesOnPath3 = this.computeTreesOnPath(5, 1);
        var treesOnPath4 = this.computeTreesOnPath(7, 1);
        var treesOnPath5 = this.computeTreesOnPath(1, 2);

        return treesOnPath1 * treesOnPath2 * treesOnPath3 * treesOnPath4 * treesOnPath5;
    }

    private long computeTreesOnPath(int rightMoves, int downMoves) {
        var elements = this.input.elements();
        var rowLength = this.input.rowLength();
        var colLength = this.input.colLength();
        var currentColIndex = 0;
        var trees = 0;

        for (int i = 0; i < rowLength; i += downMoves) {
            if (elements[i][currentColIndex] == '#') {
                trees++;
            }

            currentColIndex = (currentColIndex + rightMoves) % colLength;
        }

        return trees;
    }
}
