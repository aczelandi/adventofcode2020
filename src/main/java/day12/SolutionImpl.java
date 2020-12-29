package day12;

import common.Solution;
import day12.model.Action;
import day12.model.Direction;
import day12.model.Position;

import java.util.List;

class SolutionImpl extends Solution<List<Action>, List<Action>, Double, Double> {

    public SolutionImpl(List<Action> context1, List<Action> context2) {
        super(context1, context2);
    }

    @Override
    public Double solvePart1() {
        var initialPosition = new Position(Direction.EAST, 0, 0);
        var position = this.executeActionsPart1(this.context1, 0, initialPosition);
        return MathUtils.manhattanDistance(position.horizontalCord(), position.verticalCord());
    }

    @Override
    public Double solvePart2() {
        var initialWPosition = new Position(Direction.EAST, 10, 1);
        var initialShipPosition = new Position(null, 0, 0);
        var finalShipPosition = this.executeActionsPart2(this.context2, 0, initialWPosition, initialShipPosition);
        return MathUtils.manhattanDistance(finalShipPosition.horizontalCord(), finalShipPosition.verticalCord());
    }

    private Position executeActionsPart1(List<Action> actions, int currentIndex, Position position) {
        if (currentIndex == actions.size()) {
            return position;
        }

        var currentAction = actions.get(currentIndex);
        var nextPosition = position.next(currentAction);
        return executeActionsPart1(actions, currentIndex + 1, nextPosition);
    }

    private Position executeActionsPart2(List<Action> actions, int currentIndex, Position weightPosition, Position shipPosition) {
        if (currentIndex == actions.size()) {
            return shipPosition;
        }

        var currentAction = actions.get(currentIndex);
        if (currentAction.direction() == Direction.FORWARD) { // the ship moves
            var nextShipPosition = shipPosition.next(currentAction, weightPosition);
            return executeActionsPart2(actions, currentIndex + 1, weightPosition, nextShipPosition);
        } else { // the weigh moves
            var nextWeightPosition = weightPosition.next(currentAction, shipPosition);
            return executeActionsPart2(actions, currentIndex + 1, nextWeightPosition, shipPosition);
        }
    }
}
