package day18;

import day18.ExpressionV2Parser.ExpressionContext;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.function.Function;

public class ExpressionV2StringParser implements Function<String, ExpressionContext> {

    @Override
    public ExpressionContext apply(String expressionStr) {
        var lexer = new ExpressionV2Lexer(CharStreams.fromString(expressionStr));
        var tokens = new CommonTokenStream(lexer);
        var parser = new ExpressionV2Parser(tokens);
        return parser.expression();
    }
}
