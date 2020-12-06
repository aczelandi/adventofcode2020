package day2;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var inputs = program.readInput("day2/input.txt", program::readInputs);
        System.out.println(inputs);

        var validPasswordsRule1 = program.getValidPasswords(inputs, new PasswordValidatorRule1());
        System.out.println(validPasswordsRule1.size());

        var validPasswordsRule2 = program.getValidPasswords(inputs, new PasswordValidatorRule2());
        System.out.println(validPasswordsRule2.size());
    }

    private List<Input> readInputs(URI filePath) {
        var items = new ArrayList<Input>();
        try (var sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                var line = sc.nextLine();
                var item = line.split(":");
                var policy = parsePolicy(item[0]);
                var password = item[1].trim();
                items.add(new Input(policy, password));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return items;
    }

    private Policy parsePolicy(String policyStr) {
        var components = policyStr.split(" ", 2);
        var lowAndHigh = components[0].split("-");
        var index1 = Integer.parseInt(lowAndHigh[0]);
        var index2 = Integer.parseInt(lowAndHigh[1]);
        return new Policy(index1, index2, components[1].toCharArray()[0]);
    }

    private List<String> getValidPasswords(List<Input> inputs, Predicate<Input> passwordValidator) {
        return inputs.stream()
                .filter(passwordValidator)
                .map(p -> p.password)
                .collect(Collectors.toList());
    }

    private static class PasswordValidatorRule1 implements Predicate<Input> {

        @Override
        public boolean test(Input input) {
            var password = input.password;
            var charToAppear = input.policy.letter;
            var minTimes = input.policy.index1;
            var maxTimes = input.policy.index2;

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

    private static class PasswordValidatorRule2 implements Predicate<Input> {

        @Override
        public boolean test(Input input) {
            var password = input.password;
            var charToAppear = input.policy.letter;
            var index1 = input.policy.index1;
            var index2 = input.policy.index2;

            var charAtIndex1 = password.charAt(index1 - 1);
            var charAtIndex2 = password.charAt(index2 - 1);

            return (charAtIndex1 == charToAppear) ^ (charAtIndex2 == charToAppear);
        }
    }

    private record Policy(int index1, int index2, char letter) {
    }

    private record Input(Policy policy, String password) {
    }
}
