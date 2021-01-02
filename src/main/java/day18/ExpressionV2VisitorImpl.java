package day18;

import day18.ExpressionV2Parser.AtomContext;
import day18.ExpressionV2Parser.ExpressionContext;
import day18.ExpressionV2Parser.ParensContext;

public class ExpressionV2VisitorImpl extends ExpressionV2BaseVisitor<Long> {

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

        if (ctx.PLUS() != null) {
            return left + right;
        }
        if (ctx.TIMES() != null) {
            return left * right;
        }

        throw new IllegalStateException("Could not evaluate expression " + ctx);
    }

    @Override
    public Long visitParens(ParensContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Long visitAtom(AtomContext ctx) {
        return Long.parseLong(ctx.SCIENTIFIC_NUMBER().getSymbol().getText());
    }
}
