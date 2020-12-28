package day2;

import day2.model.Input;

import java.util.function.Predicate;

public class PasswordValidatorRule1 implements Predicate<Input> {

    @Override
    public boolean test(Input input) {
        var password = input.password();
        var charToAppear = input.policy().letter();
        var minTimes = input.policy().index1();
        var maxTimes = input.policy().index2();

        var charCount = 0;
        for (int i = 0; i < password.length(); i++) {
            var currentChar = password.charAt(i);
            if (currentChar == charToAppear) {
                charCount++;
                if (charCount > maxTimes) {
                    return false;
                }
            }
        }

        return charCount >= minTimes;
    }
}