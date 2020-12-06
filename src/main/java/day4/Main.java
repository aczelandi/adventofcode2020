package day4;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main extends BaseMain {

    private static final List<String> mandatoryKeys = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    private static final List<String> validEyeColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var validInputCount1 = program.readInput("day4/input.txt", program::readValidInputCount_With1Condition);
        System.out.println(validInputCount1);

        var validInputCount2 = program.readInput("day4/input.txt", program::readValidInputCount_With2Conditions);
        System.out.println(validInputCount2);
    }

    private Integer readValidInputCount_With1Condition(URI filePath) {
        var validCount = 0;
        var predicate = new ValidationPredicate1();
        try (var sc = new Scanner(new File(filePath))) {
            sc.useDelimiter("\n\\s+");
            while (sc.hasNext()) {
                var passport = sc.next();
                var fields = getFields(passport);
                if (predicate.test(fields)) {
                    validCount++;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return validCount;
    }

    private Integer readValidInputCount_With2Conditions(URI filePath) {
        var validCount = 0;
        var predicate1 = new ValidationPredicate1();
        var predicate2 = new ValidationPredicate2();
        try (var sc = new Scanner(new File(filePath))) {
            sc.useDelimiter("\n\\s+");
            while (sc.hasNext()) {
                var passport = sc.next();
                var fields = getFields(passport);
                if (predicate1.test(fields) && predicate2.test(fields)) {
                    validCount++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return validCount;
    }

    private Map<String, String> getFields(String passport) {
        return Arrays.stream(passport.replace("\n", " ").split(" "))
                .map(e -> {
                    var keyAndValue = e.split(":");
                    return Map.entry(keyAndValue[0], keyAndValue[1]);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static class ValidationPredicate1 implements Predicate<Map<String, String>> {

        @Override
        public boolean test(Map<String, String> fields) {
            return fields.keySet().containsAll(mandatoryKeys);
        }
    }

    private class ValidationPredicate2 implements Predicate<Map<String, String>> {

        @Override
        public boolean test(Map<String, String> fields) {
            var birthYear = fields.get("byr");
            var issueYear = fields.get("iyr");
            var expYear = fields.get("eyr");
            var height = fields.get("hgt");
            var hairColor = fields.get("hcl");
            var eyeColor = fields.get("ecl");
            var passportId = fields.get("pid");

            var validBirthYear = validateIntField(birthYear, yr -> yr >= 1920 && yr <= 2002);
            if (!validBirthYear) {
                return false;
            }

            var validIssueYear = validateIntField(issueYear, yr -> yr >= 2010 && yr <= 2020);
            if (!validIssueYear) {
                return false;
            }

            var validExpirationYear = validateIntField(expYear, yr -> yr >= 2020 && yr <= 2030);
            if (!validExpirationYear) {
                return false;
            }

            var validHeight = false;

            if (height.endsWith("cm")) {
                validHeight = validateIntField(height.substring(0, height.length() - 2), h -> h >= 150 & h <= 193);
            } else if (height.endsWith("in")) {
                validHeight = validateIntField(height.substring(0, height.length() - 2), h -> h >= 59 & h <= 76);
            }

            if (!validHeight) {
                return false;
            }

            var hairPattern = Pattern.compile("#[0-9a-f]{6}$");
            var validHairColor = hairPattern.matcher(hairColor).matches();
            if (!validHairColor) {
                return false;
            }

            var validEyeColor = validEyeColors.contains(eyeColor);
            if (!validEyeColor) {
                return false;
            }

            return passportId.length() == 9;
        }
    }

    private boolean validateIntField(String stringValue, Function<Integer, Boolean> validationFunc) {
        try {
            var intValue = Integer.parseInt(stringValue);
            return validationFunc.apply(intValue);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
