package pl.flashline.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.flashline.card.Card;
import pl.flashline.card.CardNotFoundException;
import pl.flashline.card.CardRepository;
import pl.flashline.deck.AlgorithmSettings;
import pl.flashline.deck.Deck;
import pl.flashline.deck.DeckNotFoundException;
import pl.flashline.deck.DeckRepository;
import pl.flashline.security.CurrentUserProvider;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final Sm2Calculator sm2Calculator;
    private final CurrentUserProvider currentUserProvider;

    @Transactional
    public ReviewResponse submitReview(Long deckId, long cardId, ReviewRequest request) {
        Deck deck = deckRepository.findByIdAndUserId(deckId, currentUserProvider.getCurrentUserId())
                .orElseThrow(() -> new DeckNotFoundException(deckId));

        Card card = cardRepository.findByIdAndDeckId(cardId, deck.getId())
                .orElseThrow(() -> new CardNotFoundException(cardId));

        AlgorithmSettings settings = deck.getAlgorithmSettings();

        CardState currentState = new CardState(
                card.getEaseFactor(), card.getIntervalDays(), card.getRepetitions());

        ReviewResult result = sm2Calculator.calculate(
                currentState,
                request.quality(),
                settings.getFirstIntervalDays(),
                settings.getSecondIntervalDays(),
                settings.getMinEaseFactor(),
                settings.getRequeueThreshold()
        );

        LocalDate nextReviewDate = LocalDate.now().plusDays(result.intervalDays());

        card.applyReviewResult(
                result.easeFactor(), result.intervalDays(), result.repetitions(), nextReviewDate
        );

        // Nie trzeba jawnego cardRepository.save(card) — dzięki @Transactional
        // i dirty checking (patrz słowniczek: JPA vs Hibernate) Hibernate
        // sam wykryje zmianę na już zarządzanej encji i zapisze ją na
        // koniec transakcji.
        return new ReviewResponse(nextReviewDate, result.intervalDays(), result.requeueToday());
    }

    public List<Card> getDueCards(Long deckId) {
        Deck deck = deckRepository.findByIdAndUserId(deckId, currentUserProvider.getCurrentUserId())
                .orElseThrow(() -> new DeckNotFoundException(deckId));

        return cardRepository.findAllByDeckIdAndNextReviewDateLessThanEqual(
                deck.getId(), LocalDate.now());
    }

}
