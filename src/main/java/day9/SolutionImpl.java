package day9;

import common.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class SolutionImpl extends Solution<List<Long>, List<Long>, Long, Long> {

    private final int preambleCount;

    public SolutionImpl(List<Long> context, int preambleCount) {
        super(context, context);
        this.preambleCount = preambleCount;
    }

    @Override
    public Long solvePart1() {
        var numbers = this.context1;
        var knownSums = this.getKnownSums(numbers, preambleCount);
        for (int i = preambleCount, j = 0; i < numbers.size(); i++, j++) {
            var current = numbers.get(i);
            if (!knownSums.contains(current)) {
                return current;
            }
            knownSums = getKnownSums(numbers.subList(j + 1, preambleCount + j + 1), preambleCount);
        }

        throw new NoSuchElementException("Could not identify a wrong element");
    }

    @Override
    public Long solvePart2() {
        var windowLength = 2;
        var sumToFind = solvePart1();
        while (true) {
            var slidingListOfSum = this.getSlidingListOfSum(this.context2, windowLength, sumToFind);
            if (slidingListOfSum != null) {
                var min = slidingListOfSum.stream().min(Long::compareTo).orElse(0L);
                var max = slidingListOfSum.stream().max(Long::compareTo).orElse(0L);
                return min + max;
            }
            windowLength++;
        }
    }

    private List<Long> getKnownSums(List<Long> numbers, int preambleCount) {
        if (numbers.size() < preambleCount) {
            throw new IllegalArgumentException("Unexpected number of records");
        }

        var sums = new ArrayList<Long>();

        for (int i = 0; i < preambleCount - 1; i++) {
            for (int j = 1; j < preambleCount; j++) {
                var numberAtI = numbers.get(i);
                var numberAtJ = numbers.get(j);
                if (!numberAtI.equals(numberAtJ)) {
                    sums.add(numbers.get(i) + numbers.get(j));
                }
            }
        }

        return sums;
    }

    private List<Long> getSlidingListOfSum(List<Long> numbers, int windowLength, long sumToFind) {
        for (int i = 0; i < numbers.size() - windowLength; i++) {
            var numberInWindow = numbers.subList(i, i + windowLength);
            if (numberInWindow.stream().reduce(Long::sum).orElse(0L) == sumToFind) {
                return numberInWindow;
            }
        }

        return null;
    }
}
