package pl.flashline.sourcetext;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.flashline.card.Card;
import pl.flashline.card.CardContentSerializer;
import pl.flashline.card.CardRepository;
import pl.flashline.card.CreateCardRequest;
import pl.flashline.deck.Deck;
import pl.flashline.deck.DeckNotFoundException;
import pl.flashline.deck.DeckRepository;
import pl.flashline.security.CurrentUserProvider;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final SourceTextRepository sourceTextRepository;
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final CardContentSerializer cardContentSerializer;
    private final CurrentUserProvider currentUserProvider;

    @Transactional
    public ImportResponse importSourceText(Long deckId, ImportSourceTextRequest request) {
        Deck deck = deckRepository.findByIdAndUserId(deckId, currentUserProvider.getCurrentUserId())
                .orElseThrow(() -> new DeckNotFoundException(deckId));

        SourceText sourceText = SourceText.builder()
                .deckId(deck.getId())
                .title(request.title())
                .category(request.category())
                .content(request.content())
                .level(request.level())
                .build();

        SourceText savedSourceText = sourceTextRepository.save(sourceText);

        double initialEase = deck.getAlgorithmSettings().getInitialEaseFactor();


        for (CreateCardRequest cardRequest : request.cards()) {
            Card card = Card.builder()
                    .deckId(deck.getId())
                    .sourceTextId(savedSourceText.getId())
                    .type(cardRequest.type())
                    .suggestedMode(cardRequest.suggestedMode())
                    .category(cardRequest.category())
                    .question(cardRequest.question())
                    .contentJson(cardContentSerializer.toJson(cardRequest.content()))
                    .audioUrl(cardRequest.audioUrl())
                    .level(cardRequest.level())
                    .initialEaseFactor(initialEase)
                    .build();

            cardRepository.save(card);
        }

        return new ImportResponse(savedSourceText.getId(), request.cards().size());
    }


}
