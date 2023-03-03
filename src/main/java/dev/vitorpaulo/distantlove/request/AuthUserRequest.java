package dev.vitorpaulo.distantlove.request;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUserRequest implements Serializable {

    @Email
    private final String email;

    @Size(min = 8, max = 40)
    private final String password;
}
