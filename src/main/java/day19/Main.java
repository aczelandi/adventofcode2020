package day19;

import day19.model.InputData;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        var inputRulesReader = new InputRulesReader();
        var inputMessagesReader = new InputMessagesReader();

        var inputRulesPart1 = inputRulesReader.readInput("day19/input_part1.txt");
        var inputMessagesPart1 = inputMessagesReader.readInput("day19/input_part1.txt");

        var inputRulesPart2 = inputRulesReader.readInput("day19/input_part2.txt");
        var inputMessagesPart2 = inputMessagesReader.readInput("day19/input_part2.txt");

        var inputDataPart1 = new InputData(inputRulesPart1, inputMessagesPart1);
        var inputDataPart2 = new InputData(inputRulesPart2, inputMessagesPart2);
        var solution = new SolutionImpl(inputDataPart1, inputDataPart2);
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }
}
