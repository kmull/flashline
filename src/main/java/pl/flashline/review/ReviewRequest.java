package pl.flashline.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
        @NotNull(message = "Ocena jest wymagana")
        @Min(value = 0, message = "Ocena musi być w zakresie 0-5")
        @Max(value = 5, message = "Ocena musi byc w zakresie 0-5")
        Integer quality
) {
}
