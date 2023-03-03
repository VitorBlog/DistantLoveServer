package dev.vitorpaulo.distantlove.response;

import java.io.Serializable;
import java.util.Date;

import dev.vitorpaulo.distantlove.model.Pronoun;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthUserResponse implements Serializable {

    private final String token;
    private final String refreshToken;
}
