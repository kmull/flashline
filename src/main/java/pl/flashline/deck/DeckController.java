package pl.flashline.deck;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/decks")
@Tag(name = "Decks", description = "Zarządzanie taliami fiszek")
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;

    @GetMapping
    @Operation(summary = "Lista talii zalogowanego użytkownika")
    public ResponseEntity<List<DeckResponse>> getMyDecks() {
        return ResponseEntity.ok(deckService.getMyDecks());
    }

    @PostMapping
    @Operation(summary = "Utworzenie nowej talii")
    public ResponseEntity<DeckResponse> createDeck(@Valid @RequestBody CreateDeckRequest request) {
        return ResponseEntity.ok(deckService.createDeck(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Szczegóły pojedynczej talii")
    public ResponseEntity<DeckResponse> getDeck(@PathVariable Long id) {
        return ResponseEntity.ok(deckService.getDeck(id));
    }
}
