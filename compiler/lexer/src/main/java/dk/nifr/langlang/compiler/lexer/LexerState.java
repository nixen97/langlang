package dk.nifr.langlang.compiler.lexer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;

/**
 * Only works with unix style \n line endings.
 * If input has windows style line endings they need to be converted before getting here.
 */
@RequiredArgsConstructor
public class LexerState implements Iterator<Character> {
    private final String input;
    private int current = 0;
    private int line = 0;
    private int bol = 0;

    public Token.Position currentPosition() {
        return new Token.Position(line, current - bol);
    }

    public Character peek() {
        assertBounds();
        return input.charAt(current);
    }

    @Override
    public boolean hasNext() {
        return current < input.length();
    }

    @Override
    public Character next() {
        assertBounds();
        final var nextChar = input.charAt(current++);

        if (nextChar == '\n') {
            line++;
            bol = current;
        }

        return nextChar;
    }

    public boolean consumePrefixIfExists(final @NonNull String prefix) {
        if (!hasPrefix(prefix)) return false;

        current += prefix.length();
        return true;
    }

    public boolean hasPrefix(final @NonNull String prefix) {
        final var prefixLength = prefix.length();
        if (prefixLength == 0) return true;
        if (current + prefixLength > input.length()) return false;

        for  (int offset = 0; offset < prefixLength; offset++)
            if (input.charAt(current + offset) != prefix.charAt(offset))
                return false;

        return true;
    }

    private void assertBounds() {
        if (!hasNext()) throw new IndexOutOfBoundsException("LexerInput has already been consumed");
    }
}
