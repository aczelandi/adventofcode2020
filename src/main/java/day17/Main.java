package day17;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        var inputSizeReader = new GridSizeReader();
        var size = inputSizeReader.readInput("day17/input.txt");

        var threeDGridReader = new ThreeDGridReader(size);
        var gridPart1 = threeDGridReader.readInput("day17/input.txt");
        var fourDGridReader = new FourDGridReader(size);
        var gridPart2 = fourDGridReader.readInput("day17/input.txt");

        var solution = new SolutionImpl(gridPart1, gridPart2);
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }
}
