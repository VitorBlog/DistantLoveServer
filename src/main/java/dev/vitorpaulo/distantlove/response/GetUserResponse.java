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
public class GetUserResponse implements Serializable {

    private final Long id;
    private final String name;
    private final String email;
    private final String avatar;
    private final Pronoun pronoun;
    private final Date createdAt;
}
