package pl.flashline.card;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record BatchCreateCardsRequest(
        @NotEmpty(message = "Import musi zawierać co najmniej jedną fiszkę")
        List<@Valid CreateCardRequest> cards
) {
}
