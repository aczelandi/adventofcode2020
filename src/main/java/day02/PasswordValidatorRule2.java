package day02;

import day02.model.Input;

import java.util.function.Predicate;

public class PasswordValidatorRule2 implements Predicate<Input> {

    @Override
    public boolean test(Input input) {
        var password = input.password();
        var charToAppear = input.policy().letter();
        var index1 = input.policy().index1();
        var index2 = input.policy().index2();

        var charAtIndex1 = password.charAt(index1 - 1);
        var charAtIndex2 = password.charAt(index2 - 1);

        return (charAtIndex1 == charToAppear) ^ (charAtIndex2 == charToAppear);
    }
}