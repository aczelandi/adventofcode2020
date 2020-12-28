package day16.model;

import java.util.Map;

public record Ticket(Map<String, Integer>assignment) {

    public Long getDepartureProperties() {
        return this.assignment
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith("departure"))
                .map(entry -> (long)(entry.getValue()))
                .reduce((v1, v2) -> v1 * v2)
                .orElseThrow();
    }
}
