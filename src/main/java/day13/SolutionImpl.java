package day13;

import common.Pair;
import common.Pair2;
import common.Solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class SolutionImpl extends Solution<Pair2<Long, Map<Integer, Long>>, Pair2<Long, Map<Integer, Long>>, Long, Long> {

    public SolutionImpl(Pair2<Long, Map<Integer, Long>> context) {
        super(context, context);
    }

    @Override
    public Long solvePart1() {
        var busIds = this.context1.right().values();
        var preferredTimestamp = this.context1.left();
        var schedule = this.generateSchedule(busIds, preferredTimestamp);
        var correctBusWithWaitingTime = this.findCorrectBusWithWaitingTime(schedule, preferredTimestamp);
        return correctBusWithWaitingTime.left() * correctBusWithWaitingTime.right();
    }

    @Override
    public Long solvePart2() {
        var busIdByIndex = this.context2.right();
        var increment = 1L;
        var timestamp = 0L;
        final List<Integer> busIndices = new ArrayList<>(busIdByIndex.keySet());
        for (int i = 0; i < busIndices.size() - 1; i++) {
            var prevBusIndex = busIndices.get(i);
            var busIndex = busIndices.get(i + 1);
            var busId = busIdByIndex.get(busIndex);
            increment *= busIdByIndex.get(prevBusIndex);
            while ((timestamp + busIndex) % busId != 0) {
                timestamp += increment;
            }
        }

        return timestamp;
    }

    private Pair<Long> findCorrectBusWithWaitingTime(List<Pair<Long>> schedule, long preferredTimestamp) {
        var timestamps = schedule.stream()
                .map(Pair::right)
                .sorted()
                .collect(Collectors.toList());

        var indexOfTimestamp = ListUtils.findIndexInSortedList(timestamps, preferredTimestamp);
        var busWithArrivalTime = schedule.stream()
                .filter(pair -> pair.right().equals(timestamps.get(indexOfTimestamp)))
                .findFirst()
                .orElseThrow();

        return new Pair<>(busWithArrivalTime.left(), busWithArrivalTime.right() - preferredTimestamp);
    }

    private List<Pair<Long>> generateSchedule(Collection<Long> busIds, long untilTimestamp) {
        var schedule = new ArrayList<Pair<Long>>();
        for (Long id : busIds) {
            var acc = 0L;
            var startingAt = untilTimestamp / id;
            do {
                acc += id;
                if (acc >= startingAt) {
                    schedule.add(new Pair<>(id, acc));
                }
            } while (acc < untilTimestamp);
        }

        return schedule;
    }
}
