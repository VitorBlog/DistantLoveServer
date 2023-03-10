package dev.vitorpaulo.distantlove.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.vitorpaulo.distantlove.domain.Couple;

public interface CoupleRepository extends JpaRepository<Couple, Long> {

}