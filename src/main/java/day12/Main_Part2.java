package day12;

import common.BaseMain;
import common.TriFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main_Part2 extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main_Part2();
        var actions = program.readInput("day12/input.txt", program::readInput);
        System.out.println(actions);

        var initialWPosition = new Position(Direction.EAST, 10, 1);
        var initialShipPosition = new Position(null, 0, 0);
        var finalShipPosition = program.executeActions(actions, 0, initialWPosition, initialShipPosition);
        System.out.println(finalShipPosition);

        var manhattanDistance = Math.abs(finalShipPosition.horizontalCord) + Math.abs(finalShipPosition.verticalCord);
        System.out.println(manhattanDistance);
    }

    private List<Action> readInput(URI filePath) {
        var actions = new ArrayList<Action>();
        try (var scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                var line = scanner.nextLine();
                var directionChar = line.charAt(0);
                var unit = Integer.parseInt(line.substring(1));
                var direction = switch (directionChar) {
                    case 'N' -> Direction.NORTH;
                    case 'E' -> Direction.EAST;
                    case 'S' -> Direction.SOUTH;
                    case 'W' -> Direction.WEST;
                    case 'F' -> Direction.FORWARD;
                    case 'L' -> Direction.LEFT;
                    case 'R' -> Direction.RIGHT;
                    default -> throw new IllegalStateException("Unexpected direction: " + directionChar);
                };

                var action = new Action(direction, unit);
                actions.add(action);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return actions;
    }

    private Position executeActions(List<Action> actions, int currentIndex, Position weightPosition, Position shipPosition) {
        if (currentIndex == actions.size()) {
            return shipPosition;
        }

        var currentAction = actions.get(currentIndex);
        if (currentAction.direction == Direction.FORWARD) { // the ship moves
            var nextShipPosition = shipPosition.next(currentAction, weightPosition);
            return executeActions(actions, currentIndex + 1, weightPosition, nextShipPosition);
        } else { // the weigh moves
            var nextWeightPosition = weightPosition.next(currentAction, shipPosition);
            return executeActions(actions, currentIndex + 1, nextWeightPosition, shipPosition);
        }
    }

    private enum Direction {
        NORTH((action, position, relative) -> new Position(position.direction, position.horizontalCord, position.verticalCord + action.unit)),
        EAST((action, position, relative) -> new Position(position.direction, position.horizontalCord + action.unit, position.verticalCord)),
        SOUTH((action, position, relative) -> new Position(position.direction, position.horizontalCord, position.verticalCord - action.unit)),
        WEST((action, position, relative) -> new Position(position.direction, position.horizontalCord - action.unit, position.verticalCord)),
        FORWARD((action, position, relative) ->
                new Position(null,
                        relative.horizontalCord * action.unit + position.horizontalCord,
                        relative.verticalCord * action.unit + position.verticalCord)),
        LEFT((action, position, relative) -> {
            var unit = action.unit;
            var angle = Math.toRadians(unit);
            var newHorizontal = Math.round(Math.cos(angle)) * position.horizontalCord - Math.sin(angle) * position.verticalCord;
            var newVertical = Math.sin(angle) * position.horizontalCord + Math.round(Math.cos(angle)) * position.verticalCord;
            var newFacing = findNewDirection(position.direction, unit / 90, true);
            return new Position(newFacing, newHorizontal, newVertical);
        }),
        RIGHT((action, position, relative) -> {
            var unit = action.unit;
            var angle = Math.toRadians(-unit);
            var newHorizontal = Math.round(Math.cos(angle)) * position.horizontalCord - Math.sin(angle) * position.verticalCord;
            var newVertical = Math.sin(angle) * position.horizontalCord + Math.round(Math.cos(angle)) * position.verticalCord;
            var newFacing = findNewDirection(position.direction, unit / 90, false);
            return new Position(newFacing, newHorizontal, newVertical);
        });

        private final TriFunction<Action, Position, Position, Position> transition;

        Direction(TriFunction<Action, Position, Position, Position> transition) {
            this.transition = transition;
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

    private record Action(Direction direction, int unit) {
    }

    private record Position(Direction direction, double horizontalCord, double verticalCord) {

        Position next(Action action, Position relativeTo) {
            return action.direction.transition.apply(action, this, relativeTo);
        }
    }
}