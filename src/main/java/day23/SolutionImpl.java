package day23;

import common.Solution;
import day23.model.Game;

import java.util.stream.IntStream;

public class SolutionImpl extends Solution<Game, Game, String, Long> {

    public SolutionImpl(Game context1, Game context2) {
        super(context1, context2);
    }

    @Override
    public String solvePart1() {
        var iterations = 100;
        var game = this.context1;
        IntStream.range(0, iterations)
                .boxed()
                .forEach(i -> {
                    var currentCup = game.currentCup();
                    var elementAtNextIndex = game.getValueAtOffset(3 + 1);
                    var nextThreeCups = game.removeCups(3);
                    var preferredDestination = currentCup - 1;
                    while (nextThreeCups.contains(preferredDestination)) {
                        preferredDestination--;
                    }
                    if (preferredDestination < game.minCup()) {
                        preferredDestination = game.maxCup();
                    }

                    var preferredDestinationIndex = game.getIndexByUniqueValue(preferredDestination);

                    game.insertAtIndex(nextThreeCups, preferredDestinationIndex);
                    game.setIndex(game.getIndexByUniqueValue(elementAtNextIndex));
                });

        return game.getScore();
    }

    @Override
    public Long solvePart2() {
        var iterations = 10_000_000;
        var game = this.context2;
        IntStream.range(0, iterations)
                .boxed()
                .forEach(i -> {
                    if (i % 100 == 0) {
                        System.out.println("Index: " + i);
                    }
                    var currentCup = game.currentCup();
                    var elementAtNextIndex = game.getValueAtOffset(3 + 1);
                    var nextThreeCups = game.removeCups(3);
                    var preferredDestination = currentCup - 1;
                    while (nextThreeCups.contains(preferredDestination)) {
                        preferredDestination--;
                    }
                    if (preferredDestination < game.minCup()) {
                        preferredDestination = game.maxCup();
                    }

                    var preferredDestinationIndex = game.getIndexByUniqueValue(preferredDestination);

                    game.insertAtIndex(nextThreeCups, preferredDestinationIndex);
                    game.setIndex(game.getIndexByUniqueValue(elementAtNextIndex));
                });

        return 0L;
    }
}
