package day12.model;

import common.TriFunction;

import java.util.function.BiFunction;

public enum Direction {
    NORTH(
            (action, position) -> new Position(position.direction(), position.horizontalCord(), position.verticalCord() + action.unit()),
            (action, position, relative) -> new Position(position.direction(), position.horizontalCord(), position.verticalCord() + action.unit())),
    EAST(
            (action, position) -> new Position(position.direction(), position.horizontalCord() + action.unit(), position.verticalCord()),
            (action, position, relative) -> new Position(position.direction(), position.horizontalCord() + action.unit(), position.verticalCord())),
    SOUTH(
            (action, position) -> new Position(position.direction(), position.horizontalCord(), position.verticalCord() - action.unit()),
            (action, position, relative) -> new Position(position.direction(), position.horizontalCord(), position.verticalCord() - action.unit())),
    WEST(
            (action, position) -> new Position(position.direction(), position.horizontalCord() - action.unit(), position.verticalCord()),
            (action, position, relative) -> new Position(position.direction(), position.horizontalCord() - action.unit(), position.verticalCord())),
    FORWARD(
            ((action, position) -> position.direction().applyTransitionV1(action, position)),
            (action, position, relative) -> new Position(null,
                    relative.horizontalCord() * action.unit() + position.horizontalCord(),
                    relative.verticalCord() * action.unit() + position.verticalCord())),

    LEFT(
            (action, position) -> {
                var turns = action.unit() / 90;
                var newFacing = findNewDirection(position.direction(), turns, true);
                return new Position(newFacing, position.horizontalCord(), position.verticalCord());
            },
            (action, position, relative) -> {
                var unit = action.unit();
                var angle = Math.toRadians(unit);
                var newHorizontal = Math.round(Math.cos(angle)) * position.horizontalCord() - Math.sin(angle) * position.verticalCord();
                var newVertical = Math.sin(angle) * position.horizontalCord() + Math.round(Math.cos(angle)) * position.verticalCord();
                var newFacing = findNewDirection(position.direction(), unit / 90, true);
                return new Position(newFacing, newHorizontal, newVertical);
            }),
    RIGHT(
            (action, position) -> {
                var turns = action.unit() / 90;
                var newFacing = findNewDirection(position.direction(), turns, false);
                return new Position(newFacing, position.horizontalCord(), position.verticalCord());
            },
            ((action, position, relative) -> {
                var unit = action.unit();
                var angle = Math.toRadians(-unit);
                var newHorizontal = Math.round(Math.cos(angle)) * position.horizontalCord() - Math.sin(angle) * position.verticalCord();
                var newVertical = Math.sin(angle) * position.horizontalCord() + Math.round(Math.cos(angle)) * position.verticalCord();
                var newFacing = findNewDirection(position.direction(), unit / 90, false);
                return new Position(newFacing, newHorizontal, newVertical);
            }));

    private final BiFunction<Action, Position, Position> transitionV1;

    private final TriFunction<Action, Position, Position, Position> transitionV2;

    Direction(BiFunction<Action, Position, Position> transition, TriFunction<Action, Position, Position, Position> transitionV2) {
        this.transitionV1 = transition;
        this.transitionV2 = transitionV2;
    }

    public Position applyTransitionV1(Action action, Position position) {
        return this.transitionV1.apply(action, position);
    }

    public Position applyTransitionV2(Action action, Position position, Position relativeTo) {
        return this.transitionV2.apply(action, position, relativeTo);
    }

    private static Direction findNewDirection(Direction currentDirection, int turns, boolean left) {
        if (turns == 0) {
            return currentDirection;
        }

        var nextDirection = switch (currentDirection) {
            case NORTH -> left ? Direction.WEST : Direction.EAST;
            case SOUTH -> left ? Direction.EAST : Direction.WEST;
            case EAST -> left ? Direction.NORTH : Direction.SOUTH;
            case WEST -> left ? Direction.SOUTH : Direction.NORTH;
            default -> throw new IllegalStateException("Unexpected direction: " + currentDirection);
        };

        return findNewDirection(nextDirection, turns - 1, left);
    }
}
