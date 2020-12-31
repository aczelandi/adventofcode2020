package day17.model;

import java.util.ArrayList;
import java.util.List;

public record ThreeDCoordinate(int x, int y, int z) implements Coordinate {

    public List<Coordinate> getNeighbours() {
        var neighbours = new ArrayList<Coordinate>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;
                    var coordinate = new ThreeDCoordinate(x + dx, y + dy, z + dz);
                    neighbours.add(coordinate);
                }
            }
        }
        return neighbours;
    }
}
