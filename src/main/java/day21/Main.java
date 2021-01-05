package day21;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        var ingredientsCountPart1 = new IngredientsMapReader().readInput("day21/input.txt");
        var allergyToIngredientsPart1 = new InputReader().readInput("day21/input.txt");

        // clone
        var ingredientsCountPart2 = new IngredientsMapReader().readInput("day21/input.txt");
        var allergyToIngredientsPart2 = new InputReader().readInput("day21/input.txt");

        var solution = new SolutionImpl(new InputData(ingredientsCountPart1, allergyToIngredientsPart1), new InputData(ingredientsCountPart2, allergyToIngredientsPart2));
        var part1 = solution.solvePart1();
        var part2 = solution.solvePart2();
        System.out.println("Solution for part 1: " + part1);
        System.out.println("Solution for part 2: " + part2);
    }
}
