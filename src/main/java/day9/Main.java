package day9;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Main extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var numbers = program.readInput("day9/input.txt", program::readNumbers);
        System.out.println(numbers);

        var firstIncorrect = program.getFirstIncorrect(numbers, 25);
        System.out.println(firstIncorrect);

        var encryptionWeakness = program.getEncryptionWeakness(numbers, firstIncorrect);
        System.out.println(encryptionWeakness);
    }

    private List<Long> readNumbers(URI filePath) {
        var numbers = new ArrayList<Long>();
        try (var sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                numbers.add(sc.nextLong());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return numbers;
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

    private long getFirstIncorrect(List<Long> numbers, int preambleCount) {
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

    private long getEncryptionWeakness(List<Long> numbers, long sumToFind) {
        var windowLength = 2;
        while (true) {
            var slidingListOfSum = this.getSlidingListOfSum(numbers, windowLength, sumToFind);
            if (slidingListOfSum != null) {
                var min = slidingListOfSum.stream().min(Long::compareTo).orElse(0L);
                var max = slidingListOfSum.stream().max(Long::compareTo).orElse(0L);
                return min + max;
            }
            windowLength++;
        }
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
