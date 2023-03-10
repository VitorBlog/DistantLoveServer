package dev.vitorpaulo.distantlove.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.vitorpaulo.distantlove.domain.CoupleMember;
import dev.vitorpaulo.distantlove.domain.User;

public interface CoupleMemberRepository extends JpaRepository<CoupleMember, Long> {

    Boolean existsByUser(User user);
}