package day11;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        var inputReader = new InputReader();
        var input = inputReader.readInput("day11/input.txt");

        var rowCount = input.length;
        var colCount = input[0].length;
        var solution = new SolutionImpl(input, cloneSeatMap(input, rowCount, colCount), rowCount, colCount);
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }

    private static char[][] cloneSeatMap(char[][] seatMap, int rowCount, int columnCount) {
        var clone = new char[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            if (columnCount >= 0) System.arraycopy(seatMap[i], 0, clone[i], 0, columnCount);
        }

        return clone;
    }
}
