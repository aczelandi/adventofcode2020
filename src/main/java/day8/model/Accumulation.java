package day8.model;

import day8.Expression;
import day8.ExpressionVisitor;

public class Accumulation implements Expression {

    private final int argument;

    private boolean visited;

    public Accumulation(int argument) {
        this.argument = argument;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
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
        return new Accumulation(this.argument);
    }

    @Override
    public String toString() {
        return "Accumulation{" +
                "argument=" + argument +
                '}';
    }
}