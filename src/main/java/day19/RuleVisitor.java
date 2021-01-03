package day19;

import day19.model.DelegatingRule;
import day19.model.Rule;
import day19.model.TerminalRule;

import java.util.Map;
import java.util.stream.Stream;

public class RuleVisitor {

    private final Map<Integer, Rule> rulesById;

    public RuleVisitor(Map<Integer, Rule> rulesById) {
        this.rulesById = rulesById;
    }

    public Stream<String> visit(DelegatingRule rule, String message) {
        if (message.isEmpty()) {
            return Stream.empty();
        }

        var alternatives = rule.dependencies()
                .stream()
                .map(rulIds -> rulIds.stream().map(rulesById::get));

        return alternatives.flatMap(
                ruleBucket -> ruleBucket
                        .reduce(Stream.of(message),
                                (acc, r) -> acc.flatMap(msg -> r.accept(this, msg)),
                                (prevAcc, currentAcc) -> currentAcc)
        );
    }

    public Stream<String> visit(TerminalRule rule, String message) {
        if (message.isEmpty()) {
            return Stream.empty();
        }

        var matches = message.startsWith(rule.value());
        return matches ? Stream.of(message.substring(rule.value().length())) : Stream.empty();
    }
}
