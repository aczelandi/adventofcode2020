package day17;

import common.BaseInputReader;
import day17.model.Grid;
import day17.model.ThreeDCoordinate;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.IntStream;

class ThreeDGridReader extends BaseInputReader<Grid> {

    private final int size;

    public ThreeDGridReader(int size) {
        this.size = size;
    }

    @Override
    protected Grid read(URI filePath) throws FileNotFoundException {
        var grid = Grid.init3D(size);
        var cells = new HashMap<>(grid.getCells());
        try (var scanner = new Scanner(new File(filePath))) {
            var x = 0;
            var z = 0;

            while (scanner.hasNext()) {
                var states = scanner.nextLine().chars().toArray();
                int finalX = x;
                IntStream.range(0, states.length)
                        .boxed()
                        .forEach(index -> {
                            var status = this.isActive((char) states[index]);
                            var coordinate = new ThreeDCoordinate(finalX, index, z);
                            cells.put(coordinate, status);
                        });
                x++;
            }
        }

        return new Grid(cells);
    }

    private boolean isActive(char state) {
        return state == '#';
    }
}
