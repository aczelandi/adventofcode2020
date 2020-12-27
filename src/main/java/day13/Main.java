package day13;

import common.BaseMain;
import common.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var preferredTimestamp = program.readInput("day13/input.txt", program::readPreferredTimestamp);
        var busIdByIndex = program.readInput("day13/input.txt", program::readInput);

        System.out.println(preferredTimestamp);
        System.out.println(busIdByIndex);

        var busIds = busIdByIndex.values();
        var schedule = program.generateSchedule(busIds, preferredTimestamp);
        System.out.println(schedule);

        var correctBusWithWaitingTime = program.findCorrectBusWithWaitingTime(schedule, preferredTimestamp);
        System.out.println(correctBusWithWaitingTime.left() * correctBusWithWaitingTime.right());

        var matchingTimestamp = program.findMatchingTimestamp(busIdByIndex);
        System.out.println(matchingTimestamp);
    }

    private Long readPreferredTimestamp(URI filePath) {
        var preferredTimestamp = 0L;
        try (var scanner = new Scanner(new File(filePath))) {
            preferredTimestamp = scanner.nextLong();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return preferredTimestamp;
    }

    private Map<Integer, Long> readInput(URI filePath) {
        Map<Integer, Long> busIdByIndex = null;
        try (var scanner = new Scanner(new File(filePath))) {
            // skip first line
            scanner.nextLine();
            String[] buses = scanner.nextLine().split(",");
            busIdByIndex = IntStream.range(0, buses.length)
                    .boxed()
                    .filter(id -> !buses[id].equals("x"))
                    .collect(Collectors.toMap(k -> k, v -> Long.parseLong(buses[v])));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return busIdByIndex;
    }

    private Pair<Long> findCorrectBusWithWaitingTime(List<Pair<Long>> schedule, long preferredTimestamp) {
        var timestamps = schedule.stream()
                .map(Pair::right)
                .sorted()
                .collect(Collectors.toList());

        var indexOfTimestamp = this.findIndexInSortedList(timestamps, preferredTimestamp);
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

    private int findIndexInSortedList(List<Long> list, long number) {
        return IntStream.range(0, list.size() - 1)
                .boxed()
                .filter(index -> list.get(index) <= number && list.get(index + 1) >= number)
                .map(index -> index + 1)
                .findFirst()
                .orElseThrow();
    }

    private long findMatchingTimestamp(Map<Integer, Long> busIdByIndex) {
        var increment = 1L;
        var timestamp = 0L;
        final List<Integer> busIndices = new ArrayList<>(busIdByIndex.keySet());
        for (int i = 0; i < busIndices.size() - 1; i++) {
            var prevBusIndex = busIndices.get(i);
            var busIndex = busIndices.get(i + 1);
            var busId = busIdByIndex.get(busIndex);
            increment *= busIdByIndex.get(prevBusIndex);
            var trials = 0L;
            while ((timestamp + busIndex) % busId != 0) {
                timestamp += increment;
                trials++;
            }
            System.out.println(Map.of("busId", busId, "busIndex", busIndex, "timestamp", timestamp, "increment", increment, "trials", trials));
        }

        return timestamp;
    }
}
