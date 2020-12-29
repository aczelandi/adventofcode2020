package day3;

import common.Solution;
import day3.model.Forest;

class SolutionImpl extends Solution<Forest, Forest, Long, Long> {

    public SolutionImpl(Forest context) {
        super(context, context);
    }

    @Override
    public Long solvePart1() {
        return this.computeTreesOnPath(this.context1, 3, 1);
    }

    @Override
    public Long solvePart2() {
        var treesOnPath1 = this.computeTreesOnPath(this.context2, 3, 1);
        var treesOnPath2 = this.computeTreesOnPath(this.context2, 1, 1);
        var treesOnPath3 = this.computeTreesOnPath(this.context2, 5, 1);
        var treesOnPath4 = this.computeTreesOnPath(this.context2, 7, 1);
        var treesOnPath5 = this.computeTreesOnPath(this.context2, 1, 2);

        return treesOnPath1 * treesOnPath2 * treesOnPath3 * treesOnPath4 * treesOnPath5;
    }

    private long computeTreesOnPath(Forest forest, int rightMoves, int downMoves) {
        var elements = forest.elements();
        var rowLength = forest.rowLength();
        var colLength = forest.colLength();
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
