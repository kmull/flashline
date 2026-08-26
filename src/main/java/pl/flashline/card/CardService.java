package pl.flashline.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.flashline.deck.Deck;
import pl.flashline.deck.DeckNotFoundException;
import pl.flashline.deck.DeckRepository;
import pl.flashline.security.CurrentUserProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final CardMapper cardMapper;
    private final CardContentSerializer cardContentSerializer;
    private final CurrentUserProvider currentUserProvider;

    public List<CardResponse> getCardsInDeck(Long deckId) {
        Deck deck = getOwnedDeck(deckId);
        return cardRepository.findAllByDeckId(deck.getId()).stream()
                .map(cardMapper::toResponse)
                .toList();
    }

    public CardResponse crateCard(Long deckId, CreateCardRequest request) {
        Deck deck = getOwnedDeck(deckId);

        String json = cardContentSerializer.toJson(request.content());
        double initialEase = deck.getAlgorithmSettings().getInitialEaseFactor();

        Card card = Card.builder()
                .deckId(deck.getId())
                .type(request.type())
                .suggestedMode(request.suggestedMode())
                .category(request.category())
                .question(request.question())
                .contentJson(json)
                .audioUrl(request.audioUrl())
                .extendedInfo(request.extendedInfo())
                .level(request.level())
                .initialEaseFactor(initialEase)
                .build();

        Card saved = cardRepository.save(card);
        return cardMapper.toResponse(saved);

    }

    public CardResponse getCard(Long cardId, Long deckId) {
        Deck deck = getOwnedDeck(deckId);
        Card card = cardRepository.findByIdAndDeckId(cardId, deck.getId())
                .orElseThrow(() -> new CardNotFoundException(cardId));

        return cardMapper.toResponse(card);
    }

    @Transactional
    public List<CardResponse> createCards(Long deckId, BatchCreateCardsRequest request) {
        Deck deck = getOwnedDeck(deckId);
        double initialEase = deck.getAlgorithmSettings().getInitialEaseFactor();

        return request.cards().stream()
                .map(cardRequest -> {
                    Card card = Card.builder()
                            .deckId(deck.getId())
                            .type(cardRequest.type())
                            .suggestedMode(cardRequest.suggestedMode())
                            .category(cardRequest.category())
                            .question(cardRequest.question())
                            .contentJson(cardContentSerializer.toJson(cardRequest.content()))
                            .audioUrl(cardRequest.audioUrl())
                            .extendedInfo(cardRequest.extendedInfo())
                            .level(cardRequest.level())
                            .initialEaseFactor(initialEase)
                            .build();

                    Card saved = cardRepository.save(card);
                    return cardMapper.toResponse(saved);
                })
                .toList();
    }

    public CardResponse getCardOwnedByOwnerUser(Long cardId, Long deckId) {
        Card card = cardRepository.findByIdAndOwnerUserId(cardId, deckId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
        return cardMapper.toResponse(card);
    }

    private Deck getOwnedDeck(Long deckId) {
        return deckRepository.findByIdAndUserId(deckId, currentUserProvider.getCurrentUserId())
                .orElseThrow(() -> new DeckNotFoundException(deckId));
    }

}
