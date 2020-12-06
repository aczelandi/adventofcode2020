package day1;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Main extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var numbers = program.readInput("day1/input.txt", program::readNumbers);

        var pairThatSumsToValue = program.findPair2ThatSumsToValue(numbers, 2020).orElseThrow();
        System.out.println(pairThatSumsToValue);
        System.out.println(pairThatSumsToValue.left * pairThatSumsToValue.right);

        var pairOf3ThatSumsToValue = program.findPair3ThatSumsToValue(numbers, 2020).orElseThrow();
        System.out.println(pairOf3ThatSumsToValue);
        System.out.println(pairOf3ThatSumsToValue.left * pairOf3ThatSumsToValue.middle * pairOf3ThatSumsToValue.right);
    }

    private List<Integer> readNumbers(URI filePath) {
        var numbers = new ArrayList<Integer>();
        try (var sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                var num = sc.nextInt();
                numbers.add(num);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return numbers;
    }

    private Optional<Pair2<Integer>> findPair2ThatSumsToValue(List<Integer> numbers, int value) {
        var numbersToFind = numbers.stream()
                .collect(Collectors.toMap(k -> value - k, v -> v));

        var pairOfNumber = numbers.stream()
                .filter(numbersToFind::containsKey)
                .findAny();

        if (pairOfNumber.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new Pair2<>(numbersToFind.get(pairOfNumber.get()), pairOfNumber.get()));
    }

    private Optional<Pair3<Integer>> findPair3ThatSumsToValue(List<Integer> numbers, int value) {
        var numbersToFind = numbers.stream()
                .collect(Collectors.toMap(k -> k, v -> value - v));

        Optional<Map.Entry<Integer, Pair2<Integer>>> elements = numbersToFind.entrySet()
                .stream()
                .map(e -> {
                    var pair2ThatSumsToValue = findPair2ThatSumsToValue(numbers, e.getValue());
                    return Map.entry(e.getKey(), pair2ThatSumsToValue);
                })
                .filter(e -> e.getValue().isPresent())
                .map(e -> Map.entry(e.getKey(), e.getValue().get()))
                .findAny();

        if (elements.isEmpty()) {
            return Optional.empty();
        }

        var el = elements.get();
        return Optional.of(new Pair3<>(el.getKey(), el.getValue().left, el.getValue().right));
    }

    private record Pair2<A>(A left, A right) {
    }

    private record Pair3<A>(A left, A middle, A right) {
    }
}
