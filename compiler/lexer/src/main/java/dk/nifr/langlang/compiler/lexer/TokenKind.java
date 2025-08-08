package dk.nifr.langlang.compiler.lexer;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum TokenKind {
    TOKEN_EOF,
    TOKEN_SYMBOL,
    TOKEN_NUMBER,
    TOKEN_UNKNOWN,

    // Literals
    TOKEN_DOT("."),
    TOKEN_OPEN_PAREN("("),
    TOKEN_CLOSE_PAREN(")"),
    TOKEN_OPEN_CURLY("{"),
    TOKEN_CLOSE_CURLY("}"),
    TOKEN_SEMICOLON(";"),
    TOKEN_EQUALS("="),
    TOKEN_PLUS("+"),
    TOKEN_MINUS("-"),
    TOKEN_START("*"),
    TOKEN_SLASH("/"),
    ;
    private final String literalString;

    TokenKind() {
        this(null);
    }

    TokenKind(String literalString) {
        this.literalString = literalString;
    }

    public static final Set<TokenKind> LITERALS = Arrays.stream(TokenKind.values())
            .filter(t -> t.literalString != null)
            .collect(Collectors.toUnmodifiableSet());
}
