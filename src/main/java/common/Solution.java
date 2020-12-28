package common;

public abstract class Solution<K, T1, T2> {

    protected final K input;

    public Solution(K input) {
        this.input = input;
    }

    public abstract T1 solvePart1();

    public abstract T2 solvePart2();
}
