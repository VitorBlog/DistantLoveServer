package dev.vitorpaulo.distantlove.repository;

import dev.vitorpaulo.distantlove.domain.Couple;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.vitorpaulo.distantlove.domain.CoupleMember;
import dev.vitorpaulo.distantlove.domain.User;

import java.util.Optional;

public interface CoupleMemberRepository extends JpaRepository<CoupleMember, Long> {

    Boolean existsByUser(User user);

    Optional<CoupleMember> findByUser(User user);

    Optional<CoupleMember> findByCoupleAndUserIsNot(Couple couple, User user);
}
