package day20.model;

import java.util.Map;

public enum Side {
    TOP,
    RIGHT,
    BOTTOM,
    LEFT;

    public final static Map<Side, Side> OPPOSITE = Map.of(TOP, BOTTOM, RIGHT, LEFT, LEFT, RIGHT, BOTTOM, TOP);
}
