package day08;

import common.Solution;
import day08.model.Jump;
import day08.model.NoOp;

import java.util.List;
import java.util.stream.Collectors;

class SolutionImpl extends Solution<List<Expression>, List<Expression>, Integer, Integer> {

    public SolutionImpl(List<Expression> context) {
        super(context, context);
    }

    @Override
    public Integer solvePart1() {
        return executeExpressions(this.context1, new ExpressionVisitor());
    }

    @Override
    public Integer solvePart2() {
        for (int i = 0; i < this.context2.size(); i++) {
            var expressions = context2.stream().map(Expression::copy).collect(Collectors.toList());
            var switched = false;
            var expression = expressions.get(i);
            if (expression instanceof NoOp) {
                expressions.remove(expression);
                expressions.add(i, new Jump(expression.getArgument()));
                switched = true;
            } else if (expression instanceof Jump) {
                expressions.remove(expression);
                expressions.add(i, new NoOp(0));
                switched = true;
            }

            if (switched) {
                var visitor = new ExpressionVisitor();
                var accValue = executeExpressions(expressions, visitor);
                if (visitor.getCurrentIndex() > expressions.size() - 1) {
                    return accValue;
                }
            }
        }

        throw new IllegalStateException("Could not find corresponding value");
    }

    private Integer executeExpressions(List<Expression> expressions, ExpressionVisitor visitor) {
        var finished = false;
        while (!finished) {
            var currentIndex = visitor.getCurrentIndex();
            if (currentIndex >= expressions.size() - 1) {
                finished = true;
            }
            var expression = expressions.get(currentIndex);
            if (!expression.visited()) {
                expression.accept(visitor);
            } else {
                finished = true;
            }
        }

        return visitor.getAccumulator();
    }
}
