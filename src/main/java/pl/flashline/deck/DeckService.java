package pl.flashline.deck;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flashline.security.CurrentUserProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;
    private final CurrentUserProvider currentUserProvider;

    public List<DeckResponse> getMyDecks() {
        Long userId = currentUserProvider.getCurrentUserId();
        return deckRepository.findAllByUserId(userId).stream()
                .map(deckMapper::toResponse)
                .toList();
    }

    public DeckResponse createDeck(CreateDeckRequest request) {
        Deck deck = new Deck(currentUserProvider.getCurrentUserId(), request.name(), request.category());
        Deck saved = deckRepository.save(deck);
        return deckMapper.toResponse(saved);
    }

    public DeckResponse getDeck(Long id) {
        Deck deck = deckRepository.findByIdAndUserId(id, currentUserProvider.getCurrentUserId())
                .orElseThrow(() -> new DeckNotFoundException(id));
        return deckMapper.toResponse(deck);
    }
}
