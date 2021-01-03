package day04;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class InputReader extends BaseInputReader<Integer> {

    private final List<Predicate<Map<String, String>>> validationPredicates;

    public InputReader(List<Predicate<Map<String, String>>> validationPredicates) {
        this.validationPredicates = validationPredicates;
    }

    @Override
    protected Integer read(URI filePath) throws FileNotFoundException {
        var validCount = 0;
        try (var sc = new Scanner(new File(filePath))) {
            sc.useDelimiter("\n\\s+");
            while (sc.hasNext()) {
                var passport = sc.next();
                var fields = getFields(passport);
                if (validationPredicates.stream().allMatch(p -> p.test(fields))) {
                    validCount++;
                }

            }
        }

        return validCount;
    }

    private static Map<String, String> getFields(String passport) {
        return Arrays.stream(passport.replace("\n", " ").split(" "))
                .map(e -> {
                    var keyAndValue = e.split(":");
                    return Map.entry(keyAndValue[0], keyAndValue[1]);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
