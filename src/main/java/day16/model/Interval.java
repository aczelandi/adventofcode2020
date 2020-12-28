package day16.model;

public record Interval(int start, int end) {

    public boolean contains(int value) {
        return value >= start && value <= end;
    }
}
