package day8;

public interface Expression {

    void accept(ExpressionVisitor visitor);

    boolean visited();

    int getArgument();

    Expression copy();
}
