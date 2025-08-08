package dk.nifr.langlang.compiler.lexer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.List;

import static dk.nifr.langlang.compiler.lexer.Lexer.lex;
import static dk.nifr.langlang.compiler.lexer.TokenKind.*;
import static org.assertj.core.api.Assertions.assertThat;

class LexerTest {
    private static final List<Arguments> lex_lexes = List.of(
            Arguments.of("final var test = someObject.someMethod();", List.of(
                    new Token("final", TOKEN_SYMBOL, new Token.Position(0, 0)),
                    new Token("var", TOKEN_SYMBOL, new Token.Position(0, 6)),
                    new Token("test", TOKEN_SYMBOL, new Token.Position(0, 10)),
                    new Token("=", TOKEN_EQUALS, new Token.Position(0, 15)),
                    new Token("someObject", TOKEN_SYMBOL, new Token.Position(0, 17)),
                    new Token(".", TOKEN_DOT, new Token.Position(0, 27)),
                    new Token("someMethod", TOKEN_SYMBOL, new Token.Position(0, 28)),
                    new Token("(", TOKEN_OPEN_PAREN, new Token.Position(0, 38)),
                    new Token(")", TOKEN_CLOSE_PAREN, new Token.Position(0, 39)),
                    new Token(";", TOKEN_SEMICOLON, new Token.Position(0, 40)),
                    new Token("", TOKEN_EOF, new Token.Position(0, 41)))),
            Arguments.of("""
                    public class someClass {
                        int someMethodDecl(byte someParameter) {
                            var a = 0xAF;
                            var b = 128;
                            return a + b;
                        }
                    }
                    """, List.of(
                    new Token("public", TOKEN_SYMBOL, new Token.Position(0, 0)),
                    new Token("class", TOKEN_SYMBOL, new Token.Position(0, 7)),
                    new Token("someClass", TOKEN_SYMBOL, new Token.Position(0, 13)),
                    new Token("{", TOKEN_OPEN_CURLY, new Token.Position(0, 23)),
                    new Token("int", TOKEN_SYMBOL, new Token.Position(1, 4)),
                    new Token("someMethodDecl", TOKEN_SYMBOL, new Token.Position(1, 8)),
                    new Token("(", TOKEN_OPEN_PAREN, new Token.Position(1, 22)),
                    new Token("byte", TOKEN_SYMBOL, new Token.Position(1, 23)),
                    new Token("someParameter", TOKEN_SYMBOL, new Token.Position(1, 28)),
                    new Token(")", TOKEN_CLOSE_PAREN, new Token.Position(1, 41)),
                    new Token("{", TOKEN_OPEN_CURLY, new Token.Position(1, 43)),
                    new Token("var", TOKEN_SYMBOL, new Token.Position(2, 8)),
                    new Token("a", TOKEN_SYMBOL, new Token.Position(2, 12)),
                    new Token("=", TOKEN_EQUALS, new Token.Position(2, 14)),
                    new Token("0xAF", TOKEN_NUMBER, new Token.Position(2, 16)),
                    new Token(";", TOKEN_SEMICOLON, new Token.Position(2, 20)),
                    new Token("var", TOKEN_SYMBOL, new Token.Position(3, 8)),
                    new Token("b", TOKEN_SYMBOL, new Token.Position(3, 12)),
                    new Token("=", TOKEN_EQUALS, new Token.Position(3, 14)),
                    new Token("128", TOKEN_NUMBER, new Token.Position(3, 16)),
                    new Token(";", TOKEN_SEMICOLON, new Token.Position(3, 19)),
                    new Token("return", TOKEN_SYMBOL, new Token.Position(4, 8)),
                    new Token("a", TOKEN_SYMBOL, new Token.Position(4, 15)),
                    new Token("+", TOKEN_PLUS, new Token.Position(4, 17)),
                    new Token("b", TOKEN_SYMBOL, new Token.Position(4, 19)),
                    new Token(";", TOKEN_SEMICOLON, new Token.Position(4, 20)),
                    new Token("}", TOKEN_CLOSE_CURLY, new Token.Position(5, 4)),
                    new Token("}", TOKEN_CLOSE_CURLY, new Token.Position(6, 0)),
                    new Token("", TOKEN_EOF, new Token.Position(7, 0))))
    );

    @FieldSource
    @ParameterizedTest
    void lex_lexes(final String input, List<Token> expectedOutput) {

        assertThat(lex(input))
                .containsExactlyElementsOf(expectedOutput);

    }
}