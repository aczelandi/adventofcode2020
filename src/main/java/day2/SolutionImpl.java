package day2;

import common.Solution;
import day2.model.Input;

import java.util.List;

public class SolutionImpl extends Solution<List<Input>, Long, Long> {

    public SolutionImpl(List<Input> input) {
        super(input);
    }

    @Override
    public Long solvePart1() {
        return this.input.stream()
                .filter(new PasswordValidatorRule1())
                .map(Input::password).count();
    }

    @Override
    public Long solvePart2() {
        return this.input.stream()
                .filter(new PasswordValidatorRule2())
                .map(Input::password).count();
    }
}
