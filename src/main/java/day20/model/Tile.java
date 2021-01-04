package day20.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

public record Tile(int id, String top, String right, String bottom, String left) {

    public String getSide(Side side) {
        return switch (side) {
            case TOP:
                yield this.top;
            case RIGHT:
                yield this.right;
            case BOTTOM:
                yield this.bottom;
            case LEFT:
                yield this.left;
        };
    }

    public Stream<Match> getMatches(Tile otherTile) {
        if (this == otherTile) {
            return Stream.empty();
        }
        var alterations = this.getAllAlterations();
        var otherAlterations = otherTile.getAllAlterations();

        return otherAlterations.entrySet().stream().flatMap(otherAlteration -> alterations.entrySet()
                .stream()
                .flatMap(alteration -> Arrays.stream(Side.values())
                        .filter(side -> alteration.getValue().getSide(side).equals(otherAlteration.getValue().getSide(Side.OPPOSITE.get(side))))
                        .map(side ->
                                new Match(
                                        alteration.getValue(),
                                        side,
                                        alteration.getKey(),
                                        otherAlteration.getValue(),
                                        Side.OPPOSITE.get(side),
                                        otherAlteration.getKey()
                                ))));
    }

    private Map<Position, Tile> getAllAlterations() {
        var original = this;
        var reversedTop = new StringBuilder(top).reverse().toString();
        var reversedRight = new StringBuilder(right).reverse().toString();
        var reversedBottom = new StringBuilder(bottom).reverse().toString();
        var reversedLeft = new StringBuilder(left).reverse().toString();

        var flippedHorizontal = new Tile(this.id, bottom, reversedRight, top, reversedLeft);
        var flippedVertical = new Tile(this.id, reversedTop, left, reversedBottom, right);

        var flipDiagonalTopLeft = new Tile(this.id, left, bottom, right, top);
        var flipDiagonalTopRight = new Tile(this.id, reversedRight, reversedTop, reversedLeft, reversedBottom);

        var rotate90Clock = new Tile(this.id, reversedLeft, top, reversedRight, bottom);
        var rotate180Clock = new Tile(this.id, reversedBottom, reversedLeft, reversedTop, reversedRight);
        var rotate270Clock = new Tile(this.id, right, reversedBottom, left, reversedTop);

        return Map.of(
                Position.ORIGINAL, original,
                Position.FLIPPED_HORIZONTAL, flippedHorizontal,
                Position.FLIPPED_VERTICAL, flippedVertical,
                Position.FLIPPED_DIAGONAL_TOP_LEFT, flipDiagonalTopLeft,
                Position.FLIPPED_DIAGONAL_TOP_RIGHT, flipDiagonalTopRight,
                Position.ROTATE_90_CLOCK, rotate90Clock,
                Position.ROTATE_180_CLOCK, rotate180Clock,
                Position.ROTATE_270_CLOCK, rotate270Clock
        );
    }
}
