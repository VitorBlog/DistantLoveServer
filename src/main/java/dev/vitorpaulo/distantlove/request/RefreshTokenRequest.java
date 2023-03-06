package dev.vitorpaulo.distantlove.request;

import java.io.Serializable;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest implements Serializable {

    @Size(min = 14, max = 16)
    private String refreshToken;
}
