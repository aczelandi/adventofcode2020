package day04;

import common.Solution;

class SolutionImpl extends Solution<Integer, Integer, Integer, Integer> {

    public SolutionImpl(Integer context1, Integer context2) {
        super(context1, context2);
    }

    @Override
    public Integer solvePart1() {
        return context1;
    }

    @Override
    public Integer solvePart2() {
        return context2;
    }
}
