package day22;

import common.Solution;
import day22.model.Game;

public class SolutionImpl extends Solution<Game, Game, Long, Long> {

    public SolutionImpl(Game context1, Game context2) {
        super(context1, context2);
    }

    @Override
    public Long solvePart1() {
        var winner = context1.playCombat();
        return winner.score();
    }

    @Override
    public Long solvePart2() {
        var winner = context2.playRecursiveCombat();
        return winner.score();
    }
}
