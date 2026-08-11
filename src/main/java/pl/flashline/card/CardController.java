package pl.flashline.card;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/decks/{deckId}/cards")
@Tag(name = "Cards", description = "Zarządzanie fiszkami w obrębie talii")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping
    @Operation(summary = "Lista kart w talii")
    public ResponseEntity<List<CardResponse>> getCards(@PathVariable Long deckId) {
        return ResponseEntity.ok(cardService.getCardsInDeck(deckId));
    }

    @PostMapping
    @Operation(summary = "Dodanie nowej karty do talii")
    public ResponseEntity<CardResponse> createCard(
            @PathVariable Long deckId,
            @Valid @RequestBody CreateCardRequest request) {
        return ResponseEntity.ok(cardService.crateCard(deckId, request));
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Szczegóły pojedynczej karty")
    public ResponseEntity<CardResponse> getCard(
            @PathVariable Long deckId,
            @PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.getCard(deckId, cardId));
    }

}