package dk.nifr.langlang.compiler.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Lexer {
    private static final Set<Character> VALID_HEX_LETTERS = Set.of(
            'a', 'b', 'c', 'd', 'e', 'f',
            'A', 'B', 'C', 'D', 'E', 'F'
    );

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

        final var number = number(state);
        if (number != null) return number;

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

    private static Token number(LexerState state) {
        if (!Character.isDigit(state.peek())) return null;

        final var position = state.currentPosition();
        final var sb = new StringBuilder();
        sb.append(state.next());

        if (state.hasNext() && state.peek() == 'x') {
            // Hex
            do sb.append(state.next());
            while (state.hasNext() && (Character.isDigit(state.peek()) || VALID_HEX_LETTERS.contains(state.peek())));

            return new Token(sb.toString(), TokenKind.TOKEN_NUMBER, position);
        }

        boolean dotEncountered = false;
        while (state.hasNext() && (Character.isDigit(state.peek()) || state.peek() == '.' || state.peek() == 'f' || state.peek() == 'd')) {
            if (state.peek() == '.') {
                // We've encountered a dot for the second time, which is not allowed.
                if (dotEncountered)
                    return new Token(sb.toString(), TokenKind.TOKEN_NUMBER, position);

                sb.append(state.next());
                dotEncountered = true;
                continue;
            }

            final var c = state.next();

            // float terminator: we're done
            if (c == 'f' || c == 'd') return new Token(sb.append(c).toString(), TokenKind.TOKEN_NUMBER, position);

            sb.append(c);
        }
        return new Token(sb.toString(), TokenKind.TOKEN_NUMBER, position);
    }
}
