package pl.flashline.deck;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.flashline.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;

    public List<DeckResponse> getMyDecks() {
        Long userId = currentUserId();
        return deckRepository.findAllByUserId(userId).stream()
                .map(deckMapper::toResponse)
                .toList();
    }

    public DeckResponse createDeck(CreateDeckRequest request) {
        Deck deck = new Deck(currentUserId(), request.name(), request.category());
        Deck saved = deckRepository.save(deck);
        return deckMapper.toResponse(saved);
    }

    public DeckResponse getDeck(Long id) {
        Deck deck = deckRepository.findByIdAndUserId(id, currentUserId())
                .orElseThrow(() -> new DeckNotFoundException(id));
        return deckMapper.toResponse(deck);
    }

    private Long currentUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}
