package dev.vitorpaulo.distantlove.request;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyEmailRequest implements Serializable {

    @Email
    private final String email;
}
