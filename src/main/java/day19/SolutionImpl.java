package day19;

import common.Solution;
import day19.model.InputData;
import day19.model.Rule;

import java.util.List;
import java.util.Map;

public class SolutionImpl extends Solution<InputData, InputData, Long, Long> {

    public SolutionImpl(InputData context1, InputData context2) {
        super(context1, context2);
    }

    @Override
    public Long solvePart1() {
        var rules = this.context1.rules();
        var messages = this.context1.messages();
        return findMatchingMessageCount(rules, messages);
    }

    @Override
    public Long solvePart2() {
        var rules = this.context2.rules();
        var messages = this.context2.messages();
        return findMatchingMessageCount(rules, messages);
    }

    private Long findMatchingMessageCount(Map<Integer, Rule> rules, List<String> messages) {
        var ruleToMatch = rules.get(0);
        var ruleVisitor = new RuleVisitor(rules);

        return messages.stream()
                .filter(msg -> ruleToMatch.accept(ruleVisitor, msg)
                        .anyMatch(String::isEmpty))
                .count();
    }
}
