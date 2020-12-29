package day14;

import common.Solution;
import day14.model.Operation;
import day14.model.State;

import java.math.BigInteger;
import java.util.List;

class SolutionImpl extends Solution<List<Operation>, List<Operation>, BigInteger, BigInteger> {

    public SolutionImpl(List<Operation> context1, List<Operation> context2) {
        super(context1, context2);
    }

    @Override
    public BigInteger solvePart1() {
        var currentState = new State();
        this.context1.forEach(currentState::acceptV1);
        return currentState.getMemory().values().stream().reduce(BigInteger::add).orElse(null);
    }

    @Override
    public BigInteger solvePart2() {
        var currentState = new State();
        this.context2.forEach(currentState::acceptV2);
        return currentState.getMemory().values().stream().reduce(BigInteger::add).orElse(null);
    }
}
