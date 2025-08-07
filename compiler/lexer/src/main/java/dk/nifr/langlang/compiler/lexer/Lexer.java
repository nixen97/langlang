package dk.nifr.langlang.compiler.lexer;

import java.util.ArrayList;
import java.util.List;

public final class Lexer {
    public static List<Token> lex(String input) {
        final var state = new LexerState(input);
        final List<Token> tokens = new ArrayList<>();

        while (state.hasNext()) {
            trim(state);
            if (!state.hasNext()) break;
            tokens.add(getNextToken(state));
        }
        tokens.add(new Token("", TokenKind.TOKEN_EOF, state.currentPosition()));
        return tokens;
    }

    private static Token getNextToken(final LexerState state) {
        final var position = state.currentPosition();

        // EOF
        if (!state.hasNext()) return new Token("", TokenKind.TOKEN_EOF, position);

        final var symbol = symbol(state);
        if (symbol != null) return symbol;

        // Literals
        for (final var kind : TokenKind.LITERALS)
            if (state.consumePrefixIfExists(kind.getLiteralString()))
                return new Token(kind.getLiteralString(), kind, position);

        // If we don't know how to handle this token
        return new Token(state.next(), TokenKind.TOKEN_UNKNOWN, position);
    }

    private static void trim(LexerState state) {
        while (state.hasNext() && Character.isWhitespace(state.peek())) state.next();
    }

    private static Token symbol(LexerState state) {
        if (!Character.isLetter(state.peek())) return null;

        final var position = state.currentPosition();
        final var sb = new StringBuilder();
        while(state.hasNext() && (Character.isLetterOrDigit(state.peek()) || state.peek() == '_')) {
            sb.append(state.next());
        }

        return new Token(sb.toString(), TokenKind.TOKEN_SYMBOL, position);
    }
}
