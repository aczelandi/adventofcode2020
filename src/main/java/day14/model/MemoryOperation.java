package day14.model;

import day14.BinaryNumberGenerator;

import java.math.BigInteger;

public class MemoryOperation implements Operation {

    private final BigInteger memoryLocation;

    private final BigInteger value;

    public MemoryOperation(BigInteger memoryLocation, BigInteger value) {
        this.memoryLocation = memoryLocation;
        this.value = value;
    }

    @Override
    public State applyV1(State state) {
        if (state.getMask() == null) {
            state.updateMemory(this.memoryLocation, this.value);
        } else {
            var currentMask = state.getMask();
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
        if (state.getMask() == null) {
            state.updateMemory(this.memoryLocation, this.value);
        } else {
            var currentMask = state.getMask();
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
