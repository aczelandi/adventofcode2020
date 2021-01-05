package day21;

import common.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SolutionImpl extends Solution<InputData, InputData, Long, String> {

    public SolutionImpl(InputData context1, InputData context2) {
        super(context1, context2);
    }

    @Override
    public Long solvePart1() {
        var allergyToIngredients = this.context1.allergyToIngredients();
        HashMap<String, String> identifiedIngredients = getAllergyToIngredientsMapping(allergyToIngredients);

        return this.context1.ingredientsCount()
                .entrySet()
                .stream()
                .filter(e -> !identifiedIngredients.containsValue(e.getKey()))
                .mapToLong(Map.Entry::getValue)
                .sum();

    }

    @Override
    public String solvePart2() {
        var allergyToIngredients = this.context2.allergyToIngredients();
        HashMap<String, String> identifiedIngredients = getAllergyToIngredientsMapping(allergyToIngredients);

        return identifiedIngredients.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .reduce((i1, i2) -> i1 + "," + i2)
                .orElse("");
    }

    private HashMap<String, String> getAllergyToIngredientsMapping(Map<String, List<List<String>>> allergyToIngredients) {
        var ingredientIntersectionByAllergy = allergyToIngredients.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> this.getListIntersection(entry.getValue())));

        var identifiedIngredients = new HashMap<String, String>();
        while (identifiedIngredients.size() != allergyToIngredients.keySet().size()) {
            var uniqueMatch = ingredientIntersectionByAllergy.entrySet().stream().filter(e -> e.getValue().size() == 1).findFirst().orElseThrow();
            var ingredientMatch = uniqueMatch.getValue().get(0);
            identifiedIngredients.put(uniqueMatch.getKey(), ingredientMatch);
            ingredientIntersectionByAllergy.values().forEach(ingredients -> ingredients.remove(ingredientMatch));
        }
        return identifiedIngredients;
    }

    private List<String> getListIntersection(List<List<String>> lists) {
        return lists.stream()
                .reduce(lists.get(0), (acc, list) -> {
                    acc.retainAll(list);
                    return acc;
                }, (previousAcc, currentAcc) -> currentAcc);
    }
}
