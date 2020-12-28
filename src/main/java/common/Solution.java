package common;

public abstract class Solution<K1, K2, T1, T2> {

    protected final K1 context1;

    protected final K2 context2;

    public Solution(K1 context1, K2 context2) {
        this.context1 = context1;
        this.context2 = context2;
    }

    public abstract T1 solvePart1();

    public abstract T2 solvePart2();
}
