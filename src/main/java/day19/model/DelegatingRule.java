package day19.model;

import day19.RuleVisitor;

import java.util.List;
import java.util.stream.Stream;

public record DelegatingRule(int id, List<List<Integer>>dependencies) implements Rule {

    @Override
    public Stream<String> accept(RuleVisitor visitor, String message) {
        return visitor.visit(this, message);
    }
}
