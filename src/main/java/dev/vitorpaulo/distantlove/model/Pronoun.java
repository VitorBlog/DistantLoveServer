package dev.vitorpaulo.distantlove.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Pronoun {

    MASCULINE("o", "ele"),
    FEMININE("a", "ela"),
    NEUTRAL("x", "elx");

    private final String article;
    private final String plural;
}
