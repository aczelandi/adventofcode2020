package day21;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class IngredientsMapReader extends BaseInputReader<Map<String, Long>> {

    private static final Pattern InputPattern = Pattern.compile("^(.*)\\(contains(.*)\\)");

    @Override
    protected Map<String, Long> read(URI filePath) throws FileNotFoundException {
        var ingredients = new ArrayList<String>();
        try (var scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                var line = scanner.nextLine();
                var match = InputPattern.matcher(line);
                if (match.find()) {
                    ingredients.addAll(Arrays.stream(match.group(1).trim().split(" ")).collect(Collectors.toList()));
                }
            }
        }

        return ingredients.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting()));
    }
}

