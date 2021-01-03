package day19;

import common.BaseInputReader;
import day19.model.DelegatingRule;
import day19.model.Rule;
import day19.model.TerminalRule;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

class InputRulesReader extends BaseInputReader<Map<Integer, Rule>> {

    @Override
    protected Map<Integer, Rule> read(URI filePath) throws FileNotFoundException {
        Map<Integer, Rule> rulesById = new HashMap<>();

        try (var scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                var ruleStr = scanner.nextLine();
                if (ruleStr.isEmpty()) {
                    break;
                }

                var idAdRules = ruleStr.split(":");
                var id = Integer.parseInt(idAdRules[0]);
                var ruleComponents = idAdRules[1].trim().split("\\|");
                var isCombinedRule = ruleComponents.length > 1;
                Rule rule;
                if (isCombinedRule) {
                    var rules = this.parseComponent(ruleComponents[0]);
                    var alternativeRules = this.parseComponent(ruleComponents[1]);
                    rule = new DelegatingRule(id, List.of(rules, alternativeRules));
                } else {
                    var isTerminal = ruleComponents.length == 1 && ruleComponents[0].startsWith("\"");
                    if (isTerminal) {
                        rule = new TerminalRule(id, ruleComponents[0].substring(1, ruleComponents[0].length() - 1));
                    } else {
                        var rules = this.parseComponent(ruleComponents[0]);
                        rule = new DelegatingRule(id, List.of(rules));
                    }
                }
                rulesById.put(id, rule);
            }
        }

        return rulesById;
    }

    private List<Integer> parseComponent(String component) {
        var elements = component.trim().split(" ");

        return Arrays.stream(elements)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
