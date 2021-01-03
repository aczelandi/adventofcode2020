package day02;

import common.Solution;
import day02.model.Input;

import java.util.List;

class SolutionImpl extends Solution<List<Input>, List<Input>, Long, Long> {

    public SolutionImpl(List<Input> context) {
        super(context, context);
    }

    @Override
    public Long solvePart1() {
        return this.context1.stream()
                .filter(new PasswordValidatorRule1())
                .map(Input::password).count();
    }

    @Override
    public Long solvePart2() {
        return this.context2.stream()
                .filter(new PasswordValidatorRule2())
                .map(Input::password).count();
    }
}
