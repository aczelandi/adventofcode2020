package day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        var inputReader = new InputReader();
        var input = inputReader.readInput("day6/input.txt");

        var solution = new SolutionImpl(input);
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }

    private Integer readTotalAnswers_AnyoneYes(URI filePath) {
        var totalAnswers = 0;
        try (var sc = new Scanner(new File(filePath))) {
            sc.useDelimiter("\n\\s+");
            while (sc.hasNext()) {
                var group = sc.next();
                var persons = group.replace("\n", "").chars();
                totalAnswers += persons.distinct().count();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return totalAnswers;
    }

    private Integer readTotalAnswers_EveryoneYes(URI filePath) {
        var totalAnswers = 0;
        try (var sc = new Scanner(new File(filePath))) {
            sc.useDelimiter("\n\\s+");
            while (sc.hasNext()) {
                var group = sc.next();
                var personsInGroup = group.split("\n").length;
                var persons = group.replace("\n", "").chars();
                Map<Integer, Long> charOccurrence = persons.boxed()
                        .collect(Collectors.groupingBy(k -> k, Collectors.counting()));

                var allAnsweredByAll = charOccurrence.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() == personsInGroup) // all answered
                        .count();

                totalAnswers += allAnsweredByAll;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return totalAnswers;
    }
}
