package day18;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        var inputReaderV1 = new InputReader<>(new ExpressionV1StringParser());
        var inputReaderV2 = new InputReader<>(new ExpressionV2StringParser());

        var inputPart1 = inputReaderV1.readInput("day18/input.txt");
        var inputPart2 = inputReaderV2.readInput("day18/input.txt");

        var expressionV1Visitor = new ExpressionV1VisitorImpl();
        var expressionV2Visitor = new ExpressionV2VisitorImpl();
        var solution = new SolutionImpl(inputPart1, inputPart2, expressionV1Visitor, expressionV2Visitor);
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }
}