package day1;

import common.Pair;
import common.Solution;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class SolutionImpl extends Solution<List<Integer>, Long, Long> {

    private final int valueToReach;

    public SolutionImpl(List<Integer> input, int valueToReach) {
        super(input);
        this.valueToReach = valueToReach;
    }

    @Override
    public Long solvePart1() {
        var pair = this.findPair2ThatSumsToValue(this.valueToReach);
        return pair.map(p -> (long) p.left() * p.right()).orElse(0L);
    }

    @Override
    public Long solvePart2() {
        var numbersToFind = this.input.stream()
                .collect(Collectors.toMap(k -> k, v -> this.valueToReach - v));

        Optional<Map.Entry<Integer, Pair<Integer>>> elements = numbersToFind.entrySet()
                .stream()
                .map(e -> {
                    var pair2ThatSumsToValue = findPair2ThatSumsToValue(e.getValue());
                    return Map.entry(e.getKey(), pair2ThatSumsToValue);
                })
                .filter(e -> e.getValue().isPresent())
                .map(e -> Map.entry(e.getKey(), e.getValue().get()))
                .findAny();

        if (elements.isEmpty()) {
            return 0L;
        }

        var el = elements.get();
        return (long) el.getKey() * el.getValue().left() * el.getValue().right();
    }

    private Optional<Pair<Integer>> findPair2ThatSumsToValue(int value) {
        var numbersToFind = this.input.stream()
                .collect(Collectors.toMap(k -> value - k, v -> v));

        var pairOfNumber = this.input.stream()
                .filter(numbersToFind::containsKey)
                .findAny();

        if (pairOfNumber.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new Pair<>(numbersToFind.get(pairOfNumber.get()), pairOfNumber.get()));
    }
}
