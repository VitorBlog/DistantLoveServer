package dev.vitorpaulo.distantlove.request;

import java.io.Serializable;

import dev.vitorpaulo.distantlove.model.Pronoun;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest implements Serializable {

    @Size(min = 3, max = 40)
    private String name;

    @Email
    private String email;

    @Size(min = 5, max = 40)
    private String password;

    private Pronoun pronoun;
}
