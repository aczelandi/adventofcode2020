package day08.model;

import day08.Expression;
import day08.ExpressionVisitor;

public class Jump implements Expression {

    private final int argument;

    private boolean visited;

    public Jump(int argument) {
        this.argument = argument;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
        this.visited = true;
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
        return new Jump(this.argument);
    }

    @Override
    public String toString() {
        return "Jump{" +
                "argument=" + argument +
                '}';
    }
}
