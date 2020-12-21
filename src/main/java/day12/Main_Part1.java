package day12;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Main_Part1 extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main_Part1();
        var actions = program.readInput("day12/input.txt", program::readInput);
        System.out.println(actions);

        var initialPosition = new Position(Direction.EAST, 0, 0);
        var position = program.executeActions(actions, 0, initialPosition);
        System.out.println(position);

        var manhattanDistance = Math.abs(position.horizontalCord) + Math.abs(position.verticalCord);
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

    private Position executeActions(List<Action> actions, int currentIndex, Position position) {
        if (currentIndex == actions.size()) {
            return position;
        }

        var currentAction = actions.get(currentIndex);
        var nextPosition = position.next(currentAction);
        return executeActions(actions, currentIndex + 1, nextPosition);
    }

    private enum Direction {
        NORTH((action, position) -> new Position(position.direction, position.horizontalCord, position.verticalCord + action.unit)),
        EAST((action, position) -> new Position(position.direction, position.horizontalCord + action.unit, position.verticalCord)),
        SOUTH((action, position) -> new Position(position.direction, position.horizontalCord, position.verticalCord - action.unit)),
        WEST((action, position) -> new Position(position.direction, position.horizontalCord - action.unit, position.verticalCord)),
        FORWARD(((action, position) -> position.direction.transition.apply(action, position))),
        LEFT((action, position) -> {
            var turns = action.unit / 90;
            var newFacing = findNewDirection(position.direction, turns, true);
            return new Position(newFacing, position.horizontalCord, position.verticalCord);
        }),
        RIGHT((action, position) -> {
            var turns = action.unit / 90;
            var newFacing = findNewDirection(position.direction, turns, false);
            return new Position(newFacing, position.horizontalCord, position.verticalCord);
        });

        private final BiFunction<Action, Position, Position> transition;

        Direction(BiFunction<Action, Position, Position> transition) {
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

    private record Position(Direction direction, int horizontalCord, int verticalCord) {

        Position next(Action action) {
            return action.direction.transition.apply(action, this);
        }
    }
}