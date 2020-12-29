package day12.model;

public record Position(Direction direction, double horizontalCord, double verticalCord) {

    public Position next(Action action) {
        return action.direction().applyTransitionV1(action, this);
    }

    public Position next(Action action, Position relativeTo) {
        return action.direction().applyTransitionV2(action, this, relativeTo);
    }
}
