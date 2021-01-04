package day20;

import common.Pair2;
import common.Solution;
import day20.model.Match;
import day20.model.Side;
import day20.model.Tile;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SolutionImpl extends Solution<Map<Integer, Tile>, Map<Integer, Tile>, Long, Long> {

    public SolutionImpl(Map<Integer, Tile> context) {
        super(context, context);
    }

    @Override
    public Long solvePart1() {
        var matches = this.context1
                .values()
                .stream()
                .flatMap(tile -> this.context1.values().stream().flatMap(tile::getMatches));

        var corners = this.getCorners(matches, Set.of(Side.BOTTOM, Side.LEFT));
        return corners.mapToLong(Integer::longValue).reduce((x1, x2) -> x1 * x2).orElse(0L);
    }

    @Override
    public Long solvePart2() {
        // TODO
        return null;
    }

    // This method is based on the fact that corners only have 2 neighbours and the square can be rotated, so it is enough to get all matches
    // on a certain pair of sides (ex. BOTTOM and RIGHT) and this will give us all 4 corners because they will all match.
    private Stream<Integer> getCorners(Stream<Match> matches, Set<Side> neighboursSide) {
        var eligibleNeighbours = matches
                .collect(Collectors.groupingBy(m -> new Pair2<>(m.source().id(), m.sourcePosition()), toList()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().stream().map(Match::sourceSide).collect(Collectors.toSet()).equals(neighboursSide))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return eligibleNeighbours.keySet().stream().map(Pair2::left).distinct();
    }
}
