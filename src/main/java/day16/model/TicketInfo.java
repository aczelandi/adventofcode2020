package day16.model;

import java.util.List;

public record TicketInfo(String label, List<Interval>intervals) {

    public boolean matchesTicketValue(int value) {
        return this.intervals.stream()
                .anyMatch(interval -> interval.contains(value));

    }
}
