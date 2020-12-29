package day14.model;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class State {

    private String mask;

    private final Map<BigInteger, BigInteger> memory = new HashMap<>();

    public void updateMask(String newMask) {
        this.mask = newMask;
    }

    public void updateMemory(BigInteger index, BigInteger value) {
        this.memory.put(index, value);
    }

    public String getMask() {
        return mask;
    }

    public Map<BigInteger, BigInteger> getMemory() {
        return memory;
    }

    public void acceptV1(Operation operation) {
        operation.applyV1(this);
    }

    public void acceptV2(Operation operation) {
        operation.applyV2(this);
    }
}
