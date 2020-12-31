package day17.model;

import java.util.ArrayList;
import java.util.List;

public record FourDCoordinate(int x, int y, int z, int w) implements Coordinate {

    public List<Coordinate> getNeighbours() {
        var neighbours = new ArrayList<Coordinate>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    for (int dw = -1; dw <= 1; dw++) {
                        if (dx == 0 && dy == 0 && dz == 0 && dw == 0) continue;
                        var coordinate = new FourDCoordinate(x + dx, y + dy, z + dz, w + dw);
                        neighbours.add(coordinate);
                    }
                }
            }
        }

        return neighbours;
    }
}
