package day15;

import common.Solution;
import day15.model.Turns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SolutionImpl extends Solution<List<Integer>, List<Integer>, Integer, Integer> {

    public SolutionImpl(List<Integer> context1, List<Integer> context2) {
        super(context1, context2);
    }

    @Override
    public Integer solvePart1() {
        return this.getNumberAtIndex(this.context1, 2020);
    }

    @Override
    public Integer solvePart2() {
        return this.getNumberAtIndex(this.context2, 30000000);
    }

    private Integer getNumberAtIndex(List<Integer> originalNumbers, int index) {
        var numbers = new ArrayList<>(originalNumbers);
        var turnsByNumber = this.getTurnsByNumber(numbers);
        var startIndex = numbers.size();
        for (int i = startIndex; i < index; i++) {
            var mostRecentNumber = numbers.get(i - 1);
            var turnsOfNumber = turnsByNumber.get(mostRecentNumber);
            Integer numberToAdd = null;
            Turns turnsOfCurrent;
            if (turnsOfNumber.getPreviousTurn() == null) { // only once spoken
                numberToAdd = 0;
            } else { // spoken before
                var turnsOfMostRecent = turnsByNumber.get(mostRecentNumber);
                numberToAdd = turnsOfMostRecent.getLastTurn() - turnsOfMostRecent.getPreviousTurn();
            }

            numbers.add(numberToAdd);
            turnsOfCurrent = turnsByNumber.get(numberToAdd);
            if (turnsOfCurrent == null) {
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
}
