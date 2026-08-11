package pl.flashline.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCardRequest(
        @NotNull(message = "Typ karty jest wymagany")
        CardType type,

        AnswerMode suggestedMode,

        String category,

        @NotBlank(message = "Pytanie jest wymagane")
        String question,

        @NotNull(message = "Treść karty jest wymagana")
        CardContent content,

        String audioUrl,

        CefrLevel level
) {
}
