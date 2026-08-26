package pl.flashline.card;

import java.time.LocalDate;

public record CardResponse(
        Long id,
        Long deckId,
        CardType type,
        AnswerMode suggestedMode,
        String category,
        String question,
        CardContent content,
        String audioUrl,
        String extendedInfo,
        CefrLevel level,
        LocalDate nextReviewDate
) {
}
