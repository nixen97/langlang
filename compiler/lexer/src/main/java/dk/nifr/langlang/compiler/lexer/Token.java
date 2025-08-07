package dk.nifr.langlang.compiler.lexer;

import lombok.NonNull;

public record Token(
        String value,
        TokenKind kind,
        Position position
) {

    public Token(Character character, TokenKind kind, Position position) {
        this(character.toString(), kind, position);
    }

    @Override
    public @NonNull String toString() {
        return "'%s' (%s) at %s".formatted(value, kind, position);
    }

    public record Position(
            int line, int col
    ) {
        @Override
        public @NonNull String toString() {
            return "(%d, %d)".formatted(line, col);
        }
    }
}
