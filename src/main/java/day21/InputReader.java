package day21;

import common.BaseInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class InputReader extends BaseInputReader<Map<String, List<List<String>>>> {

    private static final Pattern InputLinePattern = Pattern.compile("^(.*)\\(contains(.*)\\)");

    @Override
    protected Map<String, List<List<String>>> read(URI filePath) throws FileNotFoundException {
        var allergyToIngredients = new HashMap<String, List<List<String>>>();
        try (var scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                var line = scanner.nextLine();
                var match = InputLinePattern.matcher(line);
                if (match.find()) {
                    var ingredients = match.group(1).trim();
                    var allergies = match.group(2).trim();

                    Arrays.stream(allergies.split(", "))
                            .forEach(allergy -> {
                                List<List<String>> foodsList = allergyToIngredients.computeIfAbsent(allergy, k -> new ArrayList<>());
                                foodsList.add(Arrays.stream(ingredients.split(" ")).collect(Collectors.toList()));
                            });
                }
            }
        }

        return allergyToIngredients;
    }
}
