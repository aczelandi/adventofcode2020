package day4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ValidationPredicateRule2 implements Predicate<Map<String, String>> {

    private static final List<String> validEyeColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

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

    private boolean validateIntField(String stringValue, Function<Integer, Boolean> validationFunc) {
        try {
            var intValue = Integer.parseInt(stringValue);
            return validationFunc.apply(intValue);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}