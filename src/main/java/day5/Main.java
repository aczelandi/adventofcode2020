package day5;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        var inputReader = new InputReader();
        var input = inputReader.readInput("day5/input.txt");

        var solution = new SolutionImpl(input);
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }
}
