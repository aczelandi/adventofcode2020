package day16.model;

import java.util.Map;

public record IndexLabelMapping(Map<Integer, String>mapping) {

    public String getLabelForIndex(int index) {
        return mapping.get(index);
    }
}
