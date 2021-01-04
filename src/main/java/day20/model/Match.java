package day20.model;

public record Match(
        Tile source, Side sourceSide, Position sourcePosition,
        Tile target, Side targetSide, Position targetPosition) {
}
