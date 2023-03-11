package dev.vitorpaulo.distantlove.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class CoupleMember {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Couple couple;

    @OneToOne
    private User user;
}
