package day04;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        var inputReaderPart1 = new InputReader(List.of(new ValidationPredicateRule1()));
        var inputPart1 = inputReaderPart1.readInput("day04/input.txt");

        var inputReaderPart2 = new InputReader(List.of(new ValidationPredicateRule1(), new ValidationPredicateRule2()));
        var inputPart2 = inputReaderPart2.readInput("day04/input.txt");

        var solution = new SolutionImpl(inputPart1, inputPart2);
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }
}
