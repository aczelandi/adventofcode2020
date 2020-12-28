package day16;

import common.Solution;
import day16.model.IndexLabelMapping;
import day16.model.TicketInfo;
import day16.model.TicketRules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class SolutionImpl extends Solution<TicketRules, Integer, Long> {

    public SolutionImpl(TicketRules input) {
        super(input);
    }

    @Override
    public Integer solvePart1() {
        var nearbyTickets = this.input.nearbyCodedTickets();
        var allIntervals = this.input.getAllIntervalsFlat();

        var missingParameters = nearbyTickets.stream()
                .flatMap(ticket -> ticket.getInValidParams(allIntervals).stream())
                .collect(Collectors.toList());

        return missingParameters.stream().reduce(Integer::sum).orElse(0);
    }

    @Override
    public Long solvePart2() {
        var allIntervals = this.input.getAllIntervalsFlat();
        var nearbyTicketValidTickets = this.input.getValidNearbyTickets(allIntervals);
        var allTickets = Stream.concat(nearbyTicketValidTickets.stream(), Stream.of(this.input.currentCodedTicket())).collect(Collectors.toList());
        var labelsTotal = this.input.ticketInfos().size();

        var indexToAllPossibleLabels = IntStream.range(0, labelsTotal)
                .boxed()
                .map(index -> {
                    var paramsAtIndex = allTickets
                            .stream()
                            .map(ticket -> ticket.parameters().get(index))
                            .collect(Collectors.toList());

                    var labels = this.input
                            .ticketInfos()
                            .stream()
                            .filter(info -> paramsAtIndex.stream().allMatch(p -> info.matchesTicketValue(p)))
                            .map(TicketInfo::label)
                            .collect(Collectors.toList());

                    return Map.entry(index, labels);
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        var indexToLabel = this.reduceToOneValuePerIndex(indexToAllPossibleLabels);
        var myTicket = new TicketDecoder().apply(this.input.currentCodedTicket(), indexToLabel);

        return myTicket.getDepartureProperties();
    }

    private IndexLabelMapping reduceToOneValuePerIndex(Map<Integer, List<String>> indexToPossibleLabels) {
        var labelByIndex = new HashMap<Integer, String>();

        while (labelByIndex.size() != indexToPossibleLabels.size()) {
            var labelsWithIndexesThatMatchOnlyOneIndex = indexToPossibleLabels
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().size() == 1)
                    .map(entry -> Map.entry(entry.getKey(), entry.getValue().get(0)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            var labelsThatMatchOnlyOneIndex = labelsWithIndexesThatMatchOnlyOneIndex.values();

            indexToPossibleLabels.forEach((key, value) -> {
                if (!labelsWithIndexesThatMatchOnlyOneIndex.containsKey(key)) {
                    value.removeAll(labelsThatMatchOnlyOneIndex);
                }
            });

            labelsWithIndexesThatMatchOnlyOneIndex
                    .forEach(labelByIndex::put);
        }

        return new IndexLabelMapping(labelByIndex);
    }
}