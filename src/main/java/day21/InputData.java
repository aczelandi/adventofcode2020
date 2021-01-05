package day21;

import java.util.List;
import java.util.Map;

public record InputData(Map<String, Long>ingredientsCount, Map<String, List<List<String>>>allergyToIngredients) {
}
