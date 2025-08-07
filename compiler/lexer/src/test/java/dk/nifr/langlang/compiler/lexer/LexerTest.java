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
                    new Token("", TOKEN_EOF, new Token.Position(0, 41))
            ))
    );

    @FieldSource
    @ParameterizedTest
    void lex_lexes(final String input, List<Token> expectedOutput) {

        assertThat(lex(input))
                .containsExactlyElementsOf(expectedOutput);

    }
}