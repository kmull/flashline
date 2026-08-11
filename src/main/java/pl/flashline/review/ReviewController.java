package pl.flashline.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.flashline.card.Card;
import pl.flashline.card.CardMapper;
import pl.flashline.card.CardResponse;

import java.util.List;

@RestController
@RequestMapping("/api/decks/{deckId}")
@Tag(name = "Review", description = "Powtórki fiszek - algorytm SM-2")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final CardMapper cardMapper;

    @PostMapping("/cards/{cardId}/review")
    @Operation(summary = "Zapisanie oceny powtórki karty")
    public ResponseEntity<ReviewResponse> submitReview(
            @PathVariable Long deckId,
            @PathVariable Long cardId,
            @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.submitReview(deckId, cardId, request));
    }

    @GetMapping("/cards/due")
    @Operation(summary = "Karty do powtórki dziś")
    public ResponseEntity<List<CardResponse>> getDueCards(@PathVariable Long deckId) {
        List<Card> dueCards = reviewService.getDueCards(deckId);
        return ResponseEntity.ok(dueCards.stream().map(cardMapper::toResponse).toList());
    }
}