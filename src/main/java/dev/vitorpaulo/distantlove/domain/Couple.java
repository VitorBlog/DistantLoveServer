package dev.vitorpaulo.distantlove.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Couple {

    @Id
    @GeneratedValue
    private Long id;

    // List<CoupleDate>
    // Gallery
    // Interaction

    private Date anniversary;

    private Date createdAt;
}
