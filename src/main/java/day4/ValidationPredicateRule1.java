package day4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ValidationPredicateRule1 implements Predicate<Map<String, String>> {

    private static final List<String> mandatoryKeys = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    @Override
    public boolean test(Map<String, String> fields) {
        return fields.keySet().containsAll(mandatoryKeys);
    }
}