package day08;

import day08.model.Accumulation;
import day08.model.Jump;
import day08.model.NoOp;

public class ExpressionVisitor {

    private int accumulator;

    private int currentIndex;

    public void visit(Accumulation accExpr) {
        var arg = accExpr.getArgument();
        accumulator += arg;
        this.currentIndex++;
    }

    public void visit(Jump jumpExpr) {
        var arg = jumpExpr.getArgument();
        currentIndex += arg;
    }

    public void visit(NoOp noOp) {
        this.currentIndex++;
    }

    public int getAccumulator() {
        return accumulator;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
