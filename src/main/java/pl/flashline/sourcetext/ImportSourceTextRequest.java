package pl.flashline.sourcetext;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import pl.flashline.card.CefrLevel;
import pl.flashline.card.CreateCardRequest;

import java.util.List;

/**
 * Kształt danych dokładnie odpowiadający temu, co AI generuje wg
 * instrukcji z instrukcja_generowania_fiszek.md — "sourceText" +
 * "cards" w jednym obiekcie JSON. Front wkleja to jeden do jednego,
 * bez żadnej ręcznej przeróbki.
 */
public record ImportSourceTextRequest(
        @NotBlank(message = "Tytił tekstu jest wymagany")
        String title,

        String category,

        @NotBlank(message = "Treść tekstu jest wymagana")
        String content,

        CefrLevel level,

        @NotEmpty(message = "Import musi zawierać co najmniej jedną fiszkę")
        List<@Valid CreateCardRequest> cards
) {
}
