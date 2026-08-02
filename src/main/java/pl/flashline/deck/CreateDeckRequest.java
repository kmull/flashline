package pl.flashline.deck;

import jakarta.validation.constraints.NotBlank;

public record CreateDeckRequest(
        @NotBlank(message = "Nazwa talii jest wymagana")
        String name,

        String category
) {
}
