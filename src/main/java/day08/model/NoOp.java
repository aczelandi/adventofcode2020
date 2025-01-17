package day08.model;

import day08.Expression;
import day08.ExpressionVisitor;

public class NoOp implements Expression {

    private boolean visited;

    private final int argument;

    public NoOp(int argument) {
        this.argument = argument;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        // no-op
        visitor.visit(this);
        visited = true;
    }

    @Override
    public boolean visited() {
        return this.visited;
    }

    @Override
    public int getArgument() {
        return this.argument;
    }

    @Override
    public Expression copy() {
        return new NoOp(this.argument);
    }

    @Override
    public String toString() {
        return "NoOp{}";
    }
}
