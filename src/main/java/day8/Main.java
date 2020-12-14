package day8;

import common.BaseMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Main extends BaseMain {

    public static void main(String[] args) throws URISyntaxException {
        var program = new Main();
        var instructions1 = program.readInput("day8/input.txt", program::readExpressions);
        System.out.println(instructions1);

        var accValuePart1 = program.findAccumulatorValuePart1(instructions1, program.new ExpressionVisitor());
        System.out.println(accValuePart1);

        var accValuePart2 = program.findAccumulatorValuePart2(instructions1.size());
        System.out.println(accValuePart2);
    }

    private List<Expression> readExpressions(URI filePath) {
        var expressions = new ArrayList<Expression>();
        try (var sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                var group = sc.nextLine();
                var components = group.split(" ");
                var operation = components[0];
                var argument = components[1];
                var sign = argument.charAt(0);
                var number = Integer.parseInt(argument.substring(1));
                var offset = (sign == '+') ? number : number * -1;
                Expression expression = switch (operation) {
                    case "acc" -> new Accumulation(offset);
                    case "jmp" -> new Jump(offset);
                    case "nop" -> new NoOp(offset);
                    default -> throw new IllegalArgumentException("UnexpectedException");
                };

                expressions.add(expression);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return expressions;
    }

    private int findAccumulatorValuePart1(List<Expression> expressions, ExpressionVisitor visitor) {
        var finished = false;
        while (!finished) {
            var currentIndex = visitor.currentIndex;
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

        return visitor.accumulator;
    }

    private int findAccumulatorValuePart2(int expressionCount) throws URISyntaxException {
        for (int i = 0; i < expressionCount; i++) {
            var expressions = this.readInput("day8/input.txt", this::readExpressions);
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
                var accValue = findAccumulatorValuePart1(expressions, visitor);
                if (visitor.currentIndex > expressions.size() - 1) {
                    return accValue;
                }
            }
        }

        throw new IllegalStateException("Could not find corresponding value");
    }

    private interface Expression {

        void accept(ExpressionVisitor visitor);

        boolean visited();

        int getArgument();
    }

    private class ExpressionVisitor {

        private int accumulator;

        private int currentIndex;

        public void visit(Accumulation accExpr) {
            var arg = accExpr.argument;
            accumulator += arg;
            this.currentIndex++;
        }

        public void visit(Jump jumpExpr) {
            var arg = jumpExpr.argument;
            currentIndex += arg;
        }

        public void visit(NoOp noOp) {
            this.currentIndex++;
        }
    }

    private class Accumulation implements Expression {

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
        public String toString() {
            return "Accumulation{" +
                    "argument=" + argument +
                    '}';
        }
    }

    private class Jump implements Expression {

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
        public String toString() {
            return "Jump{" +
                    "argument=" + argument +
                    '}';
        }
    }

    private class NoOp implements Expression {

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
        public String toString() {
            return "NoOp{}";
        }
    }
}
