package pl.flashline.deck;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    List<Deck> findAllByUserId(Long userId);

    Optional<Deck> findByIdAndUserId(Long id, Long userId);
}
