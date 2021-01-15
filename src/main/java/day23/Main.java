package day23;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        var gamePart1 = new InputReader(false).readInput("day23/input.txt");
        var gamePart2 = new InputReader(true).readInput("day23/input.txt");

        var solution = new SolutionImpl(gamePart1, gamePart2);
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }
}
