package day06;

import common.Pair2;
import common.Solution;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class SolutionImpl extends Solution<List<Pair2<Integer, String>>, List<Pair2<Integer, String>>, Long, Long> {

    public SolutionImpl(List<Pair2<Integer, String>> context) {
        super(context, context);
    }

    @Override
    public Long solvePart1() {
        return this.context1
                .stream()
                .map(c -> c.right().chars().distinct().count())
                .reduce(Long::sum)
                .orElse(0L);
    }

    @Override
    public Long solvePart2() {
        var totalAnswers = 0L;
        for (Pair2<Integer, String> answersInGroupWithPersonCount : this.context2) {
            var answersInGroup = answersInGroupWithPersonCount.right();
            Map<Integer, Long> charOccurrence = answersInGroup.chars().boxed()
                    .collect(Collectors.groupingBy(k -> k, Collectors.counting()));

            var allAnsweredByAll = charOccurrence.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() == (long)answersInGroupWithPersonCount.left()) // all answered
                    .count();

            totalAnswers += allAnsweredByAll;
        }

        return totalAnswers;
    }
}
