package dev.vitorpaulo.distantlove.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.vitorpaulo.distantlove.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByRefreshToken(String refreshToken);
}