package day18;

import common.Solution;

import java.util.List;

class SolutionImpl extends Solution<List<ExpressionV1Parser.ExpressionContext>, List<ExpressionV2Parser.ExpressionContext>, Long, Long> {

    private final ExpressionV1Visitor<Long> expressionV1Visitor;

    private final ExpressionV2Visitor<Long> expressionV2Visitor;

    public SolutionImpl(List<ExpressionV1Parser.ExpressionContext> context1, List<ExpressionV2Parser.ExpressionContext> context2, ExpressionV1Visitor<Long> expressionV1Visitor, ExpressionV2Visitor<Long> expressionV2Visitor) {
        super(context1, context2);
        this.expressionV1Visitor = expressionV1Visitor;
        this.expressionV2Visitor = expressionV2Visitor;
    }

    @Override
    public Long solvePart1() {
        return this.context1.stream().mapToLong(expressionV1Visitor::visit).sum();
    }

    @Override
    public Long solvePart2() {
        return this.context2.stream().mapToLong(expressionV2Visitor::visit).sum();
    }
}
