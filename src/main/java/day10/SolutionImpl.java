package day10;

import common.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionImpl extends Solution<List<Integer>, List<Integer>, Integer, Long> {

    public SolutionImpl(List<Integer> context) {
        super(context, context);
    }

    @Override
    public Integer solvePart1() {
        var differencesOf1 = 0;
        var differencesOf3 = 0;
        var currentJoltage = 0;

        this.context1.sort(Integer::compareTo);

        for (Integer adapterJoltage : this.context1) {
            int diff = adapterJoltage - currentJoltage;
            if (diff > 3 || diff < 1) {
                throw new IllegalStateException("Can't solve problem with provided input.");
            }
            if (diff == 1) {
                differencesOf1++;
            } else if (diff == 3) {
                differencesOf3++;
            }

            currentJoltage = adapterJoltage;
        }

        return differencesOf1 * (differencesOf3 + 1);
    }

    @Override
    public Long solvePart2() {
        this.context2.add(0, 0); // extend with first
        this.context2.sort(Integer::compareTo);
        var visitedCache = new HashMap<Integer, Long>();
        return getDistinctSetupCount(this.context2, 0, visitedCache);
    }

    private long getDistinctSetupCount(List<Integer> indexedJolts, int currentIndex, Map<Integer, Long> cache) {
        if (currentIndex == indexedJolts.size() - 1) {
            return 1;
        }

        if (cache.containsKey(currentIndex)) {
            return cache.get(currentIndex);
        }

        var total = 0L;

        for (int nextIndex = currentIndex + 1; nextIndex < indexedJolts.size(); nextIndex++) {
            if (indexedJolts.get(nextIndex) - indexedJolts.get(currentIndex) > 3) break;
            total += getDistinctSetupCount(indexedJolts, nextIndex, cache);
        }
        cache.put(currentIndex, total);

        return total;
    }
}
