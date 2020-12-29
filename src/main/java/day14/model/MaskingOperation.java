package day14.model;

public class MaskingOperation implements Operation {

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