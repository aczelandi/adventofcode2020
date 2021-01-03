package day19.model;

import day19.RuleVisitor;

import java.util.stream.Stream;

public interface Rule {

    Stream<String> accept(RuleVisitor visitor, String message);
}
