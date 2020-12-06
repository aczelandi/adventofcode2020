package day6;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var totalAnswersForAnyoneWithYes = program.readInput("day6/input.txt", program::readTotalAnswers_AnyoneYes);
        System.out.println(totalAnswersForAnyoneWithYes);

        var totalAnswersForEveryoneWithYes = program.readInput("day6/input.txt", program::readTotalAnswers_EveryoneYes);
        System.out.println(totalAnswersForEveryoneWithYes);
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
