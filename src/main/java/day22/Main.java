package day22;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        var game1 = new InputReader().readInput("day22/input.txt");
        var game2 = new InputReader().readInput("day22/input.txt");

        var solution = new SolutionImpl(game1, game2);
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }
}
