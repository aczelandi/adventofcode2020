package day19.model;

import day19.RuleVisitor;

import java.util.stream.Stream;

public record TerminalRule(int id, String value) implements Rule {

    @Override
    public Stream<String> accept(RuleVisitor visitor, String message) {
        return visitor.visit(this, message);
    }
}
