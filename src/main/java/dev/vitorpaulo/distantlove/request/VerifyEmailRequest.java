package dev.vitorpaulo.distantlove.request;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailRequest implements Serializable {

    @Email
    private String email;
}
