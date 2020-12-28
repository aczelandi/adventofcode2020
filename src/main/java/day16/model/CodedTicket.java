package day16.model;

import java.util.List;
import java.util.stream.Collectors;

public record CodedTicket(List<Integer>parameters) {

    public List<Integer> getInValidParams(List<Interval> allIntervals) {
        return this.parameters()
                .stream()
                .filter(p ->
                        allIntervals
                                .stream()
                                .filter(interval -> interval.contains(p))
                                .findAny()
                                .isEmpty())
                .collect(Collectors.toList());
    }

    public boolean isValid(List<Interval> allIntervals) {
        return this.getInValidParams(allIntervals).isEmpty();
    }
}
