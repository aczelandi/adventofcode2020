package day05;

import common.Solution;
import day05.model.SeatInfo;

import java.util.List;
import java.util.stream.Collectors;

class SolutionImpl extends Solution<List<SeatInfo>, List<SeatInfo>, Integer, Integer> {

    public SolutionImpl(List<SeatInfo> context) {
        super(context, context);
    }

    @Override
    public Integer solvePart1() {
        return this.context1
                .stream()
                .map(SeatInfo::getSeatId)
                .max(Integer::compare)
                .orElse(0);
    }

    @Override
    public Integer solvePart2() {
        var sortedById = this.context2
                .stream()
                .filter(x -> !x.inFirstRow() && !x.inLastRow())
                .map(SeatInfo::getSeatId)
                .sorted()
                .collect(Collectors.toList());

        var mySeat = 0;
        var prev = sortedById.get(0);
        for (int i = 1; i < sortedById.size(); i++) {
            var current = sortedById.get(i);
            if ((current - prev) == 2) {
                mySeat = current - 1;
                break;
            } else {
                prev = current;
            }
        }

        return mySeat;
    }
}
