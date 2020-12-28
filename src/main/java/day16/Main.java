package day16;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        var inputReader = new InputReader();
        var ticketRules = inputReader.readInput("day16/input.txt");

        System.out.println(ticketRules);

        var solution = new SolutionImpl(ticketRules);
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println(part1);
        System.out.println(part2);
    }
}
