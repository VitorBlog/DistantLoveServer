package dev.vitorpaulo.distantlove.domain;

import java.util.Date;

import dev.vitorpaulo.distantlove.model.Pronoun;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    @Lob
    private String password;

    private String avatar;

    @Lob
    private String firebaseToken;

    @Lob
    private String refreshToken;

    private Pronoun pronoun;

    private Date createdAt;
}
