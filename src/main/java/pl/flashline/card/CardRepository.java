package pl.flashline.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByDeckId(Long deckId);

    Optional<Card> findByIdAndDeckId(Long id, Long deckId);

    /* Deprecated */
    @Query("""
            SELECT c FROM Card c
            WHERE c.id = :cardId
            AND c.deckId IN (SELECT d.id FROM Deck d WHERE d.userId = :userId)
            """)
    Optional<Card> findByIdAndOwnerUserId(Long cardId, Long userId);

    List<Card> findAllByDeckIdAndNextReviewDateLessThanEqual(Long deckId, LocalDate date);

}
