package day15;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var numbers = program.readInput("day15/input.txt", program::readInput);
        System.out.println(numbers);

        var numberAtIndex = program.getNumberAtIndex(numbers, 2020);
        System.out.println(numberAtIndex);

        var numberAtIndex2 = program.getNumberAtIndex(numbers, 30000000);
        System.out.println(numberAtIndex2);
    }

    private List<Integer> readInput(URI filePath) {
        List<Integer> numbers = null;
        try (var scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNext()) {
                var line = scanner.next();
                numbers = Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }

        return numbers;
    }

    private Integer getNumberAtIndex(List<Integer> originalNumbers, int index) {
        var numbers = new ArrayList<>(originalNumbers);
        var turnsByNumber = this.getTurnsByNumber(numbers);
        System.out.println(turnsByNumber);
        var startIndex = numbers.size();
        for (int i = startIndex; i < index; i++) {
            var mostRecentNumber = numbers.get(i - 1);
            var turnsOfNumber = turnsByNumber.get(mostRecentNumber);
            Integer numberToAdd = null;
            Turns turnsOfCurrent;
            if (turnsOfNumber.previousTurn == null) { // only once spoken
                numberToAdd = 0;
            } else { // spoken before
                var turnsOfMostRecent = turnsByNumber.get(mostRecentNumber);
                numberToAdd = turnsOfMostRecent.lastTurn - turnsOfMostRecent.previousTurn;
            }

            numbers.add(numberToAdd);
            turnsOfCurrent = turnsByNumber.get(numberToAdd);
            if (turnsOfCurrent == null){
                turnsOfCurrent = new Turns(Collections.emptyList());
                turnsByNumber.put(numberToAdd, turnsOfCurrent);
            }
            turnsOfCurrent.addNewTurn(i + 1);
        }

        return numbers.get(index - 1);
    }

    private Map<Integer, Turns> getTurnsByNumber(List<Integer> numbers) {
        var numbersByAllTurns = IntStream.range(0, numbers.size())
                .boxed()
                .map(index -> Map.entry(numbers.get(index), index + 1))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        return numbersByAllTurns.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> new Turns(v.getValue())));
    }

    private static class Turns {

        private Integer previousTurn;

        private Integer lastTurn;

        public Turns(List<Integer> indexes) {
            var indexesInOrder = indexes.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            if (indexesInOrder.size() > 0) {
                this.lastTurn = indexes.get(0);
            }
            if (indexesInOrder.size() > 1) {
                this.previousTurn = indexes.get(1);
            }
        }

        public void addNewTurn(Integer newTurn) {
            this.previousTurn = lastTurn;
            this.lastTurn = newTurn;
        }

        @Override
        public String toString() {
            return "Turns{" +
                    "previousTurn=" + previousTurn +
                    ", lastTurn=" + lastTurn +
                    '}';
        }
    }
}
