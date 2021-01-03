package day17;

import common.Solution;
import day17.model.Coordinate;
import day17.model.Grid;

import java.util.HashMap;

class SolutionImpl extends Solution<Grid, Grid, Long, Long> {

    public SolutionImpl(Grid context1, Grid context2) {
        super(context1, context2);
    }

    @Override
    public Long solvePart1() {
        var cycleCount = 6;
        var grid = this.context1;
        grid = executeCyclesOnGrid(cycleCount, grid);

        return grid.getCells().values().stream().filter(c -> c).count();
    }

    @Override
    public Long solvePart2() {
        var cycleCount = 6;
        var grid = this.context2;
        grid = executeCyclesOnGrid(cycleCount, grid);

        return grid.getCells().values().stream().filter(c -> c).count();
    }

    private Grid executeCyclesOnGrid(int cycleCount, Grid grid) {
        for (int i = 0; i < cycleCount; i++) {
            var newCells = new HashMap<Coordinate, Boolean>();
            for (Coordinate coordinate : grid.getExpandedCoordinates()) {
                var neighbours = coordinate.getNeighbours();
                var activeNeighbourCount = neighbours
                        .stream()
                        .filter(grid::isCoordinateActive)
                        .count();

                if (grid.isCoordinateActive(coordinate)) {
                    newCells.put(coordinate, activeNeighbourCount == 2L || activeNeighbourCount == 3L);
                } else {
                    newCells.put(coordinate, activeNeighbourCount == 3L);
                }
            }

            grid = new Grid(newCells);
        }
        return grid;
    }
}
