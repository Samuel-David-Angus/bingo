package com.example.bingo.repos;

import com.example.bingo.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByPlaycardToken(String playcardToken);
}
