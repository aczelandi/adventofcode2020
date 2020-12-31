package day17.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grid {

    private final Map<Coordinate, Boolean> cells;

    public Grid(Map<Coordinate, Boolean> cells) {
        this.cells = cells;
    }

    public Set<Coordinate> getExpandedCoordinates() {
        return this.cells.keySet().stream()
                .flatMap(cell -> Stream.concat(
                        Stream.of(cell),
                        cell.getNeighbours().stream()))
                .collect(Collectors.toSet());
    }

    public Map<Coordinate, Boolean> getCells() {
        return cells;
    }

    public boolean isCoordinateActive(Coordinate coordinate) {
        return this.cells.getOrDefault(coordinate, false);
    }

    public static Grid init3D(int size) {
        var cells = new HashMap<Coordinate, Boolean>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    var coordinate = new ThreeDCoordinate(x, y, z);
                    cells.put(coordinate, false);
                }
            }
        }

        return new Grid(cells);
    }

    public static Grid init4D(int size) {
        var cells = new HashMap<Coordinate, Boolean>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    for (int w = 0; w < size; w++) {
                        var coordinate = new FourDCoordinate(x, y, z, w);
                        cells.put(coordinate, false);
                    }
                }
            }
        }

        return new Grid(cells);
    }

    @Override
    public String toString() {
        return "Grid{" +
                "cells=" + cells +
                '}';
    }
}
