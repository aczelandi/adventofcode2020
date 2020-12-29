package day12;

import common.BaseInputReader;
import day12.model.Action;
import day12.model.Direction;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class InputReader extends BaseInputReader<List<Action>> {

    @Override
    protected List<Action> read(URI filePath) throws FileNotFoundException {
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
        }

        return actions;
    }
}
