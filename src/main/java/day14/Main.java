package day14;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main extends BaseMain {

    private final Pattern memoryOperationPattern = Pattern.compile("mem\\[([0-9]*)\\]=([0-9]*)");

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var operations = program.readInput("day14/input.txt", program::readInput);
        System.out.println(operations);

        var memoryStatusV1 = program.executeOperationsV1(operations);
        var sumOfElementsV1 = memoryStatusV1.values().stream().reduce(BigInteger::add).orElse(null);
        System.out.println(sumOfElementsV1);

        var memoryStatusV2 = program.executeOperationsV2(operations);
        var sumOfElementsV2 = memoryStatusV2.values().stream().reduce(BigInteger::add).orElse(null);
        System.out.println(sumOfElementsV2);
    }

    private List<Operation> readInput(URI filePath) {
        var operations = new ArrayList<Operation>();
        try (var scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                var line = scanner.nextLine().replaceAll("\\s+", "");
                if (line.startsWith("mask")) {
                    var operation = new MaskingOperation(line.split("=")[1]);
                    operations.add(operation);
                } else if (line.startsWith("mem")) {
                    var matcher = memoryOperationPattern.matcher(line);
                    while (matcher.find()) {
                        var memoryLocation = matcher.group(1);
                        var value = matcher.group(2);
                        var operation = new MemoryOperation(new BigInteger(memoryLocation), new BigInteger(value));
                        operations.add(operation);
                    }
                } else {
                    throw new IllegalStateException("Unexpected operation " + line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return operations;
    }

    private Map<BigInteger, BigInteger> executeOperationsV1(List<Operation> operations) {
        var currentState = new State();
        operations.forEach(currentState::acceptV1);
        return currentState.memory;
    }

    private Map<BigInteger, BigInteger> executeOperationsV2(List<Operation> operations) {
        var currentState = new State();
        operations.forEach(currentState::acceptV2);
        return currentState.memory;
    }

    private interface Operation {

        State applyV1(State state);

        State applyV2(State state);
    }

    public static class MaskingOperation implements Main.Operation {

        private final String mask;

        public MaskingOperation(String mask) {
            this.mask = mask;
        }

        @Override
        public State applyV1(State state) {
            state.updateMask(mask);
            return state;
        }

        @Override
        public State applyV2(State state) {
            state.updateMask(mask);
            return state;
        }

        @Override
        public String toString() {
            return "MaskingOperation{" +
                    "mask='" + mask + '\'' +
                    '}';
        }
    }

    public static class MemoryOperation implements Main.Operation {

        private final BigInteger memoryLocation;

        private final BigInteger value;

        public MemoryOperation(BigInteger memoryLocation, BigInteger value) {
            this.memoryLocation = memoryLocation;
            this.value = value;
        }

        @Override
        public State applyV1(State state) {
            if (state.mask == null) {
                state.updateMemory(this.memoryLocation, this.value);
            } else {
                var currentMask = state.mask;
                var onlyOnes = new BigInteger(currentMask.replace('X', '0'), 2);
                var appliedOnes = this.value.or(onlyOnes);
                var onlyZeros = new BigInteger(currentMask.replace('X', '1'), 2);
                var appliedZeros = appliedOnes.and(onlyZeros);
                state.updateMemory(this.memoryLocation, appliedZeros);
            }

            return state;
        }

        @Override
        public State applyV2(State state) {
            if (state.mask == null) {
                state.updateMemory(this.memoryLocation, this.value);
            } else {
                var currentMask = state.mask;
                var onlyOnes = new BigInteger(currentMask.replace('X', '0'), 2);
                var newLocation = this.memoryLocation.or(onlyOnes);
                var floatingChars = currentMask.chars().filter(c -> c == 'X').count();
                var combinations = BinaryNumberGenerator.generate(floatingChars);
                
                for (String currentCombination : combinations) {
                    var nextCombinationIndex = 0;
                    for (int j = 0; j < currentMask.length(); j++) {
                        if (currentMask.charAt(j) == 'X') {
                            var currentCharToSet = currentCombination.charAt(nextCombinationIndex);
                            if (currentCharToSet == '0') {
                                newLocation = newLocation.clearBit(36 - j - 1);
                            } else {
                                newLocation = newLocation.setBit(36 - j - 1);
                            }
                            nextCombinationIndex++;
                        }
                    }

                    state.updateMemory(newLocation, this.value);
                }
            }

            return state;
        }

        @Override
        public String toString() {
            return "MemoryOperation{" +
                    "memoryLocation=" + memoryLocation +
                    ", value=" + value +
                    '}';
        }
    }

    public static class State {

        private String mask;

        private final Map<BigInteger, BigInteger> memory = new HashMap<>();

        public void updateMask(String newMask) {
            this.mask = newMask;
        }

        public void updateMemory(BigInteger index, BigInteger value) {
            this.memory.put(index, value);
        }

        public void acceptV1(Main.Operation operation) {
            operation.applyV1(this);
        }

        public void acceptV2(Main.Operation operation) {
            operation.applyV2(this);
        }
    }

    private static class BinaryNumberGenerator {

        public static List<String> generate(long length) {
            if (length == 0) {
                return Collections.emptyList();
            }

            return IntStream.range(0, (int) Math.pow(2, length))
                    .boxed()
                    .map(i -> {
                        var stringValue = Integer.toBinaryString(i);
                        return String.format("%" + length + "s", stringValue).replace(' ', '0');
                    })
                    .collect(Collectors.toList());
        }
    }
}
