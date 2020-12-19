package day10;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Main extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var jolts = program.readInput("day10/input.txt", program::readInput);
        jolts.sort(Integer::compareTo);
        System.out.println(jolts);

        var joltDifference = program.getJoltDifferences(jolts);
        System.out.println(joltDifference);

        var joltsWithAdapters = program.extendWithFirst(jolts);
        var visitedCache = new HashMap<Integer, Long>();
        var distinctArrangements = program.getDistinctSetupCount(joltsWithAdapters, 0, visitedCache);
        System.out.println(distinctArrangements);
    }

    private List<Integer> readInput(URI filePath) {
        var numbers = new ArrayList<Integer>();
        try {
            var scanner = new Scanner(new File(filePath));
            while (scanner.hasNext()) {
                numbers.add(Integer.parseInt(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    private int getJoltDifferences(List<Integer> jolts) {
        var differencesOf1 = 0;
        var differencesOf3 = 0;
        var currentJoltage = 0;

        for (Integer adapterJoltage : jolts) {
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

    private List<Integer> extendWithFirst(List<Integer> numbers) {
        var extended = new ArrayList<>(numbers);
        extended.add(0, 0);

        return extended;
    }
}
