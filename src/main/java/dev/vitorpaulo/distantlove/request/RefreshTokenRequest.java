package dev.vitorpaulo.distantlove.request;

import java.io.Serializable;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenRequest implements Serializable {

    @Size(min = 14, max = 16)
    private final String refreshToken;
}
