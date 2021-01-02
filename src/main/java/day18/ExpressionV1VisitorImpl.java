package day18;

import day18.ExpressionV1Parser.ExpressionContext;

public class ExpressionV1VisitorImpl extends ExpressionV1BaseVisitor<Long> {

    @Override
    public Long visitExpression(ExpressionContext ctx) {
        if (ctx.atom() != null) { // it is an atom
            return visit(ctx.atom());
        }

        if (ctx.parens() != null) { // it is a parenthesis operation
            return visit(ctx.parens());
        }

        // it is an expression

        var left = visit(ctx.expression(0));
        var right = visit(ctx.expression(1));

        if (ctx.op().start.getType() == ExpressionV1Parser.PLUS) {
            return left + right;
        }
        if (ctx.op().start.getType() == ExpressionV1Parser.TIMES) {
            return left * right;
        }

        throw new IllegalStateException("Could not evaluate expression " + ctx);
    }

    @Override
    public Long visitParens(ExpressionV1Parser.ParensContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Long visitAtom(ExpressionV1Parser.AtomContext ctx) {
        return Long.parseLong(ctx.SCIENTIFIC_NUMBER().getSymbol().getText());
    }
}
